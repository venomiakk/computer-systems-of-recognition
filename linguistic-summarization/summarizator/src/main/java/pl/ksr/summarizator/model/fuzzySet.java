package pl.ksr.summarizator.model;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.max;

public class fuzzySet {
    Map<carObject, Double> fuzzySet = new HashMap<carObject,Double>();
    public fuzzySet(carObject car, double value) {
        fuzzySet.put(car, value);
    }

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

    public int getSupp(){
        int supp = 0;
        for (double value : fuzzySet.values()) {
            if (value > 0) {
                supp++;
            }
        }
        return supp;
    }

    public boolean isNormal(){
        double max = max(fuzzySet.values());
        if (max == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void normalize(){
        double max = max(fuzzySet.values());
        for (Map.Entry<carObject, Double> entry : fuzzySet.entrySet()) {
            entry.setValue(entry.getValue() / max);
        }
    }

    public boolean isConvex(){
       return true;
    }
}
