package pl.ksr.summarizator.model.membership;

public interface MembershipFunction {
    double calculateMembership(Object value);
    double getSupportSize();
    double getCardinality();
}
