package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.*;
import java.util.HashMap;

import static gitlet.Repository.COMMITS_DIR;
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
        this.trackedFiles = new HashMap();
        this.superFiles = new HashMap();
        if (parentCommit == null) {
            this.time = Instant.EPOCH;
        } else {
            this.parent = parentCommit;
            this.time = Instant.now();

            // Access parents superFiles hashmap
            File parentCommitFile = join(COMMITS_DIR, parentCommit);
            Commit parentCommitObject = readObject(parentCommitFile, Commit.class);
            HashMap parentSuperFiles = parentCommitObject.superFiles;

            // Copy parent superFiles to this commit
            this.superFiles.putAll(parentSuperFiles);
        }
    }

    public String getParent() {
        return this.parent;
    }

    public String getMessage() {
        return this.message;
    }

    public Instant getTime() {
        return this.time;
    }

}
