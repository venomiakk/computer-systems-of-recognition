package pl.ksr.summarizator.model.fuzzylogic;

import pl.ksr.summarizator.model.CarObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SingleSubjectTerm {
    private final List<CarObject> setOfObjects;
    private final String subject;
    private final Quantifier quantifier;
    private final List<FuzzySet> qualifiers;
    private final List<FuzzySet> summarizers;
    private final double t1;
    private final double t2;
    private final double t3;
    private final double t4;
    private final double t5;
    private final double t6;
    private final double t7;
    private final double t8;
    private final double t9;
    private final double t10;
    private final double t11;
    private final double optimalSummary;

    public SingleSubjectTerm(List<CarObject> setOfObjects, String subject, Quantifier quantifier,
                             List<FuzzySet> qualifiers, List<FuzzySet> summarizers) {
        this.setOfObjects = setOfObjects;
        this.subject = subject;
        this.quantifier = quantifier;
        this.qualifiers = qualifiers;
        this.summarizers = summarizers;
        this.t1 = calculateT1();
        this.t2 = calculateT2();
        this.t3 = calculateT3();
        this.t4 = calculateT4();
        this.t5 = calculateT5();
        this.t6 = calculateT6();
        this.t7 = calculateT7();
        this.t8 = calculateT8();
        this.t9 = calculateT9();
        this.t10 = calculateT10();
        this.t11 = calculateT11();
        this.optimalSummary = calculateOptimalSummary(List.of(this.t1, this.t2, this.t3, this.t4, this.t5,
                this.t6, this.t7, this.t8, this.t9, this.t10, this.t11), List.of());

    }

    public SingleSubjectTerm(List<CarObject> setOfObjects, String subject, Quantifier quantifier,
                             List<FuzzySet> qualifiers, List<FuzzySet> summarizers, List<Double> weights) {
        this.setOfObjects = setOfObjects;
        this.subject = subject;
        this.quantifier = quantifier;
        this.qualifiers = qualifiers;
        this.summarizers = summarizers;
        this.t1 = calculateT1();
        this.t2 = calculateT2();
        this.t3 = calculateT3();
        this.t4 = calculateT4();
        this.t5 = calculateT5();
        this.t6 = calculateT6();
        this.t7 = calculateT7();
        this.t8 = calculateT8();
        this.t9 = calculateT9();
        this.t10 = calculateT10();
        this.t11 = calculateT11();
        this.optimalSummary = calculateOptimalSummary(List.of(this.t1, this.t2, this.t3, this.t4, this.t5,
                this.t6, this.t7, this.t8, this.t9, this.t10, this.t11), weights);

    }

    public String getTerm() {
        String term = this.quantifier.getName() + " " + this.subject;
        if (!qualifiers.isEmpty()) {
            term += " będących/mających " + String.join(" i ", qualifiers.stream().map(FuzzySet::getName).toList());
        }
        if (!summarizers.isEmpty()) {
            term += " jest/ma " + String.join(" i ", summarizers.stream().map(FuzzySet::getName).toList());
        }
        return term;
    }


    //TODO: Czy jeszcze jakies miary (jak T2) powinny się ograniczac do zbioru ograniczonego przez kwalifikatory
    private double calculateT1() {
        // * Dane z bazy danych
        double denominator = 0.0;
        if (qualifiers.isEmpty()) {
            denominator = (double) this.setOfObjects.size();
        } else {
            if (qualifiers.size() == 1) {
                denominator = this.qualifiers.getFirst().getCardinality();
            } else {
                for (CarObject car : setOfObjects) {
                    double minValue = Double.MAX_VALUE;
                    for (FuzzySet qualifier : qualifiers) {
                        double membership = qualifier.getMembership(car);
                        if (membership < minValue) {
                            minValue = membership;
                        }
                    }
                    denominator += minValue;
                }
            }
            if (denominator == 0) {
                return 0.0; // Avoid division by zero
            }
        }
        //TODO: Czy tu w sumie nie powinno być min z sumaryzatorów dla obiektów które są w support kwalifikatora?
        List<FuzzySet> allSets = Stream.concat(this.qualifiers.stream(), this.summarizers.stream()).toList();
        double numerator = 0.0;
        if (allSets.size() == 1) {
            numerator = allSets.getFirst().getCardinality();
        } else {
            for (CarObject car : setOfObjects) {
                double minValue = Double.MAX_VALUE;
                for (FuzzySet set : allSets) {
                    double membership = set.getMembership(car);
                    if (membership < minValue) {
                        minValue = membership;
                    }
                }
                numerator += minValue;
            }
        }
        return this.quantifier.calculateMembership(numerator / denominator);
    }


    private double calculateT2() {
        //TODO: Trzeba jednak z bazy
        //TODO: Tylko te które spełniają kwalifikator W
        // * Model
        //double exponent = 1 / (double) this.summarizers.size();
        //List<Double> inList = new ArrayList<>();
        //for (FuzzySet summarizer : this.summarizers) {
        //    double minValue = summarizer.getFuzzySet().keySet()
        //            .stream()
        //            .map(car -> car.getCarProperties().get(summarizer.getValueName()))
        //            .mapToDouble(val -> (Double.parseDouble((String) val)))
        //            .min()
        //            .orElse(0.0);
        //    double maxValue = summarizer.getFuzzySet().keySet()
        //            .stream()
        //            .map(car -> car.getCarProperties().get(summarizer.getValueName()))
        //            .mapToDouble(val -> (Double.parseDouble((String) val)))
        //            .max()
        //            .orElse(0.0);
        //    double supportSize = summarizer.getMembershipFunction().getSupportSize();
        //    inList.add(supportSize / (maxValue - minValue));
        //}
        //double inMean = inList.stream()
        //        .reduce(1.0, (a, b) -> a * b);
        //return 1 - Math.pow(inMean, exponent);
        // * Baza
        double exponent = 1 / (double) this.summarizers.size();
        double inMean = this.summarizers.stream()
                .mapToDouble(FuzzySet::getDegreeOfFuzziness)
                .reduce(1.0, (a, b) -> a * b);
        return 1 - Math.pow(inMean, exponent);
    }

    private double calculateT3() {
        // * Dane z bazy danych
        // * Jak nakładają się sumaryzatory na podzbiór wyróżniony na kwalifikator
        double numerator = 0.0;
        double denominator = 0.0;
        if (qualifiers.isEmpty()) {
            denominator = (double) this.setOfObjects.size();
        } else {
            if (qualifiers.size() == 1) {
                denominator = this.qualifiers.getFirst().getSupport().size();
            } else {
                for (CarObject car : setOfObjects) {
                    boolean areAllInSupport = true;
                    for (FuzzySet qualifier : qualifiers) {
                        double membership = qualifier.getMembership(car);
                        if (membership <= 0) {
                            areAllInSupport = false;
                            break;
                        }
                    }
                    if (areAllInSupport) {
                        denominator += 1;
                    }
                }
            }
            if (denominator == 0) {
                return 0.0; // Avoid division by zero
            }
        }
        List<FuzzySet> allSets = Stream.concat(this.qualifiers.stream(), this.summarizers.stream()).toList();
        if (allSets.size() == 1) {
            numerator = allSets.getFirst().getSupport().size();
        } else {
            for (CarObject car : setOfObjects) {
                boolean areAllInSupport = true;
                for (FuzzySet set : allSets) {
                    double membership = set.getMembership(car);
                    if (membership <= 0) {
                        areAllInSupport = false;
                        break;
                    }
                }
                if (areAllInSupport) {
                    numerator += 1;
                }
            }
        }
        return numerator / denominator;
    }

    private double calculateT4() {
        // * Dane z bazy
        // * Odnosi jakość sumaryzatora do całej bazy
        //* na ile zbieżne są sumaryzatory
        List<Double> rj = this.summarizers.stream()
                .map(FuzzySet::getDegreeOfFuzziness)
                .toList();
        return Math.abs(rj.stream().reduce(1.0, (a, b) -> a * b) - this.t3);
    }

    private double calculateT5() {
        return 2.0 * Math.pow(0.5, this.summarizers.size());
    }

    private double calculateT6() {
        // * DLA MODELU
        if (quantifier.isAbsolute()) {
            return 1.0 - (quantifier.getMembershipFunction().getSupportSize() / (double) setOfObjects.size());
        } else {
            return 1.0 - quantifier.getMembershipFunction().getSupportSize();
        }
    }

    private double calculateT7() {
        // * DLA MODELU
        return 1.0 - quantifier.getMembershipFunction().getCardinality();
        //if (quantifier.isAbsolute()) {
        //    double suppVal = 0.0;
        //    for (int i = 1; i <= setOfObjects.size(); i++) {
        //        suppVal += quantifier.calculateMembership(i);
        //    }
        //    return 1 - (suppVal / setOfObjects.size());
        //} else {
        //    return 1.0 - quantifier.getMembershipFunction().getCardinality();
        //}
    }

    private double calculateT8() {
        // * Na bazie danych
        //TODO czy dobry wzór
        List<Double> rj = this.summarizers.stream()
                .map(fuzzySet -> fuzzySet.getCardinality() / (double) fuzzySet.getSize()
                ).toList();
        double exponent = 1 / (double) this.summarizers.size();
        double inMean = rj.stream()
                .reduce(1.0, (a, b) -> a * b);
        return 1 - Math.pow(inMean, exponent);
    }

    private double calculateT9() {
        // * Na bazie danych ?
        if (qualifiers.isEmpty()) {
            return 0.0;
        } else {
            double exponent = 1 / (double) this.qualifiers.size();
            double inMean = this.qualifiers.stream()
                    .mapToDouble(FuzzySet::getDegreeOfFuzziness)
                    .reduce(1.0, (a, b) -> a * b);
            return 1 - Math.pow(inMean, exponent);
        }
    }

    private double calculateT10() {
        // * Na bazie danych ?
        //TODO czy dobry wzór
        if (qualifiers.isEmpty()) {
            return 0.0;
        } else {
            List<Double> rj = this.qualifiers.stream()
                    .map(fuzzySet -> fuzzySet.getCardinality() / (double) fuzzySet.getSize()
                    ).toList();
            double exponent = 1 / (double) this.qualifiers.size();
            double inMean = rj.stream()
                    .reduce(1.0, (a, b) -> a * b);
            return 1 - Math.pow(inMean, exponent);
        }
    }

    private double calculateT11() {
        if (qualifiers.isEmpty()) {
            return 0.0;
        } else {
            return 2.0 * Math.pow(0.5, this.qualifiers.size());
        }
    }

    private double calculateOptimalSummary(List<Double> tValues, List<Double> inputWeights) {
        List<Double> weights; //Need to sum to 1.0
        if (inputWeights != null && inputWeights.size() == tValues.size()) {
            weights = inputWeights;
        } else {
            weights = List.of(
                    0.5, // T1
                    0.05, // T2
                    0.05, // T3
                    0.05, // T4
                    0.05, // T5
                    0.05, // T6
                    0.05, // T7
                    0.05, // T8
                    0.05, // T9
                    0.05, // T10
                    0.05  // T11
            );
        }
        //System.out.println(weights.stream().mapToDouble(Double::doubleValue).sum());
        List<Double> finalWeights = weights;
        return IntStream.range(0, tValues.size())
                .mapToDouble(i -> tValues.get(i) * finalWeights.get(i))
                .sum();
    }



    public String getSubject() {
        return subject;
    }

    public Quantifier getQuantifier() {
        return quantifier;
    }

    public List<FuzzySet> getQualifiers() {
        return qualifiers;
    }

    public List<FuzzySet> getSummarizers() {
        return summarizers;
    }

    public double getT1() {
        return t1;
    }

    public double getT2() {
        return t2;
    }

    public double getT3() {
        return t3;
    }

    public double getT4() {
        return t4;
    }

    public double getT5() {
        return t5;
    }

    public double getT6() {
        return t6;
    }

    public double getT7() {
        return t7;
    }

    public double getT8() {
        return t8;
    }

    public double getT9() {
        return t9;
    }

    public double getT10() {
        return t10;
    }

    public double getT11() {
        return t11;
    }

    public double getOptimalSummary() {
        return optimalSummary;
    }

    public List<CarObject> getSetOfObjects() {
        return setOfObjects;
    }
}
