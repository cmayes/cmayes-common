/*
 * EnvironmentException.java
 * 
 * Created on Dec 6, 2004
 * 
 * Copyright 2004 Chicago Mercantile Exchange Inc. All rights reserved.
 */
package com.cmayes.common.exception;

/**
 * Thrown when there are too many elements found.
 * 
 * @author cmayes
 */
public class TooManyException extends IllegalArgumentException {
    // ////////////////
    // Constructors //
    // ////////////////

    /** UID. */
    private static final long serialVersionUID = 5386482036368399967L;

    /**
     * @param message
     *            The exception message.
     * @param cause
     *            The wrapped exception.
     * @param vals
     *            Values to use when formatting the error message.
     */
    public TooManyException(final String message, final Throwable cause,
            final Object... vals) {
        super(String.format(message, vals), cause);
    }

    /**
     * @param message
     *            The exception message.
     * @param vals
     *            Values to use when formatting the error message.
     */
    public TooManyException(final String message, final Object... vals) {
        super(String.format(message, vals));
    }

    /**
     * @param cause
     *            The wrapped exception.
     */
    public TooManyException(final Throwable cause) {
        super(cause);
    }
}