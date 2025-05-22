package pl.ksr.summarizator.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class linguisticVariable {
    private String name;
    private Map<String, summarizer> summarizers = new HashMap<>();

    public linguisticVariable(String name) {
        this.name = name;
    }

    public void addSummarizer(String label, List<Double> parameters) {
        summarizers.put(label, new summarizer(label, parameters));
    }
}
