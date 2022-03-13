package gitlet;

import java.io.File;

import static gitlet.Repository.BRANCHES_DIR;
import static gitlet.Utils.join;
import static gitlet.Utils.restrictedDelete;

/** This class handles the logic for rm-branch
 * Command: rm-branch
 * Input: file name */

public class RmBranchMethod {

    public static void rmBranch(String branchName) {

        File branchFile = join(BRANCHES_DIR, branchName);

        /* Failure: if branch doesn't exist in branches dir */
        if (!branchFile.exists()) {
            System.out.println("A branch with that name does not exist.");
            return;
        }

        /* Failure: can't remove branch if currently on this branch */
        if (Repository.currentBranch.equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }

        /* BODY */
        // Delete the branch file
        restrictedDelete(branchFile);
    }
}
