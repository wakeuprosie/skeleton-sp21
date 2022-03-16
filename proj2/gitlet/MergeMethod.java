package gitlet;

import java.io.File;
import java.io.IOException;
import java.net.IDN;
import java.util.*;

import static gitlet.CommitMethod.commitMethod;
import static gitlet.Repository.*;
import static gitlet.StagingMethod.*;
import static gitlet.Utils.*;

public class MergeMethod {

    /* Helper that grabs the parent commit object of any commit */
    private static Commit grabParentCommit(Commit object) {
        String parentID = object.getFirstParent();

        if (parentID == null) {
            return null;
        }

        File parentFile = join(COMMITS_DIR, parentID);
        return readObject(parentFile, Commit.class);
    }

    /* Helper that finds split point via timestamp comparisons */
    private static String splitPointFinder(String currentBranchCommitID, String inputBranchCommitID) {

        // Base case: if the commit IDs are equal, return one of them
        if (currentBranchCommitID.equals(inputBranchCommitID)) { // This doesn't recognize that the commits are equal == Compare the parentIDs instead of the parent Commits first, then use the commits
            return currentBranchCommitID;
        } else {
            // Compare the two commit time stamps
            // Whichever is later, go to the parent commit of that one until base case
            Commit currentBranchCommit = readObject(join(COMMITS_DIR, currentBranchCommitID), Commit.class);
            Commit inputBranchCommit = readObject(join(COMMITS_DIR, inputBranchCommitID), Commit.class);

            if (currentBranchCommit.getTime().isAfter(inputBranchCommit.getTime())) {
                return splitPointFinder(currentBranchCommit.firstParent, inputBranchCommitID);
            } else {
                return splitPointFinder(currentBranchCommitID, inputBranchCommit.firstParent);
            }

        }

    }

    // Automatic commit
    public static void merge(String inputBranchName) throws IOException {

        File inputBranchFile = join(BRANCHES_DIR, inputBranchName);

        // Failure: check if you're currently on the input branch -- exit
        if (readContentsAsString(CURRENT_BRANCH).equals(inputBranchName)) {
            System.out.println("Cannot merge a branch with itself.");
            System.exit(0);
        }

        // Failure: check if branch doesn't exist -- exit
        if (!inputBranchFile.exists()) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }

        // Access the three of commits
        File currentBranchCommitID = join(BRANCHES_DIR, readContentsAsString(CURRENT_BRANCH));
        Commit currentBranchCommit = readObject(join(COMMITS_DIR, readContentsAsString(currentBranchCommitID)), Commit.class);
        File inputBranchID = join(BRANCHES_DIR, inputBranchName);
        Commit inputBranchCommit = readObject(join(COMMITS_DIR, readContentsAsString(inputBranchID)), Commit.class);
        // Get the ID of the current branch -- get the current branch name, find that file in branches, read the file as string
        String splitCommitID = splitPointFinder(readContentsAsString(currentBranchCommitID), readContentsAsString(inputBranchID));
        File splitCommitFile = join(COMMITS_DIR, splitCommitID);
        Commit splitCommit = readObject(splitCommitFile, Commit.class);

        // Combine three commits super files UNIQUE set of all keys
        HashMap currentHash = currentBranchCommit.getSuperFiles();
        HashMap inputHash = inputBranchCommit.getSuperFiles();
        HashMap splitHash = splitCommit.getSuperFiles();

        Set currentKeys = currentHash.keySet();
        Set inputKeys = inputHash.keySet();
        Set splitKeys = splitHash.keySet();
        List ultimateHash = new ArrayList<String>();
        ultimateHash.addAll(currentKeys);
        // Iterate through input and split -- only add missing keys
        Iterator inputKeysIterator = inputKeys.iterator();
        while (inputKeysIterator.hasNext()) {
            String key = (String) inputKeysIterator.next();
            if (!ultimateHash.contains(key)) {
                ultimateHash.add(key);
            }
        }
        Iterator splitKeysIterator = splitKeys.iterator();
        while (splitKeysIterator.hasNext()) {
            String key = (String) splitKeysIterator.next();
            if (!ultimateHash.contains(key)) {
                ultimateHash.add(key);
            }
        }

        /* For each file in the ultimate list, check the condition in
         * currentCommit and inputCommit and check the following conditions */
        Iterator<String> ultimateHashIterator = ultimateHash.iterator();

