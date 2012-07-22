package com.cmayes.common.model.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.cmayes.common.chem.AtomicElement;
import com.cmayes.common.model.Atom;

/**
 * Default atom implementation.
 * 
 * @author cmayes
 */
public class DefaultAtom implements Atom {
    private double xPos;
    private double yPos;
    private double zPos;
    private int id;
    private AtomicElement type;

    /**
     * Zero-arg constructor.
     */
    public DefaultAtom() {

    }

    /**
     * Copy constructor.
     * 
     * @param copyMe
     *            The atom to copy.
     */
    public DefaultAtom(final Atom copyMe) {
        this.xPos = copyMe.getX();
        this.yPos = copyMe.getY();
        this.zPos = copyMe.getZ();
        this.id = copyMe.getId();
        this.type = copyMe.getType();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.cmayes.common.model.Atom#getX()
     */
    public double getX() {
        return xPos;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.cmayes.common.model.Atom#setX(double)
     */
    public void setX(final double xPosition) {
        this.xPos = xPosition;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.cmayes.common.model.Atom#getY()
     */
    public double getY() {
        return yPos;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.cmayes.common.model.Atom#setY(double)
     */
    public void setY(final double yPosition) {
        this.yPos = yPosition;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.cmayes.common.model.Atom#getZ()
     */
    public double getZ() {
        return zPos;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.cmayes.common.model.Atom#setZ(double)
     */
    public void setZ(final double zPosition) {
        this.zPos = zPosition;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.cmayes.common.model.Atom#getId()
     */
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.cmayes.common.model.Atom#setId(int)
     */
    public void setId(final int atomId) {
        this.id = atomId;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.cmayes.common.model.Atom#getType()
     */
    public AtomicElement getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.cmayes.common.model.Atom#setType(com.cmayes.common.chem.AtomicElement)
     */
    public void setType(final AtomicElement atomType) {
        this.type = atomType;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultAtom)) {
            return false;
        }
        final DefaultAtom rhs = (DefaultAtom) object;
        return new EqualsBuilder().append(this.id, rhs.id)
                .append(this.yPos, rhs.yPos).append(this.xPos, rhs.xPos)
                .append(this.zPos, rhs.zPos).append(this.type, rhs.type)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1651062229, 1240753677).append(this.id)
                .append(this.yPos).append(this.xPos).append(this.zPos)
                .append(this.type).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("type", this.type)
                .append("xPos", this.xPos).append("yPos", this.yPos)
                .append("zPos", this.zPos).append("id", this.id).toString();
    }
}
