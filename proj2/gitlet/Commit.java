package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;

import static gitlet.Repository.*;
import static gitlet.Utils.*;


/** This class represents a commit object */

public class Commit implements Serializable {

    private String message;
    private Instant time;
    private String firstParent;
    private String secondParent;
    private HashMap trackedFiles; // Files updated in this commit
    private HashMap removedFiles; // Files removed in this commit
    private HashMap superFiles; // A map of ALL files and their latest versions

    // Constructor
    public Commit (String message, String parentCommit, String secondParentCommit) {

        this.message = message;
        this.trackedFiles = new HashMap();
        this.superFiles = new HashMap();
        this.removedFiles = new HashMap();
        if (firstParent == null) {
            this.time = Instant.EPOCH;
            this.firstParent = null;
            this.secondParent = null;
        } else if (secondParent == null) {
            this.time = Instant.now();
            this.firstParent = parentCommit;
            this.secondParent = null;
        } else {
            this.time = Instant.now();
            this.firstParent = parentCommit;
            this.secondParent = secondParentCommit;
        }
            // Copy parents superFiles hashmap
            File parentCommitFile = join(COMMITS_DIR, firstParent);
            Commit parentCommitObject = readObject(parentCommitFile, Commit.class);
            HashMap parentSuperFiles = parentCommitObject.superFiles;
            this.superFiles.putAll(parentSuperFiles);
    }

    public String getFirstParent() {
        return this.firstParent;
    }

    public String getSecondParent() {
        return this.secondParent;
    }

    public String getMessage() {
        return this.message;
    }

    public Instant getTime() {
        return this.time;
    }

    public HashMap getTrackedFiles() {
        return this.trackedFiles;
    }

    public HashMap getRemovedFiles() {
        return this.removedFiles;
    }

    public HashMap getSuperFiles() {
        return this.superFiles;
    }

}
