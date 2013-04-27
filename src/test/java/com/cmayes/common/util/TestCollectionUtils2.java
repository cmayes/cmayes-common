package com.cmayes.common.util;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.cmayes.common.exception.NotFoundException;
import com.cmayes.common.exception.TooManyException;

/**
 * Tests for {@link CollectionUtils2}.
 * 
 * @author cmayes
 */
public class TestCollectionUtils2 {
    private static final String SINGLE_VAL = "Single";
    private static final String[] STR_NUMS = { "1", "19", "-2" };

    /**
     * Basic case for collection conversion.
     */
    @Test
    public void testConvertCollection() {
        final Collection<Integer> intVals = CollectionUtils2.convertCollection(
                Integer.class, Arrays.asList(STR_NUMS));
        assertTrue(intVals.size() == STR_NUMS.length);
        assertThat(intVals, hasItems(1, 19, -2));
    }

    /**
     * Invalid case for collection conversion.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertCollectionInvalid() {
        CollectionUtils2.convertCollection(Integer.class,
                Arrays.asList("1", "badnum", "-2"));
    }

    /**
     * Creates a list from an array of values.
     */
    @Test
    public void testCreateList() {
        final List<String> list = CollectionUtils2.createList(STR_NUMS);
        assertThat(list, hasItems(STR_NUMS));
        assertTrue(list.size() == STR_NUMS.length);
    }

    /**
     * Creates an empty list.
     */
    @Test
    public void testCreateListNull() {
        final List<String> list = CollectionUtils2.createList((String[]) null);
        assertTrue(list.isEmpty());
    }

    /**
     * Create a set from an array of values.
     */
    @Test
    public void testCreateSet() {
        final Set<String> set = CollectionUtils2.createSet(STR_NUMS);
        assertThat(set, hasItems(STR_NUMS));
        assertTrue(set.size() == STR_NUMS.length);
    }

    /**
     * Creates an empty set.
     */
    @Test
    public void testCreateSetNull() {
        final Set<String> set = CollectionUtils2.createSet((String[]) null);
        assertTrue(set.isEmpty());
    }

    /**
     * Get a single value from a set.
     */
    @Test
    public void testGetSingleSet() {
        final Set<String> set = CollectionUtils2.createSet(SINGLE_VAL);
        assertTrue(SINGLE_VAL.equals(CollectionUtils2.getSingle(set)));
    }

    /**
     * Get a single value from a list.
     */
    @Test
    public void testGetSingleList() {
        final List<String> list = CollectionUtils2.createList(SINGLE_VAL);
        assertTrue(SINGLE_VAL.equals(CollectionUtils2.getSingle(list)));
    }

    /**
     * Throws not found for empty collection.
     */
    @Test(expected = NotFoundException.class)
    public void testGetSingleZero() {
        CollectionUtils2.getSingle(new ArrayList<String>());
    }

    /**
     * Throws too few for empty collection.
     */
    @Test(expected = TooManyException.class)
    public void testGetSingleMany() {
        CollectionUtils2.getSingle(CollectionUtils2.createList(STR_NUMS));
    }

    /**
     * Get a first value from a set.
     */
    @Test
    public void testGetFirstSet() {
        final Set<String> set = CollectionUtils2.createSet(SINGLE_VAL);
        assertTrue(SINGLE_VAL.equals(CollectionUtils2.getFirst(set)));
    }

    /**
     * Get a single value from a list.
     */
    @Test
    public void testGetFirstList() {
        final List<String> list = CollectionUtils2.createList(SINGLE_VAL);
        assertTrue(SINGLE_VAL.equals(CollectionUtils2.getFirst(list)));
    }

    /**
     * Throws not found for empty collection.
     */
    @Test(expected = NotFoundException.class)
    public void testGetFirstZero() {
        CollectionUtils2.getFirst(new ArrayList<String>());
    }

    /**
     * Gets the first element.
     */
    @Test
    public void testGetFirstMany() {
        String first = CollectionUtils2.getFirst(CollectionUtils2
                .createList(STR_NUMS));
        assertEquals(STR_NUMS[0], first);
    }
}
