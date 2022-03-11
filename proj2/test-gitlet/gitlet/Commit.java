package gitlet;

import java.io.Serializable;
import java.time.*;
import java.util.HashMap;

import static gitlet.Utils.*;


/** Commit object */

public class Commit implements Serializable {

    private String message;
    private Instant time;
    private String parent;
    private HashMap trackedFiles;

    /** Constructor */
    public Commit (String message, String parentCommit) {
        // Commit object
        this.message = message;
        if (this.parent == null) {
            this.time = Instant.EPOCH;
        } else {
            this.time = Instant.now();
        }
        if (parentCommit != null) {
            this.parent = sha1(parentCommit);
        }
        this.trackedFiles = new HashMap();
    }

    /** Method to update tracked files hashmap */
    public void updateTrackedFiles(HashMap object) {
        this.trackedFiles = object;
    }

}
