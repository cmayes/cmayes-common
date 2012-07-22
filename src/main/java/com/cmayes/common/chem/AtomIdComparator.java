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
        // Integer.compare from JDK 7
        final int x = o1.getId();
        final int y = o2.getId();
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}
