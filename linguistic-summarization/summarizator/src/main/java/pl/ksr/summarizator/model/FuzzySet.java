package pl.ksr.summarizator.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.max;

public class FuzzySet {

    private final String name;
    private Map<CarObject, Double> fuzzySet = new HashMap<CarObject, Double>();

    public FuzzySet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<CarObject, Double> getFuzzySet() {
        return fuzzySet;
    }

    public void addCar(CarObject car, double membership) {
        fuzzySet.put(car, membership);
    }

    //TODO; dodac przestrzen rozwazan
    public double getCard() {
        double sum = 0;
        for (double value : fuzzySet.values()) {
            sum += value;
        }
        return sum;
    }

    public int getSize() {
        return fuzzySet.size();
    }

    public List<CarObject> getSupp() {
        return fuzzySet.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .toList();
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
}
