package gitlet;

import java.io.File;
import java.time.*;
import java.time.format.DateTimeFormatter;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

/** This class handles the logic for log
 * Command: log
 * Input: depends on the checkout method called -- more detail below */

public class LogMethod {

    public static void log() {
        // Access the head commit
        File headFile = join(COMMITS_DIR, readContentsAsString(HEAD));
        String headCommitID = readContentsAsString(HEAD);
        Commit headCommit = readObject(headFile, Commit.class);

        // Check if you're at initial commit
        helper(headCommit, headCommitID);

    }

    private static void helper(Commit object, String ID) {

        if (object == null) {
            return;
        }

        // Print commit ID
        System.out.println("===");
        System.out.println("commit " + ID);

       /* ===
        commit e881c9575d180a215d1a636545b8fd9abfb1d2bb
        Date: Wed Dec 31 16:00:00 1969 -0800
        initial commit*/
        // Print commit date
        Instant instant = object.getTime();
        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss uuuu xxxx");
        String text = formatter.format(dateTime);
        System.out.println("Date: " + text);

        // Print commit message
        System.out.println(object.getMessage());
        System.out.println();

        // Special print case for merge commits
        if (object.secondParent != null) {
            String firstParent = object.firstParent;
            String secondParent = object.secondParent;
            System.out.println("Merge: " + 	firstParent.substring(0, 5) + " " + secondParent.substring(0, 5));
        }

        // Recurse, passing in the parent commit, as long as parent is not null
        String parentCommitID = object.firstParent;
        if (parentCommitID != null) {
            File blob = join(COMMITS_DIR, parentCommitID);
            Commit parentCommit = readObject(blob, Commit.class);
            helper(parentCommit, parentCommitID);
        }

    }
}
