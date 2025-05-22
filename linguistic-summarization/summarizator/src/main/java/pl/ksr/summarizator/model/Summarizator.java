package pl.ksr.summarizator.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Summarizator {
    public static List<CarObject> readCsv() {
        boolean header = true;
        List<String> headers = new ArrayList<>();
        List<CarObject> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/selected_cars.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split( ",");
                if (header) {
                    headers.addAll(Arrays.asList(values));
                    header = false;
                    continue;
                }
                CarObject car = new CarObject(headers,Arrays.asList(values));
                records.add(car);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }
    public static void main(String[] args) {
        List<CarObject> records = readCsv();
        List<LinguisticVariable> linguisticVariables = new ArrayList<>();
        LinguisticVariable lv = new LinguisticVariable("Rok produkcji");
        lv.addSummarizer("Zabytkowy", Arrays.asList(1965.0,1965.0,1970.0));
        lv.addSummarizer("klasyczny", Arrays.asList(1965.0,1970.0,1980.0,1985.0));
        lv.addSummarizer("Stary", Arrays.asList(1980.0,1985.0,2000.0,2005.0));
        lv.addSummarizer("Współczesny", Arrays.asList(2000.0,2005.0,2015.0,2020.0));
        lv.addSummarizer("Nowoczesny", Arrays.asList(2015.0,2020.0,2020.0));
        List<Double> parameters = new ArrayList<>();
        parameters.add(13000.0);
        parameters.add(700.0);
        Quantifier quantifier = new Quantifier("Większość",parameters);
        List<Double> parameters1 = new ArrayList<>();
        parameters1.add(5550.0);
        parameters1.add(7000.0);
        parameters1.add(9500.0);
        parameters1.add(11000.0);
        Quantifier quantifier1 = new Quantifier("polowa",parameters1);
        FuzzySet wspolczesneSamochody = new FuzzySet();
        linguisticVariables.add(lv);
        for (CarObject record : records) {
            wspolczesneSamochody.addCar(record,lv.getSummarizer("Współczesny").summarize(record,"year_from"));
        }
        System.out.println("Liczba samochodów współczesnych: " + wspolczesneSamochody.getSize());
        System.out.println("Liczba kardynalna samochodów współczesnych: " + wspolczesneSamochody.getCard());
        System.out.println("Wielkosc nosnika samochodów współczesnych: " + wspolczesneSamochody.getSupp());
        System.out.println("Czy zbiory są normalne: " + wspolczesneSamochody.isNormal());
        System.out.println("w jakim stopniu " +quantifier1.getName()+" to samochody wpolczesne " + quantifier1.calculateMembership(wspolczesneSamochody.getCard()));
        System.out.println(quantifier1.getName() + " samochodow jest/ma wspolczesny" );
    }
}
