package gitlet;

import java.io.File;
import java.io.IOException;
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
    private static String splitPointFinder(Commit currentBranchCommit, Commit inputBranchCommit) {

        // Base case: if the commits are equal, return the sha of one of them
        if (currentBranchCommit.equals(inputBranchCommit)) {
            return sha1(serialize(currentBranchCommit));
        } else {
            // Compare the two commit time stamps
            // Whichever is later, go to the parent commit of that one until base case
            if (currentBranchCommit.getTime().isAfter(inputBranchCommit.getTime())) {
                Commit currentBranchCommitParent = grabParentCommit(currentBranchCommit);
                return splitPointFinder(currentBranchCommitParent, inputBranchCommit);
            } else {
                Commit inputBranchCommitParent = grabParentCommit(inputBranchCommit);
                return splitPointFinder(currentBranchCommit, inputBranchCommitParent);
            }

        }

    }

    // Automatic commit
    public static void merge(String inputBranchName) throws IOException {

        File currentBranchFile = CURRENT_BRANCH;
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
        Commit currentBranchCommit = readObject(currentBranchFile, Commit.class);
        Commit inputBranchCommit = readObject(inputBranchFile, Commit.class);
        String splitCommitID = splitPointFinder(currentBranchCommit, inputBranchCommit);
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
        ultimateHash.addAll(inputKeys);
        ultimateHash.addAll(splitKeys);

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
                IF split = input, split != current
                THEN stage the version in input for addition */
                if (!splitSHA.equals(currentSHA) && splitSHA.equals(inputSHA)) {
                    specialStagingAdd(fileNameFromIterator, splitSHA);
                    continue;
                }

                /* Condition 2:
                 IF split != input, split = current
                 THEN do nothing */
                if (splitSHA.equals(currentSHA) && !splitSHA.equals(inputSHA)) {
                    continue;
                }

            }

            /* Condition 3a:
             * IF exists in split, modified in input, modified in current
             THEN do nothing */

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

            /* Condition 4:
             * IF NOT in split, IN current, NOT in import
             THEN stage for removal */
            if (!splitHash.containsKey(fileNameFromIterator) && currentHash.containsKey(fileNameFromIterator) && !inputHash.containsKey(fileNameFromIterator)) {

                // Add to staging for remove hashmap
                specialStagingRemove(fileNameFromIterator);
            }

            /* Condition 5:
             * IF NOT in split, NOT in current, IN import
             THEN stage import version for adding */
            if (!splitHash.containsKey(fileNameFromIterator) && !currentHash.containsKey(fileNameFromIterator) && inputHash.containsKey(fileNameFromIterator)) {
                specialStagingAdd(fileNameFromIterator, inputSHA);
                continue;
            }

            /* Condition 6:
             * IF in split, NOT changed in current, NOT IN import
             THEN stage for removal */
            if (!splitSHA.equals(currentSHA) && !inputHash.containsKey(fileNameFromIterator)) {
                specialStagingRemove(fileNameFromIterator);
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
