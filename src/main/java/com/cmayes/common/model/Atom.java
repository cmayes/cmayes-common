package com.cmayes.common.model;

import com.cmayes.common.chem.AtomicElement;

/**
 * Defines the properties of an atom.
 * 
 * @author cmayes
 */
public interface Atom {

    /**
     * 
     * @return the xPos
     */
    double getX();

    /**
     * @param xPosition
     *            the xPos to set
     */
    void setX(final double xPosition);

    /**
     * @return the yPos
     */
    double getY();

    /**
     * @param yPosition
     *            the yPos to set
     */
    void setY(final double yPosition);

    /**
     * @return the zPos
     */
    double getZ();

    /**
     * @param zPosition
     *            the zPos to set
     */
    void setZ(final double zPosition);

    /**
     * @return the id
     */
    int getId();

    /**
     * @param atomId
     *            the id to set
     */
    void setId(final int atomId);

    /**
     * @return the type
     */
    AtomicElement getType();

    /**
     * @param atomType
     *            the type to set
     */
    void setType(final AtomicElement atomType);

    /**
     * Centers the atom by subtracting the first, second, and third elements of
     * the array from the X, Y, and Z values of this atom.
     * 
     * @param xyzVals
     *            The three-value array containing the centering values.
     * @throws IllegalArgumentException
     *             If the values array has fewer than three elements.
     */
    void center(final double[] xyzVals);
}