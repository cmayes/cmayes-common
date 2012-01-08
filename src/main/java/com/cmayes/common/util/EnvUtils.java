package com.cmayes.common.util;

import java.io.File;

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

}
