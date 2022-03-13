package gitlet;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import static gitlet.Repository.COMMITS_DIR;

public class FindMethod {

    public static void find(String inputCommitMessage) {

        // Access a list of all commits in commits folder
        List<String> commitsList = Utils.plainFilenamesIn(COMMITS_DIR);

        // Iterator to access each file in list
        Iterator commitsIterator = commitsList.iterator();

        int count = 0;

        while(commitsIterator.hasNext()) {
            String commitFileID = (String) commitsIterator.next();
            File commitFile = Utils.join(COMMITS_DIR, commitFileID);
            Commit commitObject = Utils.readObject(commitFile, Commit.class);
            String commitMessage = commitObject.getMessage();
            if (commitMessage.equals(inputCommitMessage)) {
                System.out.println(commitFileID);
                count += 1;
            }
        }

        // If no commits found with that message, print failure message
        if (count == 0) {
            System.out.println("Found no commit with that message.");
        }

    }
}
