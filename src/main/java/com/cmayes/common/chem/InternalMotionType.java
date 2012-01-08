package com.cmayes.common.chem;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

/**
 * Represents an internal motion type.
 * 
 * @author cmayes
 */
public enum InternalMotionType {
    BOND_STRETCHING("R"), ANGLE_BENDING("A"), DIHEDRAL_ROTATION("D");

    private final String symbol;

    /**
     * Creates a motion type with the given symbol.
     * 
     * @param sym
     *            The one-letter symbol representing this internal motion type.
     */
    private InternalMotionType(final String sym) {
        this.symbol = asNotNull(sym, "Symbol is null");
    }

    /**
     * Look up a type by single-letter symbol.
     * 
     * @param sym
     *            The one-letter symbol to look up.
     * @return The type matching the given symbol.
     * @throws IllegalArgumentException
     *             If no types match the given symbol.
     */
    public static InternalMotionType valueOfSymbol(final String sym) {
        for (InternalMotionType val : InternalMotionType.values()) {
            if (val.getSymbol().equals(sym)) {
                return val;
            }
        }
        throw new IllegalArgumentException("No motion type for symbol " + sym);
    }

    /**
     * @return The one-letter symbol for this internal motion type.
     */
    public String getSymbol() {
        return symbol;
    }
}
