package gitlet;

import java.io.File;

import static gitlet.Repository.BRANCHES_DIR;
import static gitlet.Repository.HEAD;
import static gitlet.Utils.*;
import static gitlet.Utils.readObject;

public class BranchMethod {

    public static void branch(String branchName) {
        File newBranch = join(BRANCHES_DIR, branchName);

        // Create a new file in branches with that branch name
        File headFile = HEAD;
        Commit headCommit = readObject(headFile, Commit.class);

        // Read the commit in head into this file
        writeObject(newBranch, headCommit);

        // Change the repository current branch to this branch
        Repository.currentBranch = branchName;

    }
}
