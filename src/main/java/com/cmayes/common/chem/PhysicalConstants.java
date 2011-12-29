package com.cmayes.common.chem;

/**
 * Physical constants for use in calculations.
 * 
 * Structure follows an idea from {@link http
 * ://www.opentaps.org/docs/index.php/Java_Classes_for_Constants}
 * 
 * @author cmayes
 */
public final class PhysicalConstants {
    /** Gas constant J/Kmol. */
    public static final double GAS_JOULES = 8.314;
    /** Degrees Kelvin at 25 Celsius. */
    public static final double KELVIN_25C = 298.15;
    /** [kb] Boltzmann's Constant. (m^2 kg/s^2 Kelvin) */
    public static final double BOLTZ = 1.3806503e-23;
    /** [h] Planck's Constant. (m^2 kg/s) */
    public static final double PLANCK = 6.626068e-34;
    /** [R] Universal Gas Constant. KCal/mol */
    public static final double GAS_KCAL = 1.9872156e-3;
    /** [c] Speed of light. (cm/s) */
    public static final long LIGHT_CM = 29979245800L;
    /** [Na] Avogadro's number. */
    public static final double AVOGADRO = 6.0221415e23;
    /** Mass of an electron in kg. */
    public static final double MASS_ELEC_KG = 0.910938e-30;

    /**
     * Constants used for conversions between two units.
     */
    public static final class Conversions {
        public static final double HARTREE_TO_KCALTH = 627.509469;
        /** Thermal calorie to Joule. */
        public static final double CALTH_TO_JOULE = 4.184;
        /** Pascals to atmospheres. */
        public static final int ATM_TO_PASCALS = 101325;
        /** Hartrees per particle to Joules per mole. */
        public static final double HARTREES_TO_JOULES = HARTREE_TO_KCALTH
                * CALTH_TO_JOULE * 1000 / AVOGADRO;

        /**
         * Private constructor.
         */
        private Conversions() {
        }
    }

    /**
     * Private constructor.
     */
    private PhysicalConstants() {

    }
}
