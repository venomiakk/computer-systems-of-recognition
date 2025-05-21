package pl.ksr.summarizator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class summarizer {
    String name;
    HashMap<String,List<double>> parameters;
    List<linguisticVariable> linguisticVariables;
    public summarizer(String name) {
        this.name = name;
    }

    public List<fuzzySet> summarize(carObject car,String valueName) {
        return null;
    }
}
