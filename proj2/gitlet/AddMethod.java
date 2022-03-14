package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

/** This class handles the logic for staging
 * Command: add
 * Input: one arg of file name */

public class AddMethod {

    public static void add(String arg) throws IOException {

        // Access the file of interest in cwd
        File currentFile = Utils.join(CWD, arg);

        // Failure: check file exists on computer
        if (!currentFile.exists()) { //
            System.out.println("File does not exist.");
            return;
        }

        // Create a sha to represent current state of file
        // Save the content in blobs folder, using sha as the pathname
        String fileID = sha1(serialize(currentFile));

        File newBlobFile = Utils.join(BLOBS_DIR, fileID);
        newBlobFile.createNewFile();
        writeContents(newBlobFile, readContents(currentFile));

        // Add filename : sha to staging-for-add hashmap
        // Access, edit, and re-save staging hashmap
        File file = STAGING;
        HashMap staging = readObject(file, HashMap.class);
        if (!staging.containsKey(arg)) {
            staging.put(arg, fileID);
        } else {
            staging.replace(arg, fileID);
        }
        writeObject(file, staging);
    }

    /** Output:
     * New file in blobs with saved file - file name is the sha ID
     * New (or updated) key:value pair in staging hashmap (key:value == filename:fileSHAID)
     */


    /* Special version of staging for add for use with merge
    Difference from above: uses specific version of file rather than what's on cwd
     */
    public static void specialStagingAdd(String fileName, String blobID) {

        HashMap staging = readObject(STAGING, HashMap.class); // Deserialize staging hashmap
        if (!staging.containsKey(fileName)) {
            staging.put(fileName, blobID); // Save file name and latest version SHA in hashmap
        } else {
            staging.replace(fileName, blobID);
        }
        writeObject(STAGING, staging); // Save staging hashmap back into staging folder file
    }

    /* Special version of staging for remove for use with merge
    Difference from above: uses specific version of file rather than what's on cwd
    Output: removes file name from next commit super files map, adds file name to next commits removed files map
     */
    public static void specialStagingRemove(String fileName) {

        File file = STAGING_RM;
        // Access staging for remove hashmap
        HashMap stagingRM = readObject(file, HashMap.class);

        // Give file to this remove hashmap
        if (!stagingRM.containsKey(fileName)) {
            stagingRM.remove(fileName); // Save file name and latest version SHA in hashmap
        }

        // Close and save the staging for rm hashmap
        writeObject(file, stagingRM); // Save staging hashmap back into staging folder file

    }




}
