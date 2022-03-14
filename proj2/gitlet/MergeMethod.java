package gitlet;

import java.io.File;
import java.util.*;

import static gitlet.Repository.*;
import static gitlet.Staging.specialStagingAdd;
import static gitlet.Staging.specialStagingRemove;
import static gitlet.Utils.*;

public class MergeMethod {

    /* Helper that grabs the parent commit object of any commit */
    private static Commit grabParentCommit(Commit object) {
        String parentID = object.getParent();

        if (parentID == null) {
            return null;
        }

        File parentFile = join(COMMITS_DIR, parentID);
        Commit parentCommit = readObject(parentFile, Commit.class);
        return parentCommit;
    }

    /* This loop works by comparing timestamps and seeing which is earlier */
    private static String splitPointFinder(Commit currentBranchCommit, Commit inputBranchCommit) {

        // If the commits are equal, return the sha of either (they should be the same)
        if (currentBranchCommit.equals(inputBranchCommit)) {
            return sha1(serialize(currentBranchCommit));
        }
        // Compare the two commit time stamps
        // Whichever one is later, go to the parent commit of that one until the commits EQUAL
        else if (currentBranchCommit.getTime().isAfter(inputBranchCommit.getTime())) {
            Commit currentBranchCommitParent = grabParentCommit(currentBranchCommit);
            return splitPointFinder(currentBranchCommitParent, inputBranchCommit);
        } else if (currentBranchCommit.getTime().isBefore(inputBranchCommit.getTime()); {
            Commit inputBranchCommitParent = grabParentCommit(inputBranchCommit);
            return splitPointFinder(currentBranchCommit, inputBranchCommitParent);
        }

    }

    // At the end of this bullshit, merge automatically performs a commit method
    public static void merge(String inputBranchName) {

        File currentBranchFile = join(BRANCHES_DIR, currentBranch);
        File inputBranchFile = join(BRANCHES_DIR, inputBranchName);

        // Failure: check if you're currently on the input branch -- exit
        if (currentBranch.equals(inputBranchName)) {
            System.out.println("Cannot merge a branch with itself.");
            return;
        }

        // Failure: check if branch doesn't exist -- exit
        if (!inputBranchFile.exists()) {
            System.out.println("A branch with that name does not exist.");
            return;
        }

        // Access the triad of commits
        Commit currentBranchCommit = readObject(currentBranchFile, Commit.class);
        Commit inputBranchCommit = readObject(inputBranchFile, Commit.class);
        String splitCommitID = splitPointFinder(currentBranchCommit, inputBranchCommit);
        File splitCommitFile = join(COMMITS_DIR, splitCommitID);
        Commit splitCommit = readObject(splitCommitFile, Commit.class);

        // Combine three commits super files UNIQUE set of all keys
        HashMap currentHash = currentBranchCommit.superFiles;
        HashMap inputHash = inputBranchCommit.superFiles;
        HashMap splitHash = splitCommit.superFiles;

        Set<String> currentKeys = currentHash.keySet();
        Set<String> inputKeys = inputHash.keySet();
        Set<String> splitKeys = splitHash.keySet();
        Set<String> ultimateHash = Collections.emptySet();
        ultimateHash.addAll(currentKeys);
        ultimateHash.addAll(inputKeys);
        ultimateHash.addAll(splitKeys);

        /** For each file in the ultimate list, check the condition in
         * currentCommit and inputCommit and check the following conditions */
        Iterator<String> ultimateHashIterator = ultimateHash.iterator();

        while (ultimateHashIterator.hasNext()) {

            String fileNameFromIterator = ultimateHashIterator.next();
            String splitSHA = (String) splitHash.get(fileNameFromIterator);
            String currentSHA = (String) currentHash.get(fileNameFromIterator);
            String inputSHA = (String) inputHash.get(fileNameFromIterator);

            // Condition group: split, current, and input ALL have the file

            if (splitHash.containsKey(fileNameFromIterator) && currentHash.containsKey(fileNameFromIterator) && importHash.containsKey(fileNameFromIterator)) {

                /** Condition 1:
                IF split = input, split != current
                THEN stage the version in input for addition */

                if (!splitSHA.equals(currentSHA) && splitSHA.equals(inputSHA)) {
                    File stagingFile = STAGING;
                    HashMap stagingMap = readObject(stagingFile, HashMap.class);
                    specialStagingAdd(fileNameFromIterator, splitSHA);
                    continue;
                }

                /** Condition 2:
                 IF split != input, split = current
                 THEN do nothing */
                if (splitSHA.equals(currentSHA) && !splitSHA.equals(inputSHA)) {
                    continue;
                }

            }

            /** Condition 3a:
             * IF exists in split, modified in input, modified in current
             THEN do nothing */
            if (!splitSHA.equals(currentSHA) && !splitSHA.equals(inputSHA) && currentSHA.equals(inputSHA)) {
                continue;
            }

            /** Condition 3b: AKA CONFLICT
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

            /** Condition 4:
             * IF NOT in split, IN current, NOT in import
             THEN stage for removal */
            if (!splitHash.containsKey(fileNameFromIterator) && currentHash.containsKey(fileNameFromIterator) && !inputHash.containsKey(fileNameFromIterator)) {

                // Add to staging for remove hashmap
                specialStagingRemove(fileNameFromIterator);
            }

            /** Condition 5:
             * IF NOT in split, NOT in current, IN import
             THEN stage import version for adding */
            if (!splitHash.containsKey(fileNameFromIterator) && !currentHash.containsKey(fileNameFromIterator) && inputHash.containsKey(fileNameFromIterator)) {
                File stagingFile = STAGING;
                HashMap stagingMap = readObject(stagingFile, HashMap.class);
                specialStagingAdd(fileNameFromIterator, inputSHA);
                continue;
            }

            /** Condition 6:
             * IF in split, NOT changed in current, NOT IN import
             THEN stage for removal */
            if (!splitSHA.equals(currentSHA) && !inputHash.containsKey(fileNameFromIterator)) {
                specialStagingRemove(fileNameFromIterator);
            }

            /** Condition 7:
             * IF in split, NOT IN current, NOT changed in import
             THEN do nothing */
            if (!splitSHA.equals(inputSHA) && !currentHash.containsKey(fileNameFromIterator)) {
                continue;
            }

            // Call commit method at the very end
            String string = "Merged " + currentBranch + "into " + inputBranchName;
            CommitMethod.commitMethod(string);

            /** Merge commits differ from other commits: they record as parents both the head of
             * the current branch (called the first parent) and the head of the branch given on the command line to be merged in.
             */



        }
    }
}
