package pl.ksr.summarizator.model;

public class DefinedSubjects {
    public static class FuelType {
        public static String getValueName() {
            return "fuel_type";
        }

        public static String getName() {
            return "TYP PALIWA";
        }

        public static String getTypeDiesel() {
            return "Diesel";
        }

        public static String getTypeGasoline() {
            return "Gasoline";
        }

        public static String getTypeGas() {
            return "Gas";
        }

        public static String getTypeHybrid() {
            return "Hybrid";
        }
    }

    public static class TransmissionType {
        public static String getValueName() {
            return "transmission";
        }

        public static String getName() {
            return "RODZAJ SKRZYNI BIEGÓW";
        }

        public static String getTypeManual() {
            return "Manual";
        }

        public static String getTypeAutomatic() {
            return "Automatic";
        }
    }

    public static class BodyType {
        public static String getValueName() {
            return "body_type";
        }

        public static String getName() {
            return "NADWOZIE";
        }

        public static String getTypeSedan() {
            return "Sedan";
        }

        public static String getTypeCrossover() {
            return "Crossover";
        }

        public static String getTypeCoupe() {
            return "Coupe";
        }

        public static String getTypeHatchback() {
            return "Hatchback";
        }

        public static String getTypeLiftback() {
            return "Liftback";
        }

        public static String getTypeWagon() {
            return "Wagon";
        }

        public static String getTypeCabriolet() {
            return "Cabriolet";
        }

        public static String getTypeRoadster() {
            return "Roadster";
        }

        public static String getTypeMinivan() {
            return "Minivan";
        }

        public static String getTypePickup() {
            return "Pickup";
        }

        public static String getTypeFastback() {
            return "Fastback";
        }

        public static String getTypeTarga() {
            return "Targa";
        }
    }

    public static class DriveWheels {
        public static String getValueName() {
            return "drive_wheels";
        }

        public static String getName() {
            return "NAPĘD";
        }

        public static String getTypeFwd() {
            return "fwd";
        }

        public static String getTypeAwd() {
            return "awd";
        }

        public static String getTypeRwd() {
            return "rwd";
        }

        public static String getType4wd() {
            return "4wd";
        }
    }

    //'Inline', 'V-type', 'Opposed', 'W-type', 'Rotary-piston'
    public static class CylinderLayout {
        public static String getValueName() {
            return "cylinder_layout";
        }

        public static String getName() {
            return "UKŁAD CYLINDRÓW";
        }

        public static String getTypeInline() {
            return "Inline";
        }

        public static String getTypeVType() {
            return "V-type";
        }

        public static String getTypeOpposed() {
            return "Opposed";
        }

        public static String getTypeWType() {
            return "W-type";
        }

        public static String getTypeRotaryPiston() {
            return "Rotary-piston";
        }
    }

    // 4,  6,  5,  8, 12,  3, 10,  2
    public static class NumberOfCylinders {
        public static String getValueName() {
            return "number_of_cylinders";
        }

        public static String getName() {
            return "LICZBA CYLINDRÓW";
        }

        public static String getType4() {
            return "4";
        }

        public static String getType6() {
            return "6";
        }

        public static String getType5() {
            return "5";
        }

        public static String getType8() {
            return "8";
        }

        public static String getType12() {
            return "12";
        }

        public static String getType3() {
            return "3";
        }

        public static String getType10() {
            return "10";
        }

        public static String getType2() {
            return "2";
        }
    }
}
