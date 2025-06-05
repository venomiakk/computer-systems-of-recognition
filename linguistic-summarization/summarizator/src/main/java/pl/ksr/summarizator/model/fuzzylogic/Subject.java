package pl.ksr.summarizator.model.fuzzylogic;

import pl.ksr.summarizator.model.CarObject;

import java.util.List;
import java.util.function.Function;

public class Subject {
    /*
     *  @params:
     *      name
     *      objects
     *      qualifier
     *      summarizer
     */
    // ?: I guess it should be list of functions for summarizers and qualifiers
    // ?: And i guess it should be FuzzySet, not LinguisticVariable... maybe not?
    // ?: It really depends on how we want to use it...
    // ?: If we have LinguisticVariable, then we can generate all terms from it
    // ?: But then how we can retun only one term? And is it even needed?
    // ?: Maybe we should do some kind of map or list of terms and T1s in DoubleSubjectTerm
    private Function<List<CarObject>, LinguisticVariable> linguisticVariableFunction;

    public Subject(Function<List<CarObject>, LinguisticVariable> linguisticVariableFunction, List<CarObject> cars) {
        this.linguisticVariableFunction = linguisticVariableFunction;
        LinguisticVariable linguisticVariable = linguisticVariableFunction.apply(cars);
        System.out.println(linguisticVariable.getName());
        int index = 2;
        System.out.println(linguisticVariable.getFuzzySets().get(index).getName());
        System.out.println(linguisticVariable.getFuzzySets().get(index).getValueName());
        System.out.println(linguisticVariable.getFuzzySets().get(index).getMembershipFunction().getClass().getSimpleName());
        System.out.println(linguisticVariable.getFuzzySets().get(index).getSupport().size());
        System.out.println(linguisticVariable.getFuzzySets().get(index).getCardinality());
        System.out.println(linguisticVariable.getFuzzySets().get(index).getDegreeOfFuzziness());
        System.out.println(linguisticVariable.getFuzzySetsMap());
    }
}
