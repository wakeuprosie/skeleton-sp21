package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

/** This class handles the logic for staging
 * Command: ADD
 * Input: one arg of file name */

public class Staging {

    public static void add(String arg) throws IOException {

        File currentFile = Utils.join(CWD, arg); // Reference to the file of interest on computer

        // Failure
        if (!currentFile.exists()) { // Check if file of interest actually exists on computer
            System.out.println("File does not exist.");
            return;
        }

        // Body
        String fileID = sha1(serialize(currentFile)); // Get the SHA, aka ID of the current state, of the file of interest

        File newBlobFile = Utils.join(BLOBS_DIR, fileID); // Store the file
        newBlobFile.createNewFile();
        writeContents(newBlobFile, readContents(currentFile)); // Serialize and save the file in the blob folder

        File file = STAGING;
        HashMap staging = readObject(file, HashMap.class); // Deserialize staging hashmap
        if (!staging.containsKey(arg)) {
            staging.put(arg, fileID); // Save file name and latest version SHA in hashmap
        } else {
            staging.replace(arg, fileID);
        }
        writeObject(file, staging); // Save staging hashmap back into staging folder file
    }

/** Output:
 * a new file in blobs that has the contents of this version of the file saved in a folder represented by its filename as its SHA
 * an updated key:value pair in the staging dictionary where the value is the SHA of the latest version of this file (aka a reference to the blob)
 */

}
