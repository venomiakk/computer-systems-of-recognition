package pl.ksr.summarizator.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LinguisticVariable {
    private final String name;
    private Map<String, Summarizer> summarizers = new HashMap<>();
    private double minX;
    private double maxX;

    public LinguisticVariable(String name) {
        this.name = name;
    }

    public void addSummarizer(String label, List<Double> parameters) {
        summarizers.put(label, new Summarizer(label, parameters));
    }

    public Summarizer getSummarizer(String label) {
        return summarizers.get(label);
    }

    public String getName() {
        return name;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }
}
