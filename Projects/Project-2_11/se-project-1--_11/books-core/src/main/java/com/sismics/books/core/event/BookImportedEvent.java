package com.sismics.books.core.event;

import java.io.File;

import com.google.common.base.Objects;
import com.sismics.books.core.model.jpa.User;

/**
 * Event raised on request to import books.
 *
 * @author bgamard 
 */
public class BookImportedEvent {
    /**
     * User requesting the import.
     */
    private User user;
    
    /**
     * Temporary file to import.
     */
    private File importFile;
    
    /**
     * Getter of user.
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter of user.
     *
     * @param user user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter of importFile.
     *
     * @return importFile
     */
    public File getImportFile() {
        return importFile;
    }

    /**
     * Setter of importFile.
     *
     * @param importFile importFile
     */
    public void setImportFile(File importFile) {
        this.importFile = importFile;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("user", user)
                .add("importFile", importFile)
                .toString();
    }
}
