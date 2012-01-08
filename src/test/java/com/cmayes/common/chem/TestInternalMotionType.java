package com.cmayes.common.chem;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests for {@link InternalMotionType}.
 * 
 * @author cmayes
 */
public class TestInternalMotionType {
    /**
     * Tests valueOfSymbol for good symbol.
     */
    @Test
    public void testValueOfSymbol() {
        assertThat(
                InternalMotionType.valueOfSymbol(InternalMotionType.ANGLE_BENDING
                        .getSymbol()),
                equalTo(InternalMotionType.ANGLE_BENDING));
    }

    /**
     * Tests valueOfSymbol for bad symbol.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfBadSymbol() {
        InternalMotionType.valueOfSymbol("DOES_NOT_EXIST");
    }

    /**
     * Tests valueOfSymbol for good symbol.
     */
    @Test
    public void testValueOf() {
        assertThat(
                InternalMotionType.valueOf(InternalMotionType.DIHEDRAL_ROTATION
                        .name()), equalTo(InternalMotionType.DIHEDRAL_ROTATION));
    }
}
