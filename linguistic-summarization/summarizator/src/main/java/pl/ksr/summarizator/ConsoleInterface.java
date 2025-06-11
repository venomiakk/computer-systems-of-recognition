package pl.ksr.summarizator;

import pl.ksr.summarizator.model.*;
import pl.ksr.summarizator.model.fuzzylogic.*;

import java.util.*;


public class ConsoleInterface {
    /*
     *  How to connect multiple summarizers to a fuzzy set?
     *  Should there be a method in FuzzySet that accepts a list of summarizers?
     *  Or rather FuzzySet sould accept only one summarizer?
     *  Or maybe FuzzySet should get LinguisticVariable and then it can use all summarizers from that variable?
     */
    public static void main(String[] args) {
        //test();
        //preliminaryExperiments();

        doubleSubjectTest();

    }

    public static void doubleSubjectTest() {
        List<CarObject> cars = DataLoader.loadCars();
        List<DoubleSubjectTerm> terms = new ArrayList<>();

        //List<CarObject> coupes = DataLoader.getSubjectObjects(cars, DefinedSubjects.BodyType.getValueName(), DefinedSubjects.BodyType.getTypeCoupe());
        //List<CarObject> sedans = DataLoader.getSubjectObjects(cars, DefinedSubjects.BodyType.getValueName(), DefinedSubjects.BodyType.getTypeSedan());
        //
        //Subject coupeCars = new Subject(
        //        DefinedSubjects.BodyType.getValueName(),
        //        DefinedSubjects.BodyType.getName(),
        //        DefinedSubjects.BodyType.getTypeCoupe(),
        //        coupes,
        //        List.of(DefinedLinguisticVariables.mocPojazdu(coupes).getFuzzySets().getFirst()),
        //        List.of(DefinedLinguisticVariables.pojemnoscSilnika(coupes).getFuzzySets().getFirst())
        //);
        //Subject sedanCars = new Subject(
        //        DefinedSubjects.BodyType.getValueName(),
        //        DefinedSubjects.BodyType.getName(),
        //        DefinedSubjects.BodyType.getTypeSedan(),
        //        sedans,
        //        List.of(DefinedLinguisticVariables.mocPojazdu(sedans).getFuzzySets().getFirst()),
        //        List.of(DefinedLinguisticVariables.pojemnoscSilnika(sedans).getFuzzySets().getFirst())
        //);
        //
        //for (Quantifier quantifier : DefinedQuantifiers.getAllQuantifiers()) {
        //    DoubleSubjectTerm term = new DoubleSubjectTerm(coupeCars, sedanCars, quantifier);
        //    terms.add(term);
        //    DoubleSubjectTerm term2 = new DoubleSubjectTerm(sedanCars, coupeCars, quantifier);
        //    terms.add(term2);
        //}
        //
        List<CarObject> gasoline = DataLoader.getSubjectObjects(cars, DefinedSubjects.FuelType.getValueName(), DefinedSubjects.FuelType.getTypeGasoline());
        List<CarObject> diesel = DataLoader.getSubjectObjects(cars, DefinedSubjects.FuelType.getValueName(), DefinedSubjects.FuelType.getTypeDiesel());
        //
        //Subject gasolineCars = new Subject(
        //        DefinedSubjects.FuelType.getValueName(),
        //        DefinedSubjects.FuelType.getName(),
        //        DefinedSubjects.FuelType.getTypeGasoline(),
        //        gasoline,
        //        List.of(DefinedLinguisticVariables.czasPrzyspieszenia(gasoline).getFuzzySets().get(1),
        //                DefinedLinguisticVariables.mocPojazdu(gasoline).getFuzzySets().get(2)),
        //        List.of(DefinedLinguisticVariables.spalanieAutostrada(gasoline).getFuzzySets().get(1))
        //);
        //
        //Subject dieselCars = new Subject(
        //        DefinedSubjects.FuelType.getValueName(),
        //        DefinedSubjects.FuelType.getName(),
        //        DefinedSubjects.FuelType.getTypeDiesel(),
        //        diesel,
        //        List.of(DefinedLinguisticVariables.czasPrzyspieszenia(diesel).getFuzzySets().get(1),
        //                DefinedLinguisticVariables.mocPojazdu(diesel).getFuzzySets().get(2)),
        //        List.of(DefinedLinguisticVariables.spalanieAutostrada(diesel).getFuzzySets().get(1))
        //);
        //
        //for (Quantifier quantifier : DefinedQuantifiers.getAllQuantifiers()) {
        //    DoubleSubjectTerm term = new DoubleSubjectTerm(gasolineCars, dieselCars, quantifier);
        //    terms.add(term);
        //    DoubleSubjectTerm term2 = new DoubleSubjectTerm(dieselCars, gasolineCars, quantifier);
        //    terms.add(term2);
        //}
        //
        //List<CarObject> manual = DataLoader.getSubjectObjects(cars, DefinedSubjects.TransmissionType.getValueName(), DefinedSubjects.TransmissionType.getTypeManual());
        //List<CarObject> automatic = DataLoader.getSubjectObjects(cars, DefinedSubjects.TransmissionType.getValueName(), DefinedSubjects.TransmissionType.getTypeAutomatic());
        //
        //Subject manualCars = new Subject(
        //        DefinedSubjects.TransmissionType.getValueName(),
        //        DefinedSubjects.TransmissionType.getName(),
        //        DefinedSubjects.TransmissionType.getTypeManual(),
        //        manual,
        //        List.of(DefinedLinguisticVariables.rokProdukcji(manual).getFuzzySets().get(3)),
        //        List.of(DefinedLinguisticVariables.predkoscMaksymalna(manual).getFuzzySets().get(2),
        //                DefinedLinguisticVariables.spalanieAutostrada(manual).getFuzzySets().getFirst())
        //);
        //Subject automaticCars = new Subject(
        //        DefinedSubjects.TransmissionType.getValueName(),
        //        DefinedSubjects.TransmissionType.getName(),
        //        DefinedSubjects.TransmissionType.getTypeAutomatic(),
        //        automatic,
        //        List.of(DefinedLinguisticVariables.rokProdukcji(automatic).getFuzzySets().get(3)),
        //        List.of(DefinedLinguisticVariables.predkoscMaksymalna(automatic).getFuzzySets().get(2),
        //                DefinedLinguisticVariables.spalanieAutostrada(automatic).getFuzzySets().getFirst())
        //);
        //for (Quantifier quantifier : DefinedQuantifiers.getAllQuantifiers()) {
        //    DoubleSubjectTerm term = new DoubleSubjectTerm(manualCars, automaticCars, quantifier);
        //    terms.add(term);
        //    DoubleSubjectTerm term2 = new DoubleSubjectTerm(automaticCars, manualCars, quantifier);
        //    terms.add(term2);
        //}
        //
        //List<CarObject> crossovers = DataLoader.getSubjectObjects(cars, DefinedSubjects.BodyType.getValueName(), DefinedSubjects.BodyType.getTypeCrossover());
        //List<CarObject> sedans2 = DataLoader.getSubjectObjects(cars, DefinedSubjects.BodyType.getValueName(), DefinedSubjects.BodyType.getTypeSedan());
        //
        //Subject crossoverCars = new Subject(
        //        DefinedSubjects.BodyType.getValueName(),
        //        DefinedSubjects.BodyType.getName(),
        //        DefinedSubjects.BodyType.getTypeCrossover(),
        //        crossovers,
        //        List.of(DefinedLinguisticVariables.spalanieMieszane(crossovers).getFuzzySets().getFirst()),
        //        List.of(DefinedLinguisticVariables.pojemnoscBagaznika(crossovers).getFuzzySets().get(2))
        //);
        //Subject sedans2Cars = new Subject(
        //        DefinedSubjects.BodyType.getValueName(),
        //        DefinedSubjects.BodyType.getName(),
        //        DefinedSubjects.BodyType.getTypeSedan(),
        //        sedans2,
        //        List.of(DefinedLinguisticVariables.spalanieMieszane(sedans2).getFuzzySets().getFirst()),
        //        List.of(DefinedLinguisticVariables.pojemnoscBagaznika(sedans2).getFuzzySets().get(2))
        //);
        //for (Quantifier quantifier : DefinedQuantifiers.getAllQuantifiers()) {
        //    DoubleSubjectTerm term = new DoubleSubjectTerm(crossoverCars, sedans2Cars, quantifier);
        //    terms.add(term);
        //    DoubleSubjectTerm term2 = new DoubleSubjectTerm(sedans2Cars, crossoverCars, quantifier);
        //    terms.add(term2);
        //}

        Subject gasolineCars2 = new Subject(
                DefinedSubjects.FuelType.getValueName(),
                DefinedSubjects.FuelType.getName(),
                DefinedSubjects.FuelType.getTypeGasoline(),
                gasoline,
                List.of(DefinedLinguisticVariables.rokProdukcji(gasoline).getFuzzySets().get(2)),
                List.of(DefinedLinguisticVariables.spalanieMiasto(gasoline).getFuzzySets().getFirst())
        );
        Subject dieselCars2 = new Subject(
                DefinedSubjects.FuelType.getValueName(),
                DefinedSubjects.FuelType.getName(),
                DefinedSubjects.FuelType.getTypeDiesel(),
                diesel,
                List.of(DefinedLinguisticVariables.rokProdukcji(diesel).getFuzzySets().get(2)),
                List.of(DefinedLinguisticVariables.spalanieMiasto(diesel).getFuzzySets().getFirst())
        );
        for (Quantifier quantifier : DefinedQuantifiers.getAllQuantifiers()) {
            DoubleSubjectTerm term = new DoubleSubjectTerm(gasolineCars2, dieselCars2, quantifier);
            terms.add(term);
            DoubleSubjectTerm term2 = new DoubleSubjectTerm(dieselCars2, gasolineCars2, quantifier);
            terms.add(term2);
        }

        DataLoader.saveDoubleSubjectResults(terms);
    }

