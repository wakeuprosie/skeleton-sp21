package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.*;
import java.util.HashMap;

import static gitlet.Repository.CWD;
import static gitlet.Utils.*;


/** Commit object */

public class Commit implements Serializable {

    private String message;
    private Instant time;
    private String parent;
    public HashMap trackedFiles; // Files updated in this commit
    public HashMap superFiles; // A map of ALL files and their latest versions

    /** Constructor */
    public Commit (String message, String parentCommit) {
        // Commit object
        this.message = message;
        if (parentCommit == null) {
            this.time = Instant.EPOCH;
            this.superFiles = new HashMap();
        } else {
            this.time = Instant.now();
            this.parent = sha1(parentCommit);

            // Access parents superFiles hashmap
            File parentCommitFile = join(CWD, ".gitlet", "commits", parentCommit);
            Commit parentCommitobject = readObject(parentCommitFile, Commit.class);
            HashMap parentSuperFiles = parentCommitobject.superFiles;

            // Copy parent superFiles to this commit
            this.superFiles.putAll(parentSuperFiles);
        }
        this.trackedFiles = new HashMap();
    }

    public String getParent() {
        return this.parent;
    }

}
