package pl.ksr.summarizator;

import pl.ksr.summarizator.model.*;
import pl.ksr.summarizator.model.fuzzylogic.FuzzySet;
import pl.ksr.summarizator.model.fuzzylogic.LinguisticVariable;
import pl.ksr.summarizator.model.fuzzylogic.Quantifier;
import pl.ksr.summarizator.model.fuzzylogic.SingleSubjectLinguisticTerm;
import pl.ksr.summarizator.model.membership.Gaussian;
import pl.ksr.summarizator.model.membership.MembershipFunction;
import pl.ksr.summarizator.model.membership.Trapezoidal;

import java.util.Arrays;
import java.util.List;


public class ConsoleInterface {
    /*
     *  How to connect multiple summarizers to a fuzzy set?
     *  Should there be a method in FuzzySet that accepts a list of summarizers?
     *  Or rather FuzzySet sould accept only one summarizer?
     *  Or maybe FuzzySet should get LinguisticVariable and then it can use all summarizers from that variable?
     */
    public static void main(String[] args) {
        //firstTest();
        List<CarObject> cars = DataLoader.loadCars();
        System.out.println(cars.size());
        System.out.println(cars.get(0).getCarProperties());
        enginePower(cars);
    }

    public static void enginePower(List<CarObject> cars) {
        // * Defining membership functions for engine power
        MembershipFunction slabyFunc = new Trapezoidal(29.0, 29.0, 100.0, 150.0);
        MembershipFunction przecietnyFunc = new Gaussian(200.0, 30.0, 100.0, 300.0);
        MembershipFunction dynamicznyFunc = new Gaussian(300.0, 30.0, 200.0, 400.0);
        MembershipFunction mocnyFunc = new Trapezoidal(350.0, 400.0, 710.0, 710.0);

        // * Creating fuzzy sets for engine power
        FuzzySet slaby = new FuzzySet("MOC POJAZDU: SLABY", cars, "power", slabyFunc);
        FuzzySet przecietny = new FuzzySet("MOC POJAZDU: PRZECIETNY", cars, "power", przecietnyFunc);
        FuzzySet dynamiczny = new FuzzySet("MOC POJAZDU: DYNAMICZNY", cars, "power", dynamicznyFunc);
        FuzzySet mocny = new FuzzySet("MOC POJAZDU: MOCNY", cars, "power", mocnyFunc);

        // * Creating a linguistic variable for engine power
        LinguisticVariable enginePowerVariable =
                new LinguisticVariable("MOC POJAZDU",
                        Arrays.asList(slaby, przecietny, dynamiczny, mocny), 29.0, 710.0);

        // * Defining membership functions for fuel economy
        MembershipFunction ekonomicznyFunc = new Trapezoidal(2.7, 2.7, 6.0, 8.0);
        MembershipFunction umiarkowanyFunc = new Trapezoidal(6.0, 8.0, 12.0, 15.0);
        MembershipFunction wysokieFunc = new Trapezoidal(12.0, 15.0, 18.5, 18.5);

        // * Creating fuzzy sets for fuel economy
        FuzzySet ekonomiczny = new FuzzySet("SPALANIE NA AUTOSTRADZIE: EKONOMICZNY", cars, "highway_fc", ekonomicznyFunc);
        FuzzySet umiarkowany = new FuzzySet("SPALANIE NA AUTOSTRADZIE: UMIARKOWANY", cars, "highway_fc", umiarkowanyFunc);
        FuzzySet wysokie = new FuzzySet("SPALANIE NA AUTOSTRADZIE: WYSOKIE", cars, "highway_fc", wysokieFunc);

        // * Creating a linguistic variable for fuel economy
        LinguisticVariable fuelEconomyVariable =
                new LinguisticVariable("SPALANIE NA AUTOSTRADZIE",
                        Arrays.asList(ekonomiczny, umiarkowany, wysokie), 2.7, 18.5);

        Quantifier okoloPolowy = DefinedQuantifiers.okoloPolowy;
        Quantifier niewiele = DefinedQuantifiers.niewiele;

        //System.out.println(okoloPolowy.getName() + " samochodow " + " ma/jest " +
        //        enginePowerVariable.getFuzzySets().stream()
        //                .filter(fuzzySet -> fuzzySet.getName().equals("MOC POJAZDU: PRZECIETNY"))
        //                .toList().getFirst().getName());

        // * Creating a single subject linguistic term with quantifier, qualifiers, and summarizers
        SingleSubjectLinguisticTerm term = new SingleSubjectLinguisticTerm(
                cars,
                "wszystkich samochodow",
                niewiele,
                List.of(),
                List.of(wysokie, mocny)
        );

        System.out.println(term.getTerm());

        //System.out.println("Moc pojazdu przecietna: " + przecietny.getSupport().size() + " cardinality: " + przecietny.getCardinality());
        //System.out.println("Spalanie umiarkowane: " + umiarkowany.getSupport().size() + " cardinality: " + umiarkowany.getCardinality());
        //System.out.println("Liczba samochodow wspolna " + przecietny.getSupport().stream().filter(umiarkowany.getSupport()::contains).count());

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

}
