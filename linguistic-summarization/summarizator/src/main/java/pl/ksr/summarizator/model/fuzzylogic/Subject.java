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

// ?: I guess it should be list of functions for summarizers and qualifiers
// ?: And i guess it should be FuzzySet, not LinguisticVariable... maybe not?
// ?: It really depends on how we want to use it...
// ?: If we have LinguisticVariable, then we can generate all terms from it
// ?: But then how we can retun only one term? And is it even needed?
// ?: Maybe we should do some kind of map or list of terms and T1s in DoubleSubjectTerm
//private
//private Function<List<CarObject>, LinguisticVariable> linguisticVariableFunction;
//
//public Subject(Function<List<CarObject>, LinguisticVariable> linguisticVariableFunction, List<CarObject> cars) {
//    this.linguisticVariableFunction = linguisticVariableFunction;
//    LinguisticVariable linguisticVariable = linguisticVariableFunction.apply(cars);
//    System.out.println(linguisticVariable.getName());
//    int index = 2;
//    System.out.println(linguisticVariable.getFuzzySets().get(index).getName());
//    System.out.println(linguisticVariable.getFuzzySets().get(index).getValueName());
//    System.out.println(linguisticVariable.getFuzzySets().get(index).getMembershipFunction().getClass().getSimpleName());
//    System.out.println(linguisticVariable.getFuzzySets().get(index).getSupport().size());
//    System.out.println(linguisticVariable.getFuzzySets().get(index).getCardinality());
//    System.out.println(linguisticVariable.getFuzzySets().get(index).getDegreeOfFuzziness());
//    System.out.println(linguisticVariable.getFuzzySetsMap());
//}

