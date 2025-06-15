package pl.ksr.summarizator.model.fuzzylogic;

import pl.ksr.summarizator.model.CarObject;

import java.util.List;

public class Subject {
    /*
     *  @params:
     *      termName
     *      objects
     *      qualifier
     *      summarizer
     */
    private final String valueName;
    private final String termName;
    private final String typeName;
    private final List<CarObject> objects;
    private final List<FuzzySet> summarizers;
    private final List<FuzzySet> qualifiers;

    public Subject(String valueName, String termName, String typeName, List<CarObject> objects, List<FuzzySet> summarizers, List<FuzzySet> qualifiers) {
        this.valueName = valueName;
        this.termName = termName;
        this.typeName = typeName;
        this.objects = objects;
        this.summarizers = summarizers;
        this.qualifiers = qualifiers;
    }

    public String getValueName() {
        return valueName;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getTermName() {
        return termName;
    }

    public List<CarObject> getObjects() {
        return objects;
    }

    public List<FuzzySet> getSummarizers() {
        return summarizers;
    }

    public List<FuzzySet> getQualifiers() {
        return qualifiers;
    }

    public String getSummarizersTerm(){
        return String.join(" i ", summarizers.stream().map(FuzzySet::getName).toList());
    }

    public String getQualifiersTerm(){
        return String.join(" i ", qualifiers.stream().map(FuzzySet::getName).toList());
    }
}

