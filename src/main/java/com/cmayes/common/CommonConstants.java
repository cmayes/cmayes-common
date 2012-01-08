package com.cmayes.common;

/**
 * Constants that are useful for general programming.
 * 
 * @author cmayes
 */
public final class CommonConstants {
    /** The classpath for this VM. */
    public static final String CLASSPATH = System
            .getProperty("java.class.path");
    /** The path separator used in the classpath. */
    public static final String PATH_SEP = System.getProperty("path.separator");
    /** The file separator used in the classpath. */
    public static final String FILE_SEP = System.getProperty("file.separator");
    /** The newline for this platform. */
    public static final String NL = System.getProperty("line.separator");
    /** The temporary directory for this platform. */
    public static final String TMPDIR = System.getProperty("java.io.tmpdir");

    /**
     * Private constructor for util classes.
     */
    private CommonConstants() {

    }
}
