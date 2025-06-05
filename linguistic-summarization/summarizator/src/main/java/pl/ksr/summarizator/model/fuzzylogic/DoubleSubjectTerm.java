package pl.ksr.summarizator.model.fuzzylogic;


public class DoubleSubjectTerm {
    // ?: Maybe it should only contain information about term
    // ?: And we should have another class that handles the logic or something like that?
    // ?: ...
    private final Subject firstSubject;
    private final Subject secondSubject;
    private final Quantifier quantifier;
    private final double t1;

    public DoubleSubjectTerm(Subject firstSubject, Subject secondSubject, Quantifier quantifier) {
        this.firstSubject = firstSubject;
        this.secondSubject = secondSubject;
        this.quantifier = quantifier;
        this.t1 = 0;
    }
}
