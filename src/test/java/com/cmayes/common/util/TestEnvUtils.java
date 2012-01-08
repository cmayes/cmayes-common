package com.cmayes.common.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.cmayes.common.CommonConstants;

/**
 * Tests for {@link EnvUtils}.
 * 
 * @author cmayes
 */
public class TestEnvUtils {
    private static final String TEST_ROOT_DIR = "testRootDir";
    private static final String TEST_SUB_DIR_SEG = "testSubDir";
    private static final String TEST_SUB_DIR = TEST_ROOT_DIR
            + CommonConstants.FILE_SEP + TEST_SUB_DIR_SEG;

    /**
     * Tries to clean up from any faulty tests.
     */
    @Before
    public void setUp() {
        EnvUtils.recursiveDelete(new File(TEST_ROOT_DIR));
    }

    /**
     * Tests deleting an empty dir.
     */
    @Test
    public void testRecursiveDeleteEmpty() {
        final File rootDir = new File(TEST_ROOT_DIR);
        mkDir(rootDir);
        assertTrue(rootDir.exists());
        EnvUtils.recursiveDelete(rootDir);
        assertFalse(rootDir.exists());
    }

    /**
     * Tests deleting a dir with nested files.
     * 
     * @throws Exception
     *             If there are problems setting up the test.
     */
    @Test
    public void testRecursiveDeleteFiles() throws Exception {
        final File rootDir = new File(TEST_ROOT_DIR);
        mkDir(rootDir);
        assertTrue(rootDir.exists());
        final File subFile1 = new File(TEST_ROOT_DIR + CommonConstants.FILE_SEP
                + "testFile1");
        mkFile(subFile1);
        final File subFile2 = new File(TEST_ROOT_DIR + CommonConstants.FILE_SEP
                + "testFile2");
        mkFile(subFile2);
        EnvUtils.recursiveDelete(rootDir);
        assertFalse(rootDir.exists());
    }

    /**
     * Tests deleting a dir with nested files and directories.
     * 
     * @throws Exception
     *             If there are problems setting up the test.
     */
    @Test
    public void testRecursiveDeleteFilesAndDirs() throws Exception {
        final File subDir = new File(TEST_SUB_DIR);
        mkDir(subDir);
        assertTrue(subDir.exists());
        final File subFile1 = new File(TEST_SUB_DIR + CommonConstants.FILE_SEP
                + "testFile1");
        mkFile(subFile1);
        final File subFile2 = new File(TEST_ROOT_DIR + CommonConstants.FILE_SEP
                + "testFile2");
        mkFile(subFile2);
        final File rootDir = new File(TEST_ROOT_DIR);
        EnvUtils.recursiveDelete(rootDir);
        assertFalse(rootDir.exists());
    }

    /**
     * Creates a file at the given location.
     * 
     * @param file
     *            The file to create.
     * @throws IOException
     *             When there are problems creating the file.
     */
    private void mkFile(final File file) throws IOException {
        if (!file.createNewFile()) {
            fail(String
                    .format("Failed to create %s.  Try manually deleting if it exists.",
                            TEST_ROOT_DIR));
        }
    }

    /**
     * Creates a directory at the given location.
     * 
     * @param dir
     *            The directory to create.
     */
    private void mkDir(final File dir) {
        if (!dir.mkdirs()) {
            fail(String
                    .format("Failed to create %s.  Try manually deleting if it exists.",
                            TEST_ROOT_DIR));
        }
    }
}
