package com.cmayes.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.cmayes.common.chem.AtomicElement;
import com.cmayes.common.exception.NotFoundException;
import com.cmayes.common.exception.TooManyException;
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
        return findDistance(first, second) <= BOND_LEN;
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

    /**
     * Finds the distance between the given carbon and the otherAtoms group of
     * the given type that has a bond with it the carbon.
     * 
     * @param curCarb
     *            The carb to check.
     * @param otherAtoms
     *            The list of atoms containing candidate elements (and a
     *            carbon).
     * @param elemType
     *            The element type to search for.
     * @return The distance.
     * @throws NotFoundException
     *             When the atom of the given element isn't found.
     */
    public static Double findBond(final Atom curCarb,
            final List<Atom> otherAtoms, final AtomicElement elemType) {
        return findDistance(curCarb,
                findBondAtom(curCarb, otherAtoms, elemType));
    }

    /**
     * Finds the atom of the given type from the given list that has a bond with
     * the given atom.  Note that this method will return the first atom that
     * meets the criteria; any other atoms that might meet the requirement are
     * not considered.
     * 
     * @param curCarb
     *            The carb to check.
     * @param otherAtoms
     *            The list of atoms containing candidate elements (and a
     *            carbon).
     * @param elemType
     *            The element type to search for.
     * @return The bonded atom of the given type.
     * @throws NotFoundException
     *             When the atom of the given element isn't found.
     */
    public static Atom findBondAtom(final Atom curCarb,
            final List<Atom> otherAtoms, final AtomicElement elemType) {
        for (Atom atom : otherAtoms) {
            if (hasBond(curCarb, atom) && elemType.equals(atom.getType())) {
                return atom;
            }
        }
        throw new NotFoundException(String.format("No %s found for carbon %s",
                elemType.name().toLowerCase(), curCarb));
    }

    /**
     * Finds the distance between the two atoms.
     * 
     * @param first
     *            The first atom.
     * @param second
     *            The second atom.
     * 
     * @return The distance between the two atoms.
     */
    public static Double findDistance(final Atom first, final Atom second) {
        return vectorForAtom(first).distance(vectorForAtom(second));
    }

    /**
     * Returns the single atom of the given type from the given collection.
     * 
     * @param elemType
     *            The element type to search for.
     * @param atoms
     *            The atom list to search.
     * @return The atom of the given type.
     * @throws NotFoundException
     *             When the atom of the given element isn't found.
     * @throws TooManyException
     *             When there is more than one atom of the given type in the
     *             given list.
     */
    public static Atom findSingle(final AtomicElement elemType,
            final List<Atom> atoms) {
        final List<Atom> allAtoms = findAllForType(elemType, atoms);
        if (allAtoms.isEmpty()) {
            throw new NotFoundException("No atoms of type %s found",
                    elemType.name());
        }
        if (allAtoms.size() > 1) {
            throw new TooManyException(
                    "%d atoms of type %s found where 1 was expected",
                    allAtoms.size(), elemType.name());
        }
        return allAtoms.get(0);
    }

    /**
     * Returns a list of all of the atoms of the given type. Note that the
     * collection retains the reference to the original atom from the given
     * list.
     * 
     * @param elemType
     *            The element type to search for.
     * @param atoms
     *            The atom list to search.
     * @return The atoms of the given type.
     */
    public static List<Atom> findAllForType(final AtomicElement elemType,
            final List<Atom> atoms) {
        final List<Atom> foundList = new ArrayList<Atom>();
        for (Atom atom : atoms) {
            if (elemType.equals(atom.getType())) {
                foundList.add(atom);
            }
        }
        return foundList;
    }
}
