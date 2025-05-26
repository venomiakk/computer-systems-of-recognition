package pl.ksr.summarizator.model;
import java.util.List;

public class Quantifier {
    private String name;
    private List<Double> parameters;
    private boolean isAbsolute;
    //TODO dziedzina moze tez byc

    public Quantifier(String name, List<Double> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public double calculateMembership(double ratio) {
        switch (parameters.size()) {
            case 2:
                return MathFunctions.calc(ratio, parameters.get(0), parameters.get(1));
            case 3:
                return MathFunctions.calc(ratio, parameters.get(0), parameters.get(1), parameters.get(2));
            case 4:
                return MathFunctions.calc(ratio, parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3));
            default:
                throw new IllegalArgumentException("Unsupported quantifier shape.");
        }
    }
}