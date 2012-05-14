package com.cmayes.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.cmayes.common.exception.EnvironmentException;

/**
 * Utilities for getting resources from the environment.
 */
public final class EnvUtils {

    /**
     * No public constructor for util classes.
     */
    private EnvUtils() {

    }

    /**
     * Deletes the given file location. If the file is a directory and contains
     * files or directories, those sub-locations are also deleted.
     * 
     * @param file
     *            The location to delete.
     */
    public static void recursiveDelete(final File file) {
        if (file.isFile()) {
            final boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw new EnvironmentException(
                        "Failed to recursively delete location "
                                + file.getAbsolutePath());
            }
        } else if (file.isDirectory()) {
            final File[] subFiles = file.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                recursiveDelete(subFiles[i]);
            }
            final boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw new EnvironmentException(
                        "Failed to recursively delete location "
                                + file.getAbsolutePath());
            }
        }

        if (file.exists()) {
            throw new EnvironmentException(
                    "Failed to recursively delete location "
                            + file.getAbsolutePath());
        }
    }

    /**
     * Loads a resource from the classpath.
     * 
     * @param aResName
     *            The resource to fetch.
     * @return A handle for the resource or null if the resource is not found.
     */
    public static String getResourceAsString(String aResName) {
        Reader resourceReader = getResourceReader(aResName);
        if (resourceReader == null) {
            return null;
        }
        return getStringFromReader(resourceReader);
    }

    /**
     * Loads a resource from the classpath.
     * 
     * @param aResName
     *            The resource to fetch.
     * @return A handle for the resource or null if the resource is not found.
     */
    public static Reader getResourceReader(String aResName) {
        InputStream resource = getResource(aResName);
        if (resource == null) {
            return null;
        }
        return new InputStreamReader(resource);
    }

    /**
     * Loads a resource from the classpath.
     * 
     * @param aResName
     *            The resource to fetch.
     * @return A handle for the resource or null if the resource is not found.
     */
    public static InputStream getResource(String aResName) {
        return EnvUtils.class.getClassLoader().getResourceAsStream(aResName);
    }

    /**
     * Returns the data in the InputStream as a String.
     * 
     * @param aData
     *            The data to process.
     * @return The processed data as a String.
     */
    public static String getStringFromReader(Reader aData) {
        return getStringFromReader(aData, true);
    }

    /**
     * Returns the data in the InputStream as a String.
     * 
     * @param aData
     *            The data to process.
     * @param isAddNewlines
     *            Whether to add newlines at the end of each line in the file.
     * @return The processed data as a String.
     */
    public static String getStringFromReader(Reader aData, boolean isAddNewlines) {
        String thisLine;
        StringBuffer buf = new StringBuffer();
        BufferedReader br = new BufferedReader(aData);
        try {
            while ((thisLine = br.readLine()) != null) {
                buf.append(thisLine);
                if (isAddNewlines) {
                    buf.append(System.getProperty("line.separator"));
                }
            }
            aData.close();
        } catch (IOException e) {
            throw new EnvironmentException("Problems reading response stream: "
                    + e.getMessage(), e);
        }
        return buf.toString();
    }

}
