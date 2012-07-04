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

}