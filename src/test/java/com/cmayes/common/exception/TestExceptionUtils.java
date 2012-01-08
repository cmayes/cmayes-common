package com.cmayes.common.exception;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

/**
 * Tests {@link ExceptionUtils}.
 * 
 * @author cmayes
 * 
 */
public class TestExceptionUtils {
    private static final String STRING_VALUE = "String value";
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

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNullArg() throws Exception {
        ExceptionUtils.checkNullArg(null, NULL_MSG);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAsNotNull() throws Exception {
        ExceptionUtils.asNotNull(null, NULL_MSG);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAsStringNull() throws Exception {
        ExceptionUtils.asString(null, NULL_MSG);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testAsString() throws Exception {
        assertThat(ExceptionUtils.asString(STRING_VALUE, NULL_MSG),
                equalTo(STRING_VALUE));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testAsNotBlank() throws Exception {
        assertThat(ExceptionUtils.asNotBlank(STRING_VALUE, NULL_MSG),
                equalTo(STRING_VALUE));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAsNotBlankNull() throws Exception {
        ExceptionUtils.asNotBlank(null, NULL_MSG);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAsNotBlankBlank() throws Exception {
        ExceptionUtils.asNotBlank("", NULL_MSG);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAsNotNullCollectionNull() throws Exception {
        ExceptionUtils.asNotNullCollection(Arrays.asList(STRING_VALUE, null),
                NULL_MSG);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testAsNotNullCollection() throws Exception {
        ExceptionUtils.asNotNullCollection(
                Arrays.asList(STRING_VALUE, NULL_MSG), NULL_MSG);
    }

}
