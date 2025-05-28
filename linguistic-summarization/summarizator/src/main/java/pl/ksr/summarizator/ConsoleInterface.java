package pl.ksr.summarizator;

import pl.ksr.summarizator.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConsoleInterface {
    /*
     *  How to connect multiple summarizers to a fuzzy set?
     *  Should there be a method in FuzzySet that accepts a list of summarizers?
     *  Or rather FuzzySet sould accept only one summarizer?
     *  Or maybe FuzzySet should get LinguisticVariable and then it can use all summarizers from that variable?
     */
    public static void main(String[] args) {
        List<CarObject> cars = DataLoader.loadCars();
        System.out.println(cars.size());
        System.out.println(cars.get(1).getCarProperties());

        LinguisticVariable variable1 = new LinguisticVariable("Pojemnosc silnika");
        variable1.addSummarizer("Maly", Arrays.asList(649.0, 649.0, 1500.0, 2500.0));
        variable1.addSummarizer("Sredni", Arrays.asList(1500.0, 2500.0, 3500.0, 4500.0));
        variable1.addSummarizer("Duzy", Arrays.asList(3500.0, 4500.0, 6761.0, 6761.0));

        Quantifier quantifier = new Quantifier("Prawie wszystkie", List.of(0.8, 0.9, 1.0, 1.0));

        FuzzySet fuzzySet = new FuzzySet("Samochody z duzym silnikiem");

        for (CarObject car : cars) {
            double membership = variable1.getSummarizer("Duzy").summarize(car, "displacement");
            fuzzySet.addCar(car, membership);
        }
        System.out.println("-------------------");
        System.out.println("Liczba samochodów z dużym silnikiem: " + fuzzySet.getSize());
        System.out.println("Supp: " + fuzzySet.getSupp().size());
    }

    public void oldMain() {
        List<CarObject> records = DataLoader.loadCars();
        List<LinguisticVariable> linguisticVariables = new ArrayList<>();
        LinguisticVariable lv = new LinguisticVariable("Rok produkcji");
        lv.addSummarizer("Zabytkowy", Arrays.asList(1965.0, 1965.0, 1970.0));
        lv.addSummarizer("klasyczny", Arrays.asList(1965.0, 1970.0, 1980.0, 1985.0));
        lv.addSummarizer("Stary", Arrays.asList(1980.0, 1985.0, 2000.0, 2005.0));
        lv.addSummarizer("Współczesny", Arrays.asList(2000.0, 2005.0, 2015.0, 2020.0));
        lv.addSummarizer("Nowoczesny", Arrays.asList(2015.0, 2020.0, 2020.0));
        LinguisticVariable lv1 = new LinguisticVariable("Pjemność silnika");
        lv1.addSummarizer("Mały", Arrays.asList(649.0, 649.0, 1500.0, 2500.0));
        lv1.addSummarizer("Średni", Arrays.asList(1500.0, 2500.0, 3500.0, 4500.0));
        lv1.addSummarizer("Duży", Arrays.asList(3500.0, 4500.0, 6761.0, 6761.0));
        List<Double> parameters = new ArrayList<>();
        parameters.add(13000.0);
        parameters.add(700.0);
        Quantifier quantifier = new Quantifier("Większość", parameters);
        List<Double> parameters1 = new ArrayList<>();
        parameters1.add(5550.0);
        parameters1.add(7000.0);
        parameters1.add(9500.0);
        parameters1.add(11000.0);
        Quantifier quantifier1 = new Quantifier("polowa", parameters1);
        FuzzySet wspolczesneSamochody = new FuzzySet("Wspolczesne samochody");
        linguisticVariables.add(lv);
        linguisticVariables.add(lv1);
        for (CarObject record : records) {
            wspolczesneSamochody.addCar(record, lv.getSummarizer("Współczesny").summarize(record, "year_from"));
        }
        //System.out.println("Liczba samochodów współczesnych: " + wspolczesneSamochody.getSize());
        //System.out.println("Liczba kardynalna samochodów współczesnych: " + wspolczesneSamochody.getCard());
        //System.out.println("Wielkosc nosnika samochodów współczesnych: " + wspolczesneSamochody.getSupp());
        //System.out.println("Czy zbiory są normalne: " + wspolczesneSamochody.isNormal());
        //System.out.println("w jakim stopniu " +quantifier1.getName()+" to samochody wpolczesne " + quantifier1.calculateMembership(wspolczesneSamochody.getCard()));
        //System.out.println(quantifier1.getName() + " samochodow jest/ma wspolczesny" );

        List<Summarizer> summarizers = List.of(lv.getSummarizer("Współczesny"), lv1.getSummarizer("Duży"));
        //List<List<Summarizer>> summarizersList =
        System.out.println(summarizers);
    }
}
