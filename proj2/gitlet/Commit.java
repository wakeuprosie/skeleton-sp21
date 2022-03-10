package gitlet;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import static gitlet.Utils.*;


/** Commit object */

public class Commit implements Serializable {

    private String message;
    private Date date;
    private String parent;
    private HashMap trackedFiles = new HashMap();

    /** Constructor */
    public Commit (String message, String parentCommit) {
        // Commit object
        this.message = message;
        if (this.parent == null) {
            this.date = new Date(0);
        } else {
            this.date = new Date();
        }
        if (parentCommit != null) {
            this.parent = sha1(parentCommit);
        }
    }

    /** Method to update tracked files hashmap */
    public void updateTrackedFiles(HashMap object) {
        this.trackedFiles = object;
    }

}
