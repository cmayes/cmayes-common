package com.cmayes.common.util;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.cmayes.common.model.Atom;

/**
 * Collection of chemistry-related utility methods.
 * 
 * @author cmayes
 */
public final class ChemUtils {
    /** Default bond length in Angstroms. */
    public static final double DEF_BOND_LEN = 1.7;

    /**
     * Private constructor for util class.
     */
    private ChemUtils() {

    }

    /**
     * Returns whether the two atoms have a bond using the default bond length.
     * 
     * @param a1
     *            The first atom.
     * @param a2
     *            The second atom.
     * @return whether the two atoms have a bond using the default bond length.
     */
    public static boolean hasBond(final Atom a1, final Atom a2) {
        return hasBond(a1, a2, DEF_BOND_LEN);
    }

    /**
     * Returns whether the two atoms have a bond using the given bond length.
     * 
     * @param a1
     *            The first atom.
     * @param a2
     *            The second atom.
     * @param bondLen
     *            The bond length in Angstroms to use.
     * @return whether the two atoms have a bond using the given bond length.
     */
    public static boolean hasBond(final Atom a1, final Atom a2,
            final double bondLen) {
        return vectorForAtom(a1).distance(vectorForAtom(a2)) < bondLen;
    }

    /**
     * Creates a {@link Vector3D} instance for the given atom.
     * 
     * @param atom
     *            The atom to convert.
     * @return A {@link Vector3D} instance for the given atom.
     */
    public static Vector3D vectorForAtom(final Atom atom) {
        return new Vector3D(atom.getX(), atom.getY(), atom.getZ());
    }
}
