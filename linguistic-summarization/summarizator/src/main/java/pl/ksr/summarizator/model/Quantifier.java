package pl.ksr.summarizator.model;

import pl.ksr.summarizator.model.membership.MembershipFunction;


public class Quantifier {
    private final String name;
    private final MembershipFunction membershipFunction;
    private final double leftBound;
    private final double rightBound;
    private final boolean isAbsolute;

    public Quantifier(String name, MembershipFunction membershipFunction, double leftBound, double rightBound, boolean isAbsolute) {
        this.name = name;
        this.membershipFunction = membershipFunction;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.isAbsolute = isAbsolute;
    }

    public double calculateMembership(Object value) {
        if (value instanceof Number) {
            return membershipFunction.calculateMembership(value);
        }
        throw new IllegalArgumentException("Value must be a number");
    }

    public String getName() {
        return name;
    }

    public MembershipFunction getMembershipFunction() {
        return membershipFunction;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }

    public boolean isAbsolute() {
        return isAbsolute;
    }
}