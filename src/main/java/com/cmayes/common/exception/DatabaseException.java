package com.cmayes.common.exception;

/**
 * Thrown for exceptions caused by database issues.
 * 
 * @author cmayes
 */
public class DatabaseException extends EnvironmentException {
    /** UID. */
    private static final long serialVersionUID = -848349505100337315L;

    /**
     * Zero-arg constructor.
     */
    public DatabaseException() {
        super();
    }

    /**
     * @param message
     *            The exception message.
     * @param cause
     *            The wrapped exception.
     */
    public DatabaseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     *            The exception message.
     */
    public DatabaseException(final String message) {
        super(message);
    }

    /**
     * @param cause
     *            The wrapped exception.
     */
    public DatabaseException(final Throwable cause) {
        super(cause);
    }
}
