package com.cmayes.common.exception;

import org.junit.Test;

/**
 * Tests {@link ExceptionUtils}.
 * 
 * @author cmayes
 * 
 */
public class TestExceptionUtils {
    /** The null message. */
    private static final String NULL_MSG = "Null.";

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test(expected = InvalidDataException.class)
    public void testCheckNull() throws Exception {
        ExceptionUtils.checkNull(null, NULL_MSG);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testCheckNullNot() throws Exception {
        ExceptionUtils.checkNull("not null", NULL_MSG);
    }

}
