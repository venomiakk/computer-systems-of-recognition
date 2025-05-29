package pl.ksr.summarizator.model.membership;

public interface MembershipFunction {
    //TODO: Add modifier value (hedges)
    double calculateMembership(Object value);
}
