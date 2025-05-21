package pl.ksr.summarizator.model;

import java.util.List;


public class linguisticVariable {
    String name;
    List<String> values;
    Double[] domain;
    public linguisticVariable(String name, List<String> values, double beggining, double end) {
        this.name = name;
        this.values = values;
        this.domain = new Double[2];
        this.domain[0] = beggining;
        this.domain[1] = end;
    }
}
