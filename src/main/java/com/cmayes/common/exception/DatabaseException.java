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
     * @param vals
     *            Values to use when formatting the error message.
     */
    public DatabaseException(final String message, final Throwable cause,
            final Object... vals) {
        super(message, cause, vals);
    }

    /**
     * @param message
     *            The exception message.
     * @param vals
     *            Values to use when formatting the error message.
     */
    public DatabaseException(final String message, final Object... vals) {
        super(message, vals);
    }

    /**
     * @param cause
     *            The wrapped exception.
     */
    public DatabaseException(final Throwable cause) {
        super(cause);
    }
}
