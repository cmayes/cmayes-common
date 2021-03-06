package com.cmayes.common.exception;

/**
 * The base class for environment issues.
 * 
 * @author cmayes
 */
public class EnvironmentException extends RuntimeException {

    /** UID. */
    private static final long serialVersionUID = 9020255807735636968L;

    /**
     * Zero-arg constructor.
     */
    public EnvironmentException() {
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
    public EnvironmentException(final String message, final Throwable cause,
            final Object... vals) {
        super(String.format(message, vals), cause);
    }

    /**
     * @param message
     *            The exception message.
     * @param vals
     *            Values to use when formatting the error message.
     */
    public EnvironmentException(final String message, final Object... vals) {
        super(String.format(message, vals));
    }

    /**
     * @param cause
     *            The wrapped exception.
     */
    public EnvironmentException(final Throwable cause) {
        super(cause);
    }
}
