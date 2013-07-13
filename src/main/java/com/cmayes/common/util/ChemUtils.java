package com.cmayes.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.util.FastMath;

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
     * Finds the distance between the given atom and the otherAtoms group of the
     * given type that has a bond with it the atom.
     * 
     * @param curCarb
     *            The atom to check.
     * @param otherAtoms
     *            The list of atoms containing candidate elements.
     * @param elemType
     *            The element type to search for.
     * @return The distance.
     * @throws NotFoundException
     *             When the atom of the given element isn't found.
     */
    public static Double findBond(final Atom curCarb,
            final List<Atom> otherAtoms, final AtomicElement elemType) {
        return findDistance(curCarb,
                findSingleBondAtom(curCarb, otherAtoms, elemType));
    }

    /**
     * Finds the atom of the given type from the given list that has a bond with
     * the given atom.
     * 
     * @param tgtAtom
     *            The atom to check.
     * @param otherAtoms
     *            The list of atoms containing candidate elements (and a atom).
     * @param elemType
     *            The element type to search for.
     * @return The bonded atom of the given type.
     * @throws NotFoundException
     *             When the atom of the given element isn't found.
     * @throws TooManyException
     *             If more than one bonded atom of the given type is found.
     */
    public static Atom findSingleBondAtom(final Atom tgtAtom,
            final List<Atom> otherAtoms, final AtomicElement elemType) {
        List<Atom> foundAtoms = findBondAtoms(tgtAtom, otherAtoms, elemType);

        switch (foundAtoms.size()) {
        case 0:
            throw new NotFoundException(String.format(
                    "No %s found for atom %s", elemType.name().toLowerCase(),
                    tgtAtom));
        case 1:
            return foundAtoms.get(0);
        default:
            throw new TooManyException(
                    "Found %d %s bonded atoms where one was expected for atom %s",
                    foundAtoms.size(), elemType, tgtAtom);
        }
    }

    /**
     * Finds all atoms of the given type from the given list that has a bond
     * with the given atom.
     * 
     * @param tgtAtom
     *            The atom to check.
     * @param otherAtoms
     *            The list of atoms to check.
     * @param elemType
     *            The element type to search for.
     * @return The bonded atoms of the given type.
     */
    public static List<Atom> findBondAtoms(final Atom tgtAtom,
            final List<Atom> otherAtoms, final AtomicElement elemType) {
        final List<Atom> founds = new ArrayList<Atom>();
        for (Atom atom : otherAtoms) {
            if (hasBond(tgtAtom, atom) && elemType.equals(atom.getType())) {
                founds.add(atom);
            }
        }
        return founds;
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

    /**
     * Finds the dihedral angle in degrees for the "arm" described by the given
     * four atoms.
     * 
     * Based on https://www.chemaxon.com/forum/dihedral_916-download200.java
     * 
     * @param atom1
     *            The first atom.
     * @param atom2
     *            The second atom.
     * @param atom3
     *            The third atom.
     * @param atom4
     *            The fourth atom.
     * @return The angle (in degrees) of the intersection of the two planes (1,
     *         2, 3) and (2, 3, 4) described by the given atoms.
     */
    public static double calcDihedralAngle(final Atom atom1, final Atom atom2,
            final Atom atom3, final Atom atom4) {
        final Vector3D atomVec1 = vectorForAtom(atom1);
        final Vector3D atomVec2 = vectorForAtom(atom2);
        final Vector3D atomVec3 = vectorForAtom(atom3);
        final Vector3D atomVec4 = vectorForAtom(atom4);

        Vector3D vec2ToVec1 = atomVec1.subtract(atomVec2);
        Vector3D vec2ToVec3 = atomVec3.subtract(atomVec2);
        // Normalized vector: length 1 with same angle.
        final Vector3D normVec1 = vec2ToVec1.crossProduct(vec2ToVec3);
        Vector3D vec3ToVec2 = atomVec2.subtract(atomVec3);
        Vector3D vec3ToVec4 = atomVec4.subtract(atomVec3);
        final Vector3D normVec2 = vec3ToVec2.crossProduct(vec3ToVec4);
        final double dhAngle = FastMath.toDegrees(Vector3D.angle(normVec1,
                normVec2));

        // Checks the sign of the dihedral angle.
        return normVec1.dotProduct(atomVec4) < 0 ? -dhAngle : dhAngle;
    }
}
