package pl.ksr.summarizator.view;

import java.util.List;

public class DataDefinition {
    public String type;
    public String name;

    // Pole tylko dla "zmienna"
    public String valueName;
    public List<FunctionDefinition> functions;

    // Pole tylko dla "kwantyfikator"
    public FunctionDefinition function;
}
