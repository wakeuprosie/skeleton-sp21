package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;

import static gitlet.Repository.COMMITS_DIR;
import static gitlet.Repository.currentBranch;
import static gitlet.Utils.*;


/** Commit object */

public class Commit implements Serializable {

    private String message;
    // private Date time;
    private Instant time;
    // private LocalDateTime time;
    private String parent;
    public HashMap trackedFiles; // Files updated in this commit
    public HashMap superFiles; // A map of ALL files and their latest versions
    public String ownerBranch; // The branch this commit belongs to

    /** Constructor */
    public Commit (String message, String parentCommit) {
        // Commit object
        this.message = message;
        this.trackedFiles = new HashMap();
        this.superFiles = new HashMap();
        if (parentCommit == null) {
            // this.time = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.ofHours(0));
            this.time = Instant.EPOCH;
            this.ownerBranch = "master";
        } else {
            this.parent = parentCommit;
            this.time = Instant.now();
            this.ownerBranch = currentBranch;

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
