package com.cmayes.common.file;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.CommonConstants;
import com.cmayes.common.util.EnvUtils;

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
    private static final File EXTDIR = new File(CommonConstants.TMPDIR,
            "testextdir");
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Creates an empty extdir.
     */
    @Before
    public void setUp() {
        if (EXTDIR.exists()) {
            EnvUtils.recursiveDelete(EXTDIR);
        }
        if (!EXTDIR.mkdirs()) {
            logger.warn("Couldn't create " + EXTDIR);
        }
    }

    /**
     * Deletes the extdir.
     */
    @After
    public void tearDown() {
        if (EXTDIR.exists()) {
            EnvUtils.recursiveDelete(EXTDIR);
        }
    }

    /**
     * Test.
     */
    @Test
    public void testIncluded() {
        final ExtensionFilter filter = new ExtensionFilter(EXT_LIST1);
        assertTrue(filter.accept(null, "whatever.log"));
        assertFalse(filter.accept(null, "whateverlog"));
    }

    /**
     * Test.
     */
    @Test
    public void testExcluded() {
        final ExtensionFilter filter = new ExtensionFilter(EXT_LIST1);
        assertFalse(filter.accept(null, "whatever.txt"));
    }

    /**
     * Test.
     */
    @Test
    public void testDir() {
        final File dir = new File(EXTDIR, "testdir");
        dir.mkdirs();
        final ExtensionFilter filter = new ExtensionFilter(EXT_LIST1);
        assertFalse(filter.accept(EXTDIR, dir.getAbsolutePath()));
    }

    /**
     * Test.
     */
    @Test
    public void testVarargs() {
        final ExtensionFilter filter = new ExtensionFilter(OUT_EXT);
        assertTrue(filter.accept(null, "whatever.out"));
        assertFalse(filter.accept(null, "whatever.log"));
        assertThat(filter.getExtList().size(), equalTo(1));
        assertThat(filter.getExtList(), hasItem(OUT_EXT));
    }
}
