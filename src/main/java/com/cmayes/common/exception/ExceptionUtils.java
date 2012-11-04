package com.cmayes.common.exception;

import java.util.Collection;

/**
 * Utility methods for generating and handling exceptions.
 * 
 * @author cmayes
 */
public final class ExceptionUtils {

    /**
     * Access utility classes statically.
     */
    private ExceptionUtils() {
    }

    /**
     * Checks to see if <code>anObj</code> is null. Throws InvalidDataException
     * if it is.
     * 
     * @param obj
     *            The object to check.
     * @param errMess
     *            The message to display.
     * @param fmtArgs
     *            The string format arguments for the error message.
     * @throws InvalidDataException
     *             If the given parameter is null.
     */
    public static void checkNull(final Object obj, final String errMess,
            final Object... fmtArgs) {
        if (obj == null) {
            throw new InvalidDataException(String.format(errMess, fmtArgs));
        }
    }

    /**
     * Checks to see if <code>anObj</code> is null. Throws
     * IllegalArgumentException if it is.
     * 
     * @param obj
     *            The object to check.
     * @param errMess
     *            The message to display.
     * @param fmtArgs
     *            The string format arguments for the error message.
     * @throws IllegalArgumentException
     *             If the given parameter is null.
     */
    public static void checkNullArg(final Object obj, final String errMess,
            final Object... fmtArgs) {
        if (obj == null) {
            throw new IllegalArgumentException(String.format(errMess, fmtArgs));
        }
    }

    /**
     * Returns the instance, throwing an IllegalArgumentException if the input
     * is null.
     * 
     * @param <T>
     *            The type of the instance being checked.
     * @param input
     *            The input to check.
     * @param message
     *            The message to display if the instance is null.
     * @param fmtArgs
     *            The string format arguments for the error message.
     * @return The input.
     * @throws IllegalArgumentException
     *             When the input is null.
     */
    public static <T> T asNotNull(final T input, final String message,
            final Object... fmtArgs) {
        if (null == input) {
            throw new IllegalArgumentException(String.format(message, fmtArgs));
        }

        return input;
    }

    /**
     * Returns the instance, throwing an IllegalArgumentException if the input
     * is null.
     * 
     * @param input
     *            The input to check.
     * @param message
     *            The message to display if the instance is null.
     * @param fmtArgs
     *            The string format arguments for the error message.
     * @return The input.
     * @throws IllegalArgumentException
     *             When the input is null.
     */
    public static String asString(final Object input, final String message,
            final Object... fmtArgs) {
        if (null == input) {
            throw new IllegalArgumentException(String.format(message, fmtArgs));
        }

        return input.toString();
    }

    /**
     * Returns the instance, throwing an IllegalArgumentException if the input
     * is null or blank (i.e. zero length).
     * 
     * @param input
     *            The input to check.
     * @param message
     *            The message to display if the instance is null.
     * @param fmtArgs
     *            The string format arguments for the error message.
     * @return The input.
     * @throws IllegalArgumentException
     *             When the input is null.
     */
    public static String asNotBlank(final String input, final String message,
            final Object... fmtArgs) {
        if ((null == input) || (input.length() == 0)) {
            throw new IllegalArgumentException(String.format(message, fmtArgs));
        }

        return input;
    }

    /**
     * Returns the int if it is positive.
     * 
     * @param input
     *            The input to check.
     * @param message
     *            The message to display if the int is not positive.
     * @param fmtArgs
     *            The string format arguments for the error message.
     * @return The input.
     * @throws IllegalArgumentException
     *             When the int is not positive.
     */
    public static int asPositive(final int input, final String message,
            final Object... fmtArgs) {
        if (input <= 0) {
            throw new IllegalArgumentException(String.format(message, fmtArgs));
        }

        return input;
    }

    /**
     * Returns the long if it is positive.
     * 
     * @param input
     *            The input to check.
     * @param message
     *            The message to display if the long is not positive.
     * @param fmtArgs
     *            The string format arguments for the error message.
     * @return The input.
     * @throws IllegalArgumentException
     *             When the long is not positive.
     */
    public static long asPositive(final long input, final String message,
            final Object... fmtArgs) {
        if (input <= 0) {
            throw new IllegalArgumentException(String.format(message, fmtArgs));
        }

        return input;
    }

    /**
     * Returns the int if it is positive. Uses default exception message.
     * 
     * @param input
     *            The input to check.
     * @return The input.
     * @throws IllegalArgumentException
     *             When the int is not positive.
     */
    public static int asPositive(final int input) {
        return asPositive(input, "Value '%d' is not positive", input);
    }

    /**
     * Returns the long if it is positive. Uses default exception message.
     * 
     * @param input
     *            The input to check.
     * @return The input.
     * @throws IllegalArgumentException
     *             When the long is not positive.
     */
    public static long asPositive(final long input) {
        return asPositive(input, "Value '%d' is not positive", input);
    }

    /**
     * Ensures that neither the collection nor any member is null.
     * 
     * @param <E>
     *            The type of the collection.
     * @param collection
     *            The collection to check.
     * @param errMess
     *            The error message if any are null.
     * @param fmtArgs
     *            The string format arguments for the error message.
     * @return The collection.
     * @throws IllegalArgumentException
     *             When anything is null.
     */
    public static <E> Collection<E> asNotNullCollection(
            final Collection<E> collection, final String errMess,
            final Object... fmtArgs) {
        checkNullArg(collection, errMess, fmtArgs);
        for (E element : collection) {
            checkNullArg(element, errMess, fmtArgs);
        }
        return collection;
    }
}