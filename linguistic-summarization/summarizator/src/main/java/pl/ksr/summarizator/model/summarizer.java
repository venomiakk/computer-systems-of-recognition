package pl.ksr.summarizator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class summarizer {
    String name;
    List<double> parameters;
    public summarizer(String name, List<double> parameters) {
        this.name = name;
        this.parameters = parameters;

    }

    public fuzzySet summarize(carObject car,String valueName) {
        double membership = 0;
        if (parameters.size() == 5) {
            membership = calc.cal((double)car.carProperties.get(valueName), parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3));
        } else if (parameters.size() == 3) {
            membership = calc.cal((double)car.carProperties.get(valueName), parameters.get(0), parameters.get(1), parameters.get(2));
        } else if (parameters.size() == 2) {
            membership = calc.cal((double)car.carProperties.get(valueName), parameters.get(0), parameters.get(1));
        } else {
            throw new IllegalArgumentException("Unsupported number of parameters: " + parameters.size());
        }
        return new fuzzySet(car, membership);
    }
}
