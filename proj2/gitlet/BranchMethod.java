package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class BranchMethod {

    public static void branch(String branchName) throws IOException {

        // Create a new file in branches with that branch name
        File newBranch = join(BRANCHES_DIR, branchName);
        newBranch.createNewFile();
        writeContents(newBranch, readContentsAsString(HEAD));

        // Change the current branch pointer
        writeContents(CURRENT_BRANCH, branchName);

    }
}
