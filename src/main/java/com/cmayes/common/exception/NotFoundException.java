package com.cmayes.common.exception;

/**
 * The base class for items not found.
 * 
 * @author cmayes
 */
public class NotFoundException extends RuntimeException {

    /** UID. */
    private static final long serialVersionUID = 9020255807735636968L;

    /**
     * Zero-arg constructor.
     */
    public NotFoundException() {
        super();
    }

    /**
     * @param message
     *            The exception message.
     * @param cause
     *            The wrapped exception.
     */
    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     *            The exception message.
     */
    public NotFoundException(final String message) {
        super(message);
    }

    /**
     * @param cause
     *            The wrapped exception.
     */
    public NotFoundException(final Throwable cause) {
        super(cause);
    }
}
