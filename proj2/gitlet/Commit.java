package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;

import static gitlet.Repository.*;
import static gitlet.Utils.*;


/** This class represents a commit object */

public class Commit implements Serializable {

    public String message;
    public Instant time;
    public String firstParent;
    public String secondParent;
    public HashMap trackedFiles; // Files updated in this commit
    public HashMap removedFiles; // Files removed in this commit
    public HashMap superFiles; // A map of ALL files and their latest versions

    // Constructor
    public Commit (String message, String parentID, String secondParentID) {

        this.message = message;
        this.trackedFiles = new HashMap();
        this.superFiles = new HashMap();
        this.removedFiles = new HashMap();

        // Mother commit base case
        if (parentID == null && secondParentID == null) {
            this.time = Instant.EPOCH;
            this.firstParent = null;
            this.secondParent = null;
        } else {
            this.time = Instant.now();
            this.firstParent = parentID;
            this.secondParent = secondParentID;
            // Copy parent super files hashmap
            File parentCommitFile = join(COMMITS_DIR, parentID);
            Commit parentCommitObject = readObject(parentCommitFile, Commit.class);
            HashMap parentSuperFiles = parentCommitObject.getSuperFiles();
            this.superFiles.putAll(parentSuperFiles);
        }

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
