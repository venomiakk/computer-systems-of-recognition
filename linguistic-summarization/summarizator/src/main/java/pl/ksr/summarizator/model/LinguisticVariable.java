package pl.ksr.summarizator.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LinguisticVariable {
    private String name;
    private Map<String, Summarizer> summarizers = new HashMap<>();

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
}
