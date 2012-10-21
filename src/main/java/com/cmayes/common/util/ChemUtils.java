package com.cmayes.common.util;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.cmayes.common.model.Atom;

/**
 * Chemistry-related utility methods.
 * 
 * @author cmayes
 */
public final class ChemUtils {
    /** Default bond length in Angstroms. */
    public static final double BOND_LEN = 1.8;

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
    public static Vector3D vectorForAtom(final Atom atom) {
        return new Vector3D(atom.getX(), atom.getY(), atom.getZ());
    }

    /**
     * Returns whether the two atoms are within BOND_LEN of each other.
     * 
     * @param first
     *            The first atom to compare.
     * @param second
     *            The second atom to compare.
     * @return Whether the two atoms have a bond.
     */
    public static boolean hasBond(final Atom first, final Atom second) {
        return vectorForAtom(first).distance(vectorForAtom(second)) <= BOND_LEN;
    }

    /**
     * Converts phi in theta in degrees to an XYZ coordinate vector.
     * 
     * @param phi
     *            Phi in degrees.
     * @param theta
     *            Theta in degrees.
     * @return An XYZ coordinate vector for phi and theta.
     */
    public static Vector3D phiThetaToVector(final double phi, final double theta) {
        final double phiRad = Math.toRadians(phi);
        final double thetaRad = Math.toRadians(theta);
        final double x = Math.cos(phiRad) * Math.sin(thetaRad);
        final double y = Math.sin(phiRad) * Math.sin(thetaRad);
        final double z = Math.cos(thetaRad);
        return new Vector3D(x, y, z);
    }
}
