package com.cmayes.common.chem;

/**
 * Represents atomic elements, providing the elements' chemical symbol, atomic
 * number, and atomic mass. Most atomic values from <a href="https://ptable.com/">PTable</a>.
 *
 * @author cmayes
 */
public enum AtomicElement {
    HYDROGEN("H", 1, 1.00794), HELIUM("He", 2, 4.002602), LITHIUM("Li", 3,
            6.941), BERYLLIUM("Be", 4, 9.012182), BORON("B", 5, 10.811), CARBON(
            "C", 6, 12.0107), NITROGEN("N", 7, 14.0067), OXYGEN("O", 8, 15.9994), FLUORINE(
            "F", 9, 18.9984032), NEON("Ne", 10, 20.1797), SODIUM("Na", 11,
            22.98976928), MAGNESIUM("Mg", 12, 24.3050), ALUMINIUM("Al", 13,
            26.9815386), SILICON("Si", 14, 28.0855), PHOSPHOROUS("P", 15,
            30.973762), SULFUR("S", 16, 32.065), CHLORINE("Cl", 17, 35.453), ARGON(
            "Ar", 18, 39.948), POTASSIUM("K", 19, 39.0983), CALCIUM("Ca", 20,
            40.078), SCANDIUM("Sc", 21, 44.956), TITANIUM("Ti",
            22, 47.867), VANADIUM("V", 23, 50.942), CHROMIUM("Cr", 24,
            51.996), MANGANESE("Ma", 25, 54.938), IRON("Fe", 26,
            55.845), COBALT("Co", 27, 58.933), NICKEL("Ni", 28,
            58.693), COPPER("Cu", 29, 63.546), ZINC("Zn", 30,
            65.38), GALLIUM("Ga", 31, 69.723), GERMANIUM("Ge", 32,
            72.630), ARSENIC("As", 33, 74.922), SELENIUM("Se", 34,
            78.971), BROMINE("Br", 35, 79.904), KRYPTON("Kr", 36,
            83.798), RUBIDIUM("Rb", 37, 85.468), STRONTIUM("Sr", 38,
            87.62), YTTRIUM("Y", 39, 88.906), ZIRCONIUM("Zr", 40,
            91.224), NIOBIUM("Nb", 41, 92.906), MOLYBDENUM("Mo", 42,
            95.95), TECHNETIUM("Tc", 43, 98), RUTHENIUM("Ru", 44,
            101.07), RHODIUM("Rh", 45, 102.91), PALLADIUM("Pd", 46,
            106.42), SILVER("Ag", 47, 107.87), CADMIUM("Cd", 48,
            112.41), INDIUM("In", 49, 114.82), TIN("Sn", 50,
            118.71), ANTIMONY("Sb", 51, 121.76), TELLURIUM("Te", 52,
            127.60), IODINE("I", 53, 126.90), XENON("Xe", 54,
            131.29), CESIUM("Cs", 55, 132.91), BARIUM("Ba", 56,
            137.33), LUTETIUM("Lu", 71, 174.97), HAFNIUM("Hf", 72,
            178.49), TANTALUM("Ta", 73, 180.95), TUNGSTEN("W", 74,
            183.84), RHENIUM("Re", 75, 186.21), OSMIUM("Os", 76,
            190.23), IRIDIUM("Ir", 77, 192.22), PLATINUM("Pt", 78,
            195.08), GOLD("Au", 79, 196.97), MERCURY("Hg", 80,
            200.59), THALLIUM("Tl", 81, 204.38), LEAD("Pb", 82, 207.2);

    private final String symbol;
    private final int atomicNumber;
    private final double atomicMass;

    /**
     * Creates an element with the given symbol and number.
     *
     * @param sym  The chemical symbol for this element.
     * @param num  The atomic number for this element.
     * @param mass The atomic mass for this element.
     */
    AtomicElement(final String sym, final int num, final double mass) {
        this.symbol = sym;
        this.atomicNumber = num;
        this.atomicMass = mass;
    }

    /**
     * Returns the chemical symbol for this element.
     *
     * @return The chemical symbol for this element.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the atomic number for this element.
     *
     * @return The atomic number for this element.
     */
    public int getNumber() {
        return atomicNumber;
    }

    /**
     * Returns the atomic mass for this element.
     *
     * @return The atomic mass of this element.
     */
    public double getMass() {
        return atomicMass;
    }

    /**
     * Look up an element by atomic number.
     *
     * @param number The atomic number to look up.
     * @return The element matching the given number.
     * @throws IllegalArgumentException If not elements match the given number.
     */
    public static AtomicElement valueOf(final int number) {
        for (AtomicElement val : AtomicElement.values()) {
            if (number == val.getNumber()) {
                return val;
            }
        }
        throw new IllegalArgumentException("No element number " + number);
    }
}
