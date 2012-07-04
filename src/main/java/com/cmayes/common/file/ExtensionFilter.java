package com.cmayes.common.file;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

/**
 * Filters filenames by extension.
 */
public class ExtensionFilter implements FilenameFilter {

    /** List of extensions to filter on. */
    private final Collection<String> extList;

    private final Pattern extPat;

    /**
     * Creates a file filter that selects files with the given format list. Be
     * sure to include any needed prefixes (a dot, etc.).
     * 
     * @param exts
     *            The format to filter for.
     */
    public ExtensionFilter(final Collection<String> exts) {
        this.extList = Collections.unmodifiableCollection(asNotNull(exts,
                "Extensions are null"));
        final Collection<String> escExts = Collections2.transform(extList,
                new Function<String, String>() {
                    public String apply(final String input) {
                        return Pattern.quote(input);
                    }
                });
        extPat = Pattern.compile(String.format(".*(%s)$",
                StringUtils.join(escExts, "|")));
    }

    /**
     * Creates a file filter that selects files with the given format list. Be
     * sure to include any needed prefixes (a dot, etc.).
     * 
     * @param exts
     *            The format to filter for.
     */
    public ExtensionFilter(final String... exts) {
        this(Arrays.asList(exts));
    }

    /**
     * Tests if a specified file should be included in a file list. Directories
     * always return true.
     * 
     * @param dir
     *            the directory in which the file was found.
     * @param name
     *            the name of the file.
     * @return <code>true</code> if and only if the name should be included in
     *         the file list; <code>false</code> otherwise.
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    public boolean accept(final File dir, final String name) {
        final File tgtFile = new File(dir, name);
        if (tgtFile.isDirectory()) {
            return true;
        }
        return extPat.matcher(name).matches();
    }

    /**
     * Returns a read-only view of the extensions that are included in this
     * filter.
     * 
     * @return The extensions allowed by this filter.
     */
    public Collection<String> getExtList() {
        return extList;
    }
}
