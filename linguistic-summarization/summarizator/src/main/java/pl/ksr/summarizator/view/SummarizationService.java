package pl.ksr.summarizator.view;

import pl.ksr.summarizator.model.CarObject;
import pl.ksr.summarizator.model.DataLoader;
import pl.ksr.summarizator.model.DefinedLinguisticVariables;
import pl.ksr.summarizator.model.DefinedQuantifiers;
import pl.ksr.summarizator.model.fuzzylogic.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SummarizationService {
    private static final List<CarObject> cars = DataLoader.loadCars();

    public static List<LinguisticVariable> getLinguisticVariables() {
        List<LinguisticVariable> linguisticVariables = new ArrayList<>();
        linguisticVariables.add(DefinedLinguisticVariables.rokProdukcji(cars));
        linguisticVariables.add(DefinedLinguisticVariables.pojemnoscSilnika(cars));
        linguisticVariables.add(DefinedLinguisticVariables.mocPojazdu(cars));
        linguisticVariables.add(DefinedLinguisticVariables.momentObrotowy(cars));
        linguisticVariables.add(DefinedLinguisticVariables.czasPrzyspieszenia(cars));
        linguisticVariables.add(DefinedLinguisticVariables.predkoscMaksymalna(cars));
        linguisticVariables.add(DefinedLinguisticVariables.spalanieMieszane(cars));
        linguisticVariables.add(DefinedLinguisticVariables.spalanieMiasto(cars));
        linguisticVariables.add(DefinedLinguisticVariables.spalanieAutostrada(cars));
        linguisticVariables.add(DefinedLinguisticVariables.pojemnoscBagaznika(cars));
        linguisticVariables.add(DefinedLinguisticVariables.masaPojazdu(cars));
        return linguisticVariables;
    }

    public static List<Quantifier> getQuantifiers() {
        return new ArrayList<>(DefinedQuantifiers.getAllQuantifiers());
    }

    public static List<SingleSubjectTerm> getSingleSubjectTerms(List<Quantifier> quantifiers, List<FuzzySet> sets,
                                                                List<Double> weights) {
        List<List<FuzzySet>> powerSet = generatePowerSet(sets);
        List<SingleSubjectTerm> terms = new ArrayList<>();
        // * Qualifiers loop
        for (List<FuzzySet> qualifier : powerSet) {
            for (List<FuzzySet> summarizer : powerSet) {
                if (summarizer.isEmpty() || summarizer.stream().anyMatch(qualifier::contains)) {
                    continue;
                }
                for (Quantifier quantifier : quantifiers) {
                    if (weights.isEmpty() || weights == null) {
                        terms.add(
                                new SingleSubjectTerm(
                                        cars,
                                        "wszystkich samochodów",
                                        quantifier,
                                        qualifier,
                                        summarizer
                                )
                        );
                    } else {
                        terms.add(
                                new SingleSubjectTerm(
                                        cars,
                                        "wszystkich samochodów",
                                        quantifier,
                                        qualifier,
                                        summarizer,
                                        weights
                                )
                        );
                    }
                }
            }
        }
        return terms;
    }

    public static List<DstViewModel> getDoubleSubjectTerms(List<Quantifier> quantifiers, List<FuzzySet> sets,
                                                           SubjectViewModel firstSubject, SubjectViewModel secondSubject) {
        System.out.println(quantifiers);
        System.out.println(sets);
        System.out.println(firstSubject);
        System.out.println(secondSubject);
        List<DstViewModel> terms = new ArrayList<>();
        List<List<FuzzySet>> powerSet = generatePowerSet(sets);
        System.out.println(cars.getFirst().getCarProperties());
        List<CarObject> firstObjects = DataLoader.getSubjectObjects(cars, firstSubject.valueName(), firstSubject.typeName());
        List<CarObject> secondObjects = DataLoader.getSubjectObjects(cars, secondSubject.valueName(), secondSubject.typeName());
        int index = 1;
        for (List<FuzzySet> summarizer : powerSet) {
            if (summarizer.isEmpty()) {
                continue;
            }
            Subject subject1 = new Subject(
                    firstSubject.valueName(),
                    firstSubject.name(),
                    firstSubject.typeName(),
                    firstObjects,
                    changeObjects(summarizer, firstObjects),
                    List.of()
            );
            Subject subject2 = new Subject(
                    secondSubject.valueName(),
                    secondSubject.name(),
                    secondSubject.typeName(),
                    secondObjects,
                    changeObjects(summarizer, secondObjects),
                    List.of()
            );
            DoubleSubjectTerm dstTerm = new DoubleSubjectTerm(subject1, subject2, quantifiers.getFirst());
            DstViewModel forthForm1 = new DstViewModel(
                    "4", dstTerm.getTerm4(), dstTerm.getDot4(), index++
            );
            dstTerm = new DoubleSubjectTerm(subject2, subject1, quantifiers.getFirst());
            DstViewModel forthForm2 = new DstViewModel(
                    "4", dstTerm.getTerm4(), dstTerm.getDot4(), index++
            );
            terms.addAll(List.of(forthForm1, forthForm2));
            for (List<FuzzySet> qulifier : powerSet) {
                //TODO: Czy to tak powinno być?
                if (summarizer.stream().anyMatch(qulifier::contains)) {
                    continue;
                }
                for (Quantifier quantifier : quantifiers) {
                    if (qulifier.isEmpty()) {
                        Subject anotherSubject1 = new Subject(
                                firstSubject.valueName(),
                                firstSubject.name(),
                                firstSubject.typeName(),
                                firstObjects,
                                changeObjects(summarizer, firstObjects),
                                List.of()
                        );
                        Subject anotherSubject2 = new Subject(
                                secondSubject.valueName(),
                                secondSubject.name(),
                                secondSubject.typeName(),
                                secondObjects,
                                changeObjects(summarizer, secondObjects),
                                List.of()
                        );
                        DoubleSubjectTerm dst2 = new DoubleSubjectTerm(anotherSubject1, anotherSubject2, quantifier);
                        terms.add(new DstViewModel("1", dst2.getTerm1(), dst2.getDot1(), index++));
                        dst2 = new DoubleSubjectTerm(anotherSubject2, anotherSubject1, quantifier);
                        terms.add(new DstViewModel("1", dst2.getTerm1(), dst2.getDot1(), index++));
                    } else {
                        Subject anotherSubject1 = new Subject(
                                firstSubject.valueName(),
                                firstSubject.name(),
                                firstSubject.typeName(),
                                firstObjects,
                                changeObjects(summarizer, firstObjects),
                                changeObjects(qulifier, firstObjects)
                        );
                        Subject anotherSubject2 = new Subject(
                                secondSubject.valueName(),
                                secondSubject.name(),
                                secondSubject.typeName(),
                                secondObjects,
                                changeObjects(summarizer, secondObjects),
                                changeObjects(qulifier, secondObjects)
                        );
                        DoubleSubjectTerm dst1 = new DoubleSubjectTerm(anotherSubject1, anotherSubject2, quantifier);
                        terms.add(new DstViewModel("2", dst1.getTerm2(), dst1.getDot2(), index++));
                        terms.add(new DstViewModel("3", dst1.getTerm3(), dst1.getDot3(), index++));
                        dst1 = new DoubleSubjectTerm(anotherSubject2, anotherSubject1, quantifier);
                        terms.add(new DstViewModel("2", dst1.getTerm2(), dst1.getDot2(), index++));
                        terms.add(new DstViewModel("3", dst1.getTerm3(), dst1.getDot3(), index++));
                    }
                }
            }


            //TODO: SET?
        }

        return terms;
    }

    public static <T> List<List<T>> generatePowerSet(List<T> originalList) {
        List<List<T>> powerSet = new ArrayList<>();
        int setSize = originalList.size();
        int powerSetSize = 1 << setSize; // 2^n podzbiorów

        for (int i = 0; i < powerSetSize; i++) {
            List<T> subset = new ArrayList<>();
            for (int j = 0; j < setSize; j++) {
                if ((i & (1 << j)) != 0) {
                    subset.add(originalList.get(j));
                }
            }
            powerSet.add(subset);
        }

        return powerSet;
    }

    public static List<FuzzySet> changeObjects(List<FuzzySet> sets, List<CarObject> objects) {
        return sets.stream()
                .map(s -> new FuzzySet(
                        s.getName(),
                        objects,
                        s.getValueName(),
                        s.getMembershipFunction()
                ))
                .collect(Collectors.toList());
    }
}
