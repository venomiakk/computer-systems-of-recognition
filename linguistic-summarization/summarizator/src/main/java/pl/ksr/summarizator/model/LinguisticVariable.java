package pl.ksr.summarizator.model;

import java.util.List;

public class LinguisticVariable {
    private String name;
    private List<FuzzySet> fuzzySets;
    private double leftBound;
    private double rightBound;

    public LinguisticVariable(String name, List<FuzzySet> fuzzySets, double leftBound, double rightBound) {
        this.name = name;
        this.fuzzySets = fuzzySets;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FuzzySet> getFuzzySets() {
        return fuzzySets;
    }

    public void setFuzzySets(List<FuzzySet> fuzzySets) {
        this.fuzzySets = fuzzySets;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public void setLeftBound(double leftBound) {
        this.leftBound = leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }

    public void setRightBound(double rightBound) {
        this.rightBound = rightBound;
    }
}