    public static void test() {
        List<CarObject> cars = DataLoader.loadCars();
        // * Creating a single subject linguistic term with quantifier, qualifiers, and summarizers
        SingleSubjectTerm term = new SingleSubjectTerm(
                cars,
                "wszystkich samochodow",
                DefinedQuantifiers.okoloPolowy,
                List.of(DefinedLinguisticVariables.masaPojazdu(cars).getFuzzySets().getFirst()),
                List.of(DefinedLinguisticVariables.spalanieMieszane(cars).getFuzzySets().getFirst(), DefinedLinguisticVariables.mocPojazdu(cars).getFuzzySets().getFirst())
        );
        System.out.println(term.getTerm());
        System.out.println("T1: " + term.getT1());
        System.out.println("T2: " + term.getT2());
        System.out.println("T3: " + term.getT3());
        System.out.println("T4: " + term.getT4());
        System.out.println("T5: " + term.getT5());
        System.out.println("T6: " + term.getT6());
        System.out.println("T7: " + term.getT7());
        System.out.println("T8: " + term.getT8());
        System.out.println("T9: " + term.getT9());
        System.out.println("T10: " + term.getT10());
        System.out.println("T11: " + term.getT11());
        System.out.println("Optimal Summary: " + term.getOptimalSummary());

    }

