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
     * @param anObj
     *            The object to check.
     * @param anErrMess
     *            The message to display after "Null object found: ".
     * @throws InvalidDataException
     *             If the given parameter is null.
     */
    public static void checkNull(final Object anObj, final String anErrMess) {
        if (anObj == null) {
            throw new InvalidDataException("Null object found: " + anErrMess);
        }
    }

    /**
     * Checks to see if <code>anObj</code> is null. Throws
     * IllegalArgumentException if it is.
     * 
     * @param anObj
     *            The object to check.
     * @param anErrMess
     *            The message to display after "Null object found: ".
     * @throws IllegalArgumentException
     *             If the given parameter is null.
     */
    public static void checkNullArg(final Object anObj, final String anErrMess) {
        if (anObj == null) {
            throw new IllegalArgumentException("Null argument: " + anErrMess);
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
     * @return The input.
     * @throws IllegalArgumentException
     *             When the input is null.
     */
    public static <T> T asNotNull(final T input, final String message) {
        if (null == input) {
            throw new IllegalArgumentException(message);
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
     * @return The input.
     * @throws IllegalArgumentException
     *             When the input is null.
     */
    public static String asString(final Object input, final String message) {
        if (null == input) {
            throw new IllegalArgumentException(message);
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
     * @return The input.
     * @throws IllegalArgumentException
     *             When the input is null.
     */
    public static String asNotBlank(final String input, final String message) {
        if ((null == input) || (input.length() == 0)) {
            throw new IllegalArgumentException(message);
        }

        return input;
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
     * @return The collection.
     * @throws IllegalArgumentException
     *             When anything is null.
     */
    public static <E> Collection<E> asNotNullCollection(
            final Collection<E> collection, final String errMess) {
        checkNullArg(collection, errMess);
        for (E element : collection) {
            checkNullArg(element, errMess);
        }
        return collection;
    }
}