package com.cmayes.common.chem;

import java.util.Comparator;

import com.cmayes.common.model.Atom;

/**
 * Compares atoms by ID in ascending order.
 * 
 * @author cmayes
 */
public class AtomIdComparator implements Comparator<Atom> {
    /**
     * Compares atoms by ID in ascending order. {@inheritDoc}
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(final Atom o1, final Atom o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
