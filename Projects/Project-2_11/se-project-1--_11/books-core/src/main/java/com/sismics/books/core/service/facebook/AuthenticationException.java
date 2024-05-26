package com.sismics.books.core.service.facebook;

/**
 * Exception raised on a Facebook authentication error.
 *
 * @author jtremeaux 
 */
public class AuthenticationException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of AuthenticationException.
     * 
     * @param message Message
     */
    public AuthenticationException(String message) {
        super(message);
    }
}
