package pl.ksr.summarizator;

import pl.ksr.summarizator.model.*;
import pl.ksr.summarizator.model.fuzzylogic.FuzzySet;
import pl.ksr.summarizator.model.fuzzylogic.Quantifier;
import pl.ksr.summarizator.model.fuzzylogic.SingleSubjectTerm;
import pl.ksr.summarizator.model.fuzzylogic.Subject;

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
        System.out.println(cars.getFirst().getCarProperties());
        System.out.println(DefinedSubjects.FuelType.getTypeGasoline());
        List<CarObject> coupes = DataLoader.getSubjectObjects(cars, DefinedSubjects.BodyType.getName(), DefinedSubjects.BodyType.getTypeCoupe());
        System.out.println(coupes.getFirst().getCarProperties());
        System.out.println(coupes.size());
        Subject coupeCars = new Subject(DefinedLinguisticVariables::mocPojazdu, coupes);
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

        for (FuzzySet summarizer1 : DefinedLinguisticVariables.mocPojazdu(cars).getFuzzySets()){
            for (FuzzySet summarizer2 : DefinedLinguisticVariables.spalanieMieszane(cars).getFuzzySets()){
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
        //for (FuzzySet summarizer : DefinedLinguisticVariables.mocPojazdu(cars).getFuzzySets()) {
        //    for (FuzzySet qualifier : DefinedLinguisticVariables.pojemnoscSilnika(cars).getFuzzySets()) {
        //        for (Quantifier quantifier : quantifiers) {
        //            SingleSubjectLinguisticTerm term = new SingleSubjectLinguisticTerm(
        //                    cars,
        //                    "wszystkich samochodow",
        //                    quantifier,
        //                    List.of(qualifier),
        //                    List.of(summarizer)
        //            );
        //            System.out.println(term.getTerm());
        //            results.add(term);
        //        }
        //    }
        //}

        DataLoader.saveResults(results);
    }
}
