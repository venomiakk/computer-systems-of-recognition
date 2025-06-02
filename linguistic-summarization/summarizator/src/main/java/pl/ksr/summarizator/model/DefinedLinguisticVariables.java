package pl.ksr.summarizator.model;

import pl.ksr.summarizator.model.fuzzylogic.FuzzySet;
import pl.ksr.summarizator.model.fuzzylogic.LinguisticVariable;
import pl.ksr.summarizator.model.membership.Gaussian;
import pl.ksr.summarizator.model.membership.Trapezoidal;
import pl.ksr.summarizator.model.membership.Triangular;

import java.util.List;

public class DefinedLinguisticVariables {
    public static LinguisticVariable rokProdukcji(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "ROK PRODUKCJI",
                List.of(
                        new FuzzySet("ROK PRODUKCJI: ZABYTKOWY",
                                carsSet,
                                "year_from",
                                new Trapezoidal(1964, 1964.0, 1964.0, 1970.0)),
                        new FuzzySet("ROK PRODUKCJI: KLASYCZNY",
                                carsSet,
                                "year_from",
                                new Trapezoidal(1965.0, 1970.0, 1980.0, 1985.0)),
                        new FuzzySet("ROK PRODUKCJI: STARY",
                                carsSet,
                                "year_from",
                                new Trapezoidal(1975.0, 1980.0, 2000.0, 2005.0)),
                        new FuzzySet("ROK PRODUKCJI: WSPÓŁCZESNY",
                                carsSet,
                                "year_from",
                                new Trapezoidal(2000.0, 2005.0, 2015.0, 2020.0)),
                        new FuzzySet("ROK PRODUKCJI: NOWOCZESNY",
                                carsSet,
                                "year_from",
                                new Trapezoidal(2015.0, 2020.0, 2020.0, 2020.0))
                ),
                1964.0,
                2019.0
        );
    }

    public static LinguisticVariable pojemnoscSilnika(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "POJEMNOŚĆ SILNIKA",
                List.of(
                        new FuzzySet("POJEMNOŚĆ SILNIKA: MAŁA",
                                carsSet,
                                "displacement",
                                new Trapezoidal(649.0, 649.0, 1500.0, 2500.0)),
                        new FuzzySet("POJEMNOŚĆ SILNIKA: ŚREDNIA",
                                carsSet,
                                "displacement",
                                new Trapezoidal(1500.0, 2500.0, 3500.0, 4500.0)),
                        new FuzzySet("POJEMNOŚĆ SILNIKA: DUŻA",
                                carsSet,
                                "displacement",
                                new Trapezoidal(3500.0, 4500.0, 6761.0, 6761.0))
                ),
                649.0,
                6761.0
        );
    }

    public static LinguisticVariable mocPojazdu(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "MOC POJAZDU",
                List.of(
                        new FuzzySet("MOC POJAZDU: SŁABY",
                                carsSet,
                                "power",
                                new Trapezoidal(29.0, 29.0, 100.0, 150.0)),
                        new FuzzySet("MOC POJAZDU: PRZECIĘTNY",
                                carsSet,
                                "power",
                                new Gaussian(200.0, 30.0, 100.0, 300.0)),
                        new FuzzySet("MOC POJAZDU: DYNAMICZNY",
                                carsSet,
                                "power",
                                new Gaussian(300.0, 30.0, 200.0, 400.0)),
                        new FuzzySet("MOC POJAZDU: MOCNY",
                                carsSet,
                                "power",
                                new Trapezoidal(350.0, 400.0, 710.0, 710.0))
                ),
                29.0,
                710.0
        );
    }

    public static LinguisticVariable momentObrotowy(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "MOMENT OBROTOWY",
                List.of(
                        new FuzzySet("MOMENT OBROTOWY: MAŁY",
                                carsSet,
                                "torque",
                                new Trapezoidal(44.0, 44.0, 100.0, 200.0)),
                        new FuzzySet("MOMENT OBROTOWY: ŚREDNI",
                                carsSet,
                                "torque",
                                new Trapezoidal(100.0, 200.0, 300.0, 400.0)),
                        new FuzzySet("MOMENT OBROTOWY: DUŻY",
                                carsSet,
                                "torque",
                                new Gaussian(300.0, 400.0, 1020.0, 1020.0))
                ),
                44.0,
                1020.0
        );
    }

    public static LinguisticVariable czasPrzyspieszenia(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "CZAS PRZYSPIESZENIA",
                List.of(
                        new FuzzySet("CZAS PRZYSPIESZENIA: EKSTREMALNY",
                                carsSet,
                                "acc",
                                new Trapezoidal(3.2, 3.2, 3.2, 4.0)),
                        new FuzzySet("CZAS PRZYSPIESZENIA: KRÓTKI",
                                carsSet,
                                "acc",
                                new Gaussian(6.0, 1.0, 3.2, 9.0)),
                        new FuzzySet("CZAS PRZYSPIESZENIA: PRZECIĘTNY",
                                carsSet,
                                "acc",
                                new Gaussian(12.0, 2.0, 6.0, 12.0)),
                        new FuzzySet("CZAS PRZYSPIESZENIA: DŁUGI",
                                carsSet,
                                "acc",
                                new Trapezoidal(14.0, 16.0, 33.2, 33.2))
                ),
                3.2,
                33.2
        );
    }

    public static LinguisticVariable predkoscMaksymalna(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "PRĘDKOŚĆ MAKSYMALNA",
                List.of(
                        new FuzzySet("PRĘDKOŚĆ MAKSYMALNA: NISKA",
                                carsSet,
                                "max_speed",
                                new Trapezoidal(100.0, 100.0, 150.0, 200.0)),
                        new FuzzySet("PRĘDKOŚĆ MAKSYMALNA: NORMALNA",
                                carsSet,
                                "max_speed",
                                new Triangular(150.0, 200.0, 250.0)),
                        new FuzzySet("PRĘDKOŚĆ MAKSYMALNA: WYSOKA",
                                carsSet,
                                "max_speed",
                                new Trapezoidal(200.0, 250.0, 300.0, 350.0)),
                        new FuzzySet("PRĘDKOŚĆ MAKSYMALNA: EKSTREMALNA",
                                carsSet,
                                "max_speed",
                                new Trapezoidal(300.0, 350.0, 350.0, 350.0))
                ),
                100.0,
                350.0
        );
    }

    public static LinguisticVariable spalanieMieszane(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "SPALANIE W TRYBIE MIESZANYM",
                List.of(
                        new FuzzySet("SPALANIE W TRYBIE MIESZANYM: EKONOMICZNY",
                                carsSet,
                                "mixed_fc",
                                new Trapezoidal(2.9, 2.9, 5, 10.0)),
                        new FuzzySet("SPALANIE W TRYBIE MIESZANYM: UMIARKOWANE",
                                carsSet,
                                "mixed_fc",
                                new Triangular(8.0, 12.0, 15.0)),
                        new FuzzySet("SPALANIE W TRYBIE MIESZANYM: WYSOKIE",
                                carsSet,
                                "mixed_fc",
                                new Trapezoidal(12.0, 15.0, 25.6, 25.6))
                ),
                2.9,
                25.6
        );
    }

    public static LinguisticVariable spalanieMiasto(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "SPALANIE W MIEŚCIE",
                List.of(
                        new FuzzySet("SPALANIE W MIEŚCIE: EKONOMICZNY",
                                carsSet,
                                "city_fc",
                                new Trapezoidal(3.6, 3.6, 8.0, 12.0)),
                        new FuzzySet("SPALANIE W MIEŚCIE: UMIARKOWANE",
                                carsSet,
                                "city_fc",
                                new Triangular(9.0, 14.0, 19.0)),
                        new FuzzySet("SPALANIE W MIEŚCIE: WYSOKIE",
                                carsSet,
                                "city_fc",
                                new Trapezoidal(15.0, 17.0, 35.6, 35.6))
                ),
                3.6,
                35.6
        );
    }

    public static LinguisticVariable spalanieAutostrada(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "SPALANIE NA AUTOSTRADZIE",
                List.of(
                        new FuzzySet("SPALANIE NA AUTOSTRADZIE: EKONOMICZNY",
                                carsSet,
                                "highway_fc",
                                new Trapezoidal(2.7, 2.7, 6.0, 8.0)),
                        new FuzzySet("SPALANIE NA AUTOSTRADZIE: UMIARKOWANY",
                                carsSet,
                                "highway_fc",
                                new Trapezoidal(6.0, 8.0, 12.0, 15.0)),
                        new FuzzySet("SPALANIE NA AUTOSTRADZIE: WYSOKIE",
                                carsSet,
                                "highway_fc",
                                new Trapezoidal(12.0, 15.0, 18.5, 18.5))
                ),
                2.7,
                18.5
        );
    }

    public static LinguisticVariable pojemnoscBagaznika(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "POJEMNOŚĆ BAGAŻNIKA",
                List.of(
                        new FuzzySet("POJEMNOŚĆ BAGAŻNIKA: MAŁY",
                                carsSet,
                                "trunk",
                                new Trapezoidal(72.0, 72.0, 250.0, 350.0)),
                        new FuzzySet("POJEMNOŚĆ BAGAŻNIKA: ŚREDNI",
                                carsSet,
                                "trunk",
                                new Trapezoidal(250.0, 350.0, 500.0, 600.0)),
                        new FuzzySet("POJEMNOŚĆ BAGAŻNIKA: POJEMNY",
                                carsSet,
                                "trunk",
                                new Trapezoidal(500.0, 600.0, 1500.0, 2500.0)),
                        new FuzzySet("POJEMNOŚĆ BAGAŻNIKA: TRANSPORTOWY",
                                carsSet,
                                "trunk",
                                new Trapezoidal(1500.0, 2500.0, 5400.0, 5400.0))
                ),
                72.0,
                5400.0
        );
    }

    public static LinguisticVariable masaPojazdu(List<CarObject> carsSet) {
        return new LinguisticVariable(
                "MASA POJAZDU",
                List.of(
                        new FuzzySet("MASA POJAZDU: LEKKI",
                                carsSet,
                                "weight",
                                new Trapezoidal(950.0, 950.0, 1250.0, 1500.0)),
                        new FuzzySet("MASA POJAZDU: ŚREDNI",
                                carsSet,
                                "weight",
                                new Trapezoidal(1250.0, 1500.0, 2000.0, 2500.0)),
                        new FuzzySet("MASA POJAZDU: CIĘŻKI",
                                carsSet,
                                "weight",
                                new Trapezoidal(2000.0, 2500.0, 3966.0, 3966.0))
                ),
                950.0,
                3966.0
        );
    }
}