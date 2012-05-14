package com.cmayes.common.chem;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests for {@link AtomicElement}.
 * 
 * @author cmayes
 */
public class TestAtomicElement {
    /**
     * Tests that the numeric valueOf works.
     */
    @Test
    public void testNumericValueOf() {
        assertThat(AtomicElement.valueOf(AtomicElement.CARBON.getNumber()),
                equalTo(AtomicElement.CARBON));
    }

    /**
     * Tests that an invalid atomic number throws an
     * {@link IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNumericValueOfNegative() {
        AtomicElement.valueOf(-1);
    }

    /**
     * Tests that the stock valueOf works.
     */
    @Test
    public void testStockValueOf() {
        assertThat(AtomicElement.valueOf(AtomicElement.ARGON.name()),
                equalTo(AtomicElement.ARGON));
    }

    /**
     * Tests that the stock valueOf works.
     */
    @Test
    public void testSymbol() {
        assertThat(AtomicElement.OXYGEN.getSymbol(), equalTo("O"));
    }

    /**
     * Tests that the stock valueOf works.
     */
    @Test
    public void testMass() {
        assertThat(AtomicElement.NEON.getMass(), equalTo(20.1797));
    }
}
