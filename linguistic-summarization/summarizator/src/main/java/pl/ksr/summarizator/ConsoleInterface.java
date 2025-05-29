package pl.ksr.summarizator;

import pl.ksr.summarizator.model.*;
import pl.ksr.summarizator.model.membership.Gaussian;
import pl.ksr.summarizator.model.membership.MembershipFunction;
import pl.ksr.summarizator.model.membership.Trapezoidal;
import pl.ksr.summarizator.model.membership.Triangular;

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
        FuzzySet slaby = new FuzzySet("SLABY", cars, "power", slabyFunc);
        FuzzySet przecietny = new FuzzySet("PRZECIETNY", cars, "power", przecietnyFunc);
        FuzzySet dynamiczny = new FuzzySet("DYNAMICZNY", cars, "power", dynamicznyFunc);
        FuzzySet mocny = new FuzzySet("MOCNY", cars, "power", mocnyFunc);

        // * Creating a linguistic variable for engine power
        LinguisticVariable enginePowerVariable =
                new LinguisticVariable("MOC POJAZDU",
                        Arrays.asList(slaby, przecietny, dynamiczny, mocny), 29.0, 710.0);

        Quantifier okoloPolowy = DefinedQuantifiers.okoloPolowy;

        System.out.println(okoloPolowy.getName() + " samochodow " + " ma/jest " + enginePowerVariable.getName() + ": " +
                enginePowerVariable.getFuzzySets().get(1).getName());
    }

}
