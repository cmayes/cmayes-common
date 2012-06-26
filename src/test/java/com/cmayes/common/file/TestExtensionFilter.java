package com.cmayes.common.file;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test different extension filters for {@link ExtensionFilter}.
 * 
 * @author cmayes
 */
public class TestExtensionFilter {
    private static final String OUT_EXT = ".out";
    private static final String LOG_EXT = ".log";
    private static final List<String> EXT_LIST1 = Arrays.asList(LOG_EXT,
            OUT_EXT);

    @Test
    public void testIncluded() {
        ExtensionFilter filter = new ExtensionFilter(EXT_LIST1);
        assertTrue(filter.accept(null, "whatever.log"));
        assertFalse(filter.accept(null, "whateverlog"));
    }

    @Test
    public void testExcluded() {
        ExtensionFilter filter = new ExtensionFilter(EXT_LIST1);
        assertFalse(filter.accept(null, "whatever.txt"));
    }

    @Test
    public void testVarargs() {
        ExtensionFilter filter = new ExtensionFilter(OUT_EXT);
        assertTrue(filter.accept(null, "whatever.out"));
        assertFalse(filter.accept(null, "whatever.log"));
        assertThat(filter.getExtList().size(), equalTo(1));
        assertThat(filter.getExtList(), hasItem(OUT_EXT));
    }
}
