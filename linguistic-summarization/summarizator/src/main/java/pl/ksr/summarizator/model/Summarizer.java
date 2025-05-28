package pl.ksr.summarizator.model;

import java.util.List;

public class Summarizer {
    private final String name;
    private final List<Double> parameters;

    public Summarizer(String name, List<Double> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public List<Double> getParameters() {
        return parameters;
    }

    public Double summarize(CarObject car, String valueName) {
        double membership;
        if (parameters.size() == 4) {
            membership = MathFunctions.calc(Double.parseDouble((String)car.getCarProperties().get(valueName)), parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3));
        } else if (parameters.size() == 3) {
            membership = MathFunctions.calc(Double.parseDouble((String)car.getCarProperties().get(valueName)), parameters.get(0), parameters.get(1), parameters.get(2));
        } else if (parameters.size() == 2) {
            membership = MathFunctions.calc(Double.parseDouble((String)car.getCarProperties().get(valueName)), parameters.get(0), parameters.get(1));
        } else {
            throw new IllegalArgumentException("Unsupported number of parameters: " + parameters.size());
        }
        return membership;
    }


}
