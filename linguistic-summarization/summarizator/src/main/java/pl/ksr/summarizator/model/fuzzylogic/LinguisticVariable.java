package pl.ksr.summarizator.model.fuzzylogic;

import java.util.List;
import java.util.Map;

public class LinguisticVariable {
    private String name;
    //TODO: Consider using a Map<String, FuzzySet> for better access by name
    // ?: Maybe add additional field to not refactor all the code...
    private Map<String, FuzzySet> fuzzySetsMap;
    private List<FuzzySet> fuzzySets;
    private double leftBound;
    private double rightBound;

    public LinguisticVariable(String name, List<FuzzySet> fuzzySets, double leftBound, double rightBound) {
        this.name = name;
        this.fuzzySets = fuzzySets;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.fuzzySetsMap = fuzzySets.stream()
                .collect(java.util.stream.Collectors.toMap(FuzzySet::getName, fuzzySet -> fuzzySet));
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

    public Map<String, FuzzySet> getFuzzySetsMap() {
        return fuzzySetsMap;
    }
}