        while (ultimateHashIterator.hasNext()) {

            String fileNameFromIterator = ultimateHashIterator.next();
            String splitSHA = (String) splitHash.get(fileNameFromIterator);
            String currentSHA = (String) currentHash.get(fileNameFromIterator);
            String inputSHA = (String) inputHash.get(fileNameFromIterator);

            // Condition group: split, current, and input ALL have the file

            if (splitHash.containsKey(fileNameFromIterator) && currentHash.containsKey(fileNameFromIterator) && inputHash.containsKey(fileNameFromIterator)) {

                /* Condition 1:
                IF split != current, current != input, split = input:
                THEN stage the version in current for addition */
                if (!splitSHA.equals(currentSHA) && splitSHA.equals(inputSHA)) {
                    specialStagingAdd(fileNameFromIterator, currentSHA);
                    continue;
                }

                /* Condition 2:
                 IF split = current, current != input
                 THEN save the input version */
                if (splitSHA.equals(currentSHA) && !splitSHA.equals(inputSHA)) {
                    specialStagingAdd(fileNameFromIterator, inputSHA);

                    // Update file on CWD
                    writeContents(join(CWD, fileNameFromIterator), readContentsAsString(join(BLOBS_DIR, inputSHA)));
                    continue;
                }

            }

            /* Condition 4:
             * IF NOT in split, IN current, NOT in import
             THEN stage for removal */
            if (!splitHash.containsKey(fileNameFromIterator) && currentHash.containsKey(fileNameFromIterator) && !inputHash.containsKey(fileNameFromIterator)) {
                // Add to staging for remove hashmap
                specialStagingRemove(fileNameFromIterator);
                continue;
            }

            /* Condition 5:
             * IF NOT in split, NOT in current, IN import
             THEN stage input version for adding */
            if (!splitHash.containsKey(fileNameFromIterator) && !currentHash.containsKey(fileNameFromIterator) && inputHash.containsKey(fileNameFromIterator)) {
                specialStagingAdd(fileNameFromIterator, inputSHA);

                // Add to CWD
                File newFile = join(CWD, fileNameFromIterator);
                newFile.createNewFile();
                writeContents(newFile, readContentsAsString(join(BLOBS_DIR, inputSHA)));
                continue;
            }

             /* Condition 3aa:
             * IF exists in split, removed in input, removed in current
             THEN do nothing */
            if (splitHash.containsKey(fileNameFromIterator) && !inputHash.containsKey(fileNameFromIterator) && !currentHash.containsKey(fileNameFromIterator)) {
                continue;
            }

            /* Condition 3ab:
             * IF exists in split, modified in input, modified in current
             THEN do nothing */
            if (splitHash.containsKey(fileNameFromIterator) && !inputHash.equals(splitHash) && inputHash.equals(currentHash)) {
                continue;
            }

            /* Condition 3b: AKA CONFLICT
             * IF exists in split, modified in input, modified in current
             THEN CONFLICT */
            if (!splitSHA.equals(currentSHA) && !splitSHA.equals(inputSHA) && !currentSHA.equals(inputSHA)) {

                // Create a new blob with this string as content and reassign to this filename
                String randomContent = "<<<<<<< HEAD\n" +
                        "contents of file in current branch\n" +
                        "=======\n" +
                        "contents of file in given branch\n" +
                        ">>>>>>>";
                String newSHA = sha1(randomContent);
                File replacementFile = join(BLOBS_DIR, newSHA);
                writeContents(replacementFile, randomContent);

                // Add to staging for add hashmap
                specialStagingAdd(fileNameFromIterator, newSHA);

            }

            /* Condition 6:
             * IF in split, NOT changed in current, NOT IN import
             THEN stage for removal */
            if (splitSHA.equals(currentSHA) && !inputHash.containsKey(fileNameFromIterator)) {
                specialStagingRemove(fileNameFromIterator);

                // Remove from CWD
                join(CWD, fileNameFromIterator).delete();
            }

            /* Condition 7:
             * IF in split, NOT IN current, NOT changed in import
             THEN do nothing */

        }

        /* Call commit method at the very end */
        String string = "Merged " + readContentsAsString(CURRENT_BRANCH) + " into " + inputBranchName;
        commitMethod(string);

    }
}
