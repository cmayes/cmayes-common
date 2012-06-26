package com.cmayes.common.file;

import static org.junit.Assert.assertTrue;

import java.io.FilenameFilter;

import org.junit.Test;

/**
 * Ensures that {@link AcceptAllFilter} always returns true.
 * 
 * @author cmayes
 */
public class TestAcceptAllFilter {
    private static final FilenameFilter FILTER = new AcceptAllFilter();

    @Test
    public void testString() {
        assertTrue(FILTER.accept(null, "something"));
    }

    @Test
    public void testNull() {
        assertTrue(FILTER.accept(null, null));
    }
}
