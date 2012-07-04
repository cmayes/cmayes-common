package com.cmayes.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Describes types of data formats.
 * 
 * @author cmayes
 */
public enum MediaType {
    TEXT("text/plain", "Plain text", Arrays.asList("txt")), LOG("text/plain",
            "Log file", Arrays.asList("log")), OUT("text/plain", "Output file",
            Arrays.asList("out")), CSV("text/csv", "Comma-separated values",
            Arrays.asList("csv")), JSON("application/json", "JSON", Arrays
            .asList("json", "jsn"));

    private final String mimeType;
    private final String description;
    private final List<String> extensions;

    /**
     * Creates a media type.
     * 
     * @param mime
     *            The MIME type.
     * @param desc
     *            The description of the media type.
     * @param ext
     *            The extensions used to identify the media type, with the
     *            primary extension as the first element.
     */
    private MediaType(final String mime, final String desc,
            final List<String> ext) {
        this.mimeType = mime;
        this.description = desc;
        this.extensions = Collections.unmodifiableList(ext);
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the primary extension used to identify the media type. This will
     * be used for naming files of this media type.
     * 
     * @return The primary extension.
     */
    public String getPrimaryExtension() {
        return this.extensions.get(0);
    }

    /**
     * Returns all recognized extensions for this media type with the primary
     * media type as the first element.
     * 
     * @return All known extensions for this media type.
     */
    public List<String> getAllExtensions() {
        return this.extensions;
    }
}
