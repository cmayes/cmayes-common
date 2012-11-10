package com.cmayes.common.exception;

/**
 * Extension of {@link IllegalArgumentException} that adds String.format
 * parameters.
 * 
 * @author cmayes
 */
public class ParamIllegalArgumentException extends IllegalArgumentException {

    /** UID. */
    private static final long serialVersionUID = 9020255807735636968L;

    /**
     * Zero-arg constructor.
     */
    public ParamIllegalArgumentException() {
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
    public ParamIllegalArgumentException(final String message,
            final Throwable cause, final Object... vals) {
        super(String.format(message, vals), cause);
    }

    /**
     * @param message
     *            The exception message.
     * @param vals
     *            Values to use when formatting the error message.
     */
    public ParamIllegalArgumentException(final String message,
            final Object... vals) {
        super(String.format(message, vals));
    }

    /**
     * @param cause
     *            The wrapped exception.
     */
    public ParamIllegalArgumentException(final Throwable cause) {
        super(cause);
    }
}
