package com.cmayes.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.exception.NotFoundException;

/**
 * A collection of data format utilities.
 * 
 * @author cmayes
 */
public final class FormatUtils {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(FormatUtils.class);
    private String value;

    /**
     * Private utility constructor.
     */
    private FormatUtils() {

    }

    /**
     * Finds the header with the given column name.
     * 
     * @param headerRow
     *            The header row to search.
     * @param colName
     *            The column name to search for.
     * @return The index of the given column name.
     * @throws NotFoundException
     *             If the column name is not found in the header.
     */
    public static int findIdx(final String[] headerRow, final String colName) {
        for (int i = 0; i < headerRow.length; i++) {
            if (headerRow[i].equalsIgnoreCase(colName)) {
                return i;
            }
        }
        throw new NotFoundException("No header entry for " + colName);
    }

    /**
     * Parses value into a Double.
     * 
     * @param strVal
     *            The string value to convert.
     * @param valLabel
     *            The label for this value (for error messages).
     * @return The double value or null if the parse fails.
     */
    public static Double toDouble(final String strVal, final String valLabel) {
        final String errMsg = String.format(
                "Couldn't parse double %s for field %s", strVal, valLabel);
        try {
            return Double.valueOf(strVal);
        } catch (final NumberFormatException e) {
            LOGGER.error(errMsg, e);
            throw new IllegalArgumentException(errMsg, e);
        }
    }
}