    public static void preliminaryExperiments() {
        List<CarObject> cars = DataLoader.loadCars();
        List<Quantifier> quantifiers = List.of(
                DefinedQuantifiers.niewiele,
                DefinedQuantifiers.mniejszosc,
                DefinedQuantifiers.okoloPolowy,
                DefinedQuantifiers.wiekszosc,
                DefinedQuantifiers.prawieWszystkie
        );
        List<SingleSubjectTerm> results = new ArrayList<>();

        // * Pierwsza forma
        // *
        for (FuzzySet summarizer : DefinedLinguisticVariables.mocPojazdu(cars).getFuzzySets()) {
            for (Quantifier quantifier : quantifiers) {
                SingleSubjectTerm term = new SingleSubjectTerm(
                        cars,
                        "wszystkich samochodow",
                        quantifier,
                        List.of(),
                        List.of(summarizer)
                );
                System.out.println(term.getTerm());
                results.add(term);
            }
        }

        for (FuzzySet summarizer1 : DefinedLinguisticVariables.mocPojazdu(cars).getFuzzySets()) {
            for (FuzzySet summarizer2 : DefinedLinguisticVariables.spalanieMieszane(cars).getFuzzySets()) {
                for (Quantifier quantifier : quantifiers) {
                    SingleSubjectTerm term = new SingleSubjectTerm(
                            cars,
                            "wszystkich samochodow",
                            quantifier,
                            List.of(),
                            List.of(summarizer1, summarizer2)
                    );
                    System.out.println(term.getTerm());
                    results.add(term);
                }
            }
        }

        // * Druga forma
        for (FuzzySet summarizer : DefinedLinguisticVariables.mocPojazdu(cars).getFuzzySets()) {
            for (FuzzySet qualifier : DefinedLinguisticVariables.pojemnoscSilnika(cars).getFuzzySets()) {
                for (Quantifier quantifier : quantifiers) {
                    SingleSubjectTerm term = new SingleSubjectTerm(
                            cars,
                            "wszystkich samochodow",
                            quantifier,
                            List.of(qualifier),
                            List.of(summarizer)
                    );
                    System.out.println(term.getTerm());
                    results.add(term);
                }
            }
        }

        DataLoader.saveResults(results);
    }

}
