package pl.ksr.summarizator.view;

import pl.ksr.summarizator.model.CarObject;
import pl.ksr.summarizator.model.DataLoader;
import pl.ksr.summarizator.model.DefinedLinguisticVariables;
import pl.ksr.summarizator.model.DefinedQuantifiers;
import pl.ksr.summarizator.model.fuzzylogic.FuzzySet;
import pl.ksr.summarizator.model.fuzzylogic.LinguisticVariable;
import pl.ksr.summarizator.model.fuzzylogic.Quantifier;
import pl.ksr.summarizator.model.fuzzylogic.SingleSubjectTerm;

import java.util.ArrayList;
import java.util.List;

public class SingleSubjectService {
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

    public static List<SingleSubjectTerm> getExampleSingleSubjectTerms() {
        List<SingleSubjectTerm> exampleTerms = new ArrayList<>();
        List<Quantifier> quantifiers = getQuantifiers();
        for (FuzzySet summarizer1 : DefinedLinguisticVariables.mocPojazdu(cars).getFuzzySets()) {
            for (FuzzySet summarizer2 : DefinedLinguisticVariables.spalanieMieszane(cars).getFuzzySets()) {
                for (Quantifier quantifier : quantifiers) {
                    SingleSubjectTerm term = new SingleSubjectTerm(
                            cars,
                            "wszystkich samochodow",
                            quantifier,
                            List.of(summarizer1),
                            List.of(summarizer2)
                    );
                    System.out.println(term.getTerm());
                    exampleTerms.add(term);
                }
            }
        }
        return exampleTerms;
    }
}
