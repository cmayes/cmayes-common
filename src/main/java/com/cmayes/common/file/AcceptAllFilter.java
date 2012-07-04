package com.cmayes.common.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Stub filter that accepts everything.
 * 
 * @author cmayes
 */
public class AcceptAllFilter implements FilenameFilter {

    /**
     * Always returns true. {@inheritDoc}
     * 
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    @Override
    public boolean accept(File dir, String name) {
        return true;
    }
}
