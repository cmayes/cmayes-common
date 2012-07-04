package com.cmayes.common.util;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.cmayes.common.model.Atom;

public final class ChemUtils {
    /** Default bond length in Angstroms. */
    private static final double BOND_LEN = 1.8;

    /**
     * Private constructor for util class.
     */
    private ChemUtils() {

    }

    /**
     * Returns a vector for the given atom.
     * 
     * @param atom
     *            The atom to convert.
     * @return The atom's XYZ as a {@link Vector3D}.
     */
    public static Vector3D vectorForAtom(Atom atom) {
        return new Vector3D(atom.getX(), atom.getY(), atom.getZ());
    }

    /**
     * @param first
     * @param second
     * @return
     */
    public static boolean hasBond(Atom first, Atom second) {
        return vectorForAtom(first).distance(vectorForAtom(second)) <= BOND_LEN;
    }

}
