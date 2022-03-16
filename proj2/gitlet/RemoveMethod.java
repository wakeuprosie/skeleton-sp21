package gitlet;

import java.io.File;
import java.util.HashMap;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class RemoveMethod {

    /** This class handles the logic for removing
     * Command: rm
     * Input: one arg of file name */

    /** Removes from CWD and from HEAD commit */
    public static void remove(String filename) {

        File stagingFile = STAGING;
        HashMap stagingOpened = readObject(stagingFile, HashMap.class);

        File fileInCWD = join(CWD, filename);

        // Failure: if not in staging or in head commit -- print error message
        if (!stagingOpened.containsKey(filename) && !fileInCWD.exists()) {
            System.out.println("No reason to remove the file.");
        }

        // Remove from staging hashmap if it exists
        stagingOpened.remove(filename);

        // Close up staging hashmap and re-save
        writeObject(stagingFile, stagingOpened);

        // Stage for removal
        // Add to staging for remove hashmap
        HashMap stagingRm = readObject(STAGING_RM, HashMap.class);
        stagingRm.put(filename, null);

        // Close up staging for rm hashmap and re-save
        writeObject(STAGING_RM, stagingRm);

        // Remove from CWD
        fileInCWD.delete();

    }
}
