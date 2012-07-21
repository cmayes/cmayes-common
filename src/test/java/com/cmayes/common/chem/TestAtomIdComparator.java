package com.cmayes.common.chem;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.cmayes.common.model.Atom;
import com.cmayes.common.model.impl.DefaultAtom;

/**
 * Tests for {@link AtomIdComparator}.
 * 
 * @author cmayes
 */
public class TestAtomIdComparator {
    private static final AtomIdComparator COMP = new AtomIdComparator();

    /**
     * Tests two sequential IDs.
     */
    @Test
    public void testSimple() {
        final Atom a1 = new DefaultAtom();
        a1.setId(1);
        final Atom a2 = new DefaultAtom();
        a2.setId(2);
        assertThat(COMP.compare(a1, a2), lessThan(0));
    }

    /**
     * Tests that an unsorted list is sorted.
     */
    @Test
    public void testUnsortedList() {
        final Atom a1 = new DefaultAtom();
        a1.setId(1);
        final Atom a2 = new DefaultAtom();
        a2.setId(2);
        final List<Atom> unord = new ArrayList<Atom>();
        unord.add(a2);
        unord.add(a1);
        Collections.sort(unord, COMP);
        assertEquals(unord.get(0), a1);
        assertEquals(unord.get(1), a2);
    }
}
