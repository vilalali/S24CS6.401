package com.sismics.books.core.service.facebook;

/**
 * Exception raised on a Facebook permission not found.
 *
 * @author jtremeaux 
 */
public class PermissionException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of PermissionException.
     * 
     * @param message Message
     */
    public PermissionException(String message) {
        super(message);
    }
}
