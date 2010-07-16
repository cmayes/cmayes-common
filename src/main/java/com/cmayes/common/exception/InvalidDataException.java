/*
 * EnvironmentException.java
 * 
 * Created on Dec 6, 2004
 * 
 * Copyright 2004 Chicago Mercantile Exchange Inc. All rights reserved.
 */
package com.cmayes.common.exception;

/**
 * Thrown when user-supplied data is invalid.
 * 
 * @author cmayes
 */
public class InvalidDataException extends IllegalArgumentException {
    // ////////////////
    // Constructors //
    // ////////////////

    /** UID. */
    private static final long serialVersionUID = 5386482036368399967L;

    /**
     * Creates the exception with a detail message and a wrapped exception.
     * 
     * @param aMessage
     *            The detail message for this exception.
     * @param aWrapped
     *            The exception to wrap.
     */
    public InvalidDataException(final String aMessage, final Throwable aWrapped) {
        super(aMessage, aWrapped);
    }

    /**
     * Creates the exception with a detail message.
     * 
     * @param aMessage
     *            The detail message for this exception.
     */
    public InvalidDataException(final String aMessage) {
        super(aMessage);
    }
}