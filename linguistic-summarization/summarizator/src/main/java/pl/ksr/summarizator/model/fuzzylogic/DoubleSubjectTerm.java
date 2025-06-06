package pl.ksr.summarizator.model.fuzzylogic;


import pl.ksr.summarizator.model.CarObject;
import pl.ksr.summarizator.model.membership.MembershipFunction;

import java.util.List;
import java.util.stream.Stream;

public class DoubleSubjectTerm {
    // ?: Maybe it should only contain information about term
    // ?: And we should have another class that handles the logic or something like that?
    // ?: ...
    private final Subject firstSubject;
    private final Subject secondSubject;
    private final Quantifier quantifier;

    public DoubleSubjectTerm(Subject firstSubject, Subject secondSubject, Quantifier quantifier) {
        this.firstSubject = firstSubject;
        this.secondSubject = secondSubject;
        this.quantifier = quantifier;
    }

    public String getTerm1() {
        return this.quantifier.getName() + " samochodów z " + firstSubject.getTermName() + " " + firstSubject.getTypeName() + " w porównaniu do samochodów z "
                + secondSubject.getTermName() + " " + secondSubject.getTypeName() + " jest/ma " + firstSubject.getSummarizersTerm();
    }

    public double getDot1() {
        if (firstSubject.getSummarizers().isEmpty() || secondSubject.getSummarizers().isEmpty()) {
            return 0.0;
        }
        double card1;
        if (firstSubject.getSummarizers().size() == 1) {
            card1 = firstSubject.getSummarizers().getFirst().getCardinality();
        } else {
            card1 = getCardinalityOfSum(firstSubject.getSummarizers(), firstSubject.getObjects());
        }
        double card2;
        if (secondSubject.getSummarizers().size() == 1) {
            card2 = secondSubject.getSummarizers().getFirst().getCardinality();
        } else {
            card2 = getCardinalityOfSum(secondSubject.getSummarizers(), secondSubject.getObjects());
        }
        double value = (card1 / (double) firstSubject.getObjects().size()) /
                (
                        card1 / (double) firstSubject.getObjects().size() +
                                card2 / (double) secondSubject.getObjects().size()
                );
        return quantifier.calculateMembership(value);
    }

    public String getTerm2() {
        return this.quantifier.getName() + " samochodów z " + firstSubject.getTermName() + " " + firstSubject.getTypeName() + " w porównaniu do samochodów z "
                + secondSubject.getTermName() + " " + secondSubject.getTypeName() + " które są/mają " + secondSubject.getQualifiersTerm()
                + " jest/ma " + secondSubject.getSummarizersTerm();
    }

    public double getDot2() {
        if (secondSubject.getQualifiers().isEmpty()) {
            return -1.0;
        }
        double card1;
        if (firstSubject.getSummarizers().size() == 1) {
            card1 = firstSubject.getSummarizers().getFirst().getCardinality();
        } else {
            card1 = getCardinalityOfSum(firstSubject.getSummarizers(), firstSubject.getObjects());
        }
        double card2 = getCardinalityOfSum(Stream.concat(
                                this.secondSubject.getQualifiers().stream(),
                                this.secondSubject.getSummarizers().stream())
                        .toList(),
                secondSubject.getObjects()
        );
        double value = (card1 / (double) firstSubject.getObjects().size()) /
                (
                        card1 / (double) firstSubject.getObjects().size() +
                                card2 / (double) secondSubject.getObjects().size()
                );
        return quantifier.calculateMembership(value);
    }

    public String getTerm3() {
        return this.quantifier.getName() + " samochodów z " + firstSubject.getTermName() + " " + firstSubject.getTypeName()
                + " które są/mają " + firstSubject.getQualifiersTerm()
                + " w porównaniu do samochodów z "
                + secondSubject.getTermName() + " " + secondSubject.getTypeName()
                + " jest/ma " + firstSubject.getSummarizersTerm();
    }

    public double getDot3() {
        if (firstSubject.getQualifiers().isEmpty()) {
            return -1.0;
        }
        double card1 = getCardinalityOfSum(Stream.concat(
                                this.firstSubject.getQualifiers().stream(),
                                this.firstSubject.getSummarizers().stream())
                        .toList(),
                firstSubject.getObjects()
        );
        double card2;
        if (secondSubject.getSummarizers().size() == 1) {
            card2 = secondSubject.getSummarizers().getFirst().getCardinality();
        } else {
            card2 = getCardinalityOfSum(secondSubject.getSummarizers(), secondSubject.getObjects());
        }
        double value = (card1 / (double) firstSubject.getObjects().size()) /
                (
                        card1 / (double) firstSubject.getObjects().size() +
                                card2 / (double) secondSubject.getObjects().size()
                );
        return quantifier.calculateMembership(value);
    }

    public String getTerm4() {
        return "Więcej samochodow z " + firstSubject.getTermName() + " " + firstSubject.getTypeName()
                + " niż samochodów z " + secondSubject.getTermName() + " " + secondSubject.getTypeName()
                + " jest/ma " + firstSubject.getSummarizersTerm();
    }

    public double getDot4() {
        double sp1 = getCardinalityOfSum(firstSubject.getSummarizers(), firstSubject.getObjects())
                / (double) firstSubject.getObjects().size();
        double inc = 0.0;
        for (CarObject car : secondSubject.getObjects()) {
            double minValue = Double.MAX_VALUE;
            for (FuzzySet summarizer : secondSubject.getSummarizers()) {
                double membership = summarizer.getMembership(car);
                if (membership < minValue) {
                    minValue = membership;
                }
            }
            // implikacja Łukasiewicza
            inc += Math.min(1.0, 1.0 - minValue + sp1);
        }

        return 1.0 - (inc / (double) secondSubject.getObjects().size());
    }

    private double getCardinalityOfSum(List<FuzzySet> summarizers, List<CarObject> objects) {
        double cardinality = 0.0;
        for (CarObject car : objects) {
            double minValue = Double.MAX_VALUE;
            for (FuzzySet summarizer : summarizers) {
                double membership = summarizer.getMembership(car);
                if (membership < minValue) {
                    minValue = membership;
                }
            }
            cardinality += minValue;
        }

        return cardinality;
    }
}
