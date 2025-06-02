package pl.ksr.summarizator.model.fuzzylogic;

import pl.ksr.summarizator.model.CarObject;
import pl.ksr.summarizator.model.membership.MembershipFunction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.max;

public class FuzzySet {

    private final String name;
    private Map<CarObject, Double> fuzzySet = new LinkedHashMap<>();
    private MembershipFunction membershipFunction;
    private String valueName;
    //private String linguisticModifier;
    //private double modifierValue;

    public FuzzySet(String name, List<CarObject> cars, String valueName, MembershipFunction membershipFunction) {
        this.name = name;
        this.membershipFunction = membershipFunction;
        this.valueName = valueName;
        for (CarObject car : cars) {
            double membership = membershipFunction.calculateMembership(car.getCarProperties().get(valueName));
            fuzzySet.put(car, membership);
        }
    }

    public void addCar(CarObject car, double membership) {
        fuzzySet.put(car, membership);
    }

    public List<CarObject> getSupport() {
        return fuzzySet.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .toList();
    }

    public double getCardinality() {
        return fuzzySet.values()
                .stream()
                .mapToDouble(v -> v)
                .sum();
    }

    public double getDegreeOfFuzziness() {
        return getSupport().size() / (double) getSize();
    }

    public int getSize() {
        return fuzzySet.size();
    }


    public boolean isNormal() {
        double max = max(fuzzySet.values());
        if (max == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void normalize() {
        double max = max(fuzzySet.values());
        for (Map.Entry<CarObject, Double> entry : fuzzySet.entrySet()) {
            entry.setValue(entry.getValue() / max);
        }
    }

    public boolean isConvex() {
        return true;
    }

    public String getName() {
        return name;
    }

    public Map<CarObject, Double> getFuzzySet() {
        return fuzzySet;
    }

    public double getMembership(CarObject car) {
        // If the car is not in the fuzzy set, return 0 membership
        return fuzzySet.getOrDefault(car, 0.0);
    }

    public String getValueName() {
        return valueName;
    }

    public double calculateMembership(Double value) {
        return membershipFunction.calculateMembership(value);
    }

    public MembershipFunction getMembershipFunction() {
        return membershipFunction;
    }
}
