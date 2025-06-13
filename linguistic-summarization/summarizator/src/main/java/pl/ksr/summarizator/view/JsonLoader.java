package pl.ksr.summarizator.view;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.ksr.summarizator.model.CarObject;
import pl.ksr.summarizator.model.DataLoader;
import pl.ksr.summarizator.model.fuzzylogic.FuzzySet;
import pl.ksr.summarizator.model.fuzzylogic.LinguisticVariable;
import pl.ksr.summarizator.model.fuzzylogic.Quantifier;
import pl.ksr.summarizator.model.membership.Gaussian;
import pl.ksr.summarizator.model.membership.MembershipFunction;
import pl.ksr.summarizator.model.membership.Trapezoidal;
import pl.ksr.summarizator.model.membership.Triangular;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonLoader {
    public static List<Object> processJson(File file) throws IOException {
        List<CarObject> cars = DataLoader.loadCars();
        List<Object> newData = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<DataDefinition> dataDefinitions = mapper.readValue(
                file,
                new TypeReference<>() {
                }
        );

        for (DataDefinition def : dataDefinitions) {
            if ("zmienna".equals(def.type) && def.functions != null) {
                List<FuzzySet> fuzzySets = new ArrayList<>();
                for (FunctionDefinition f : def.functions) {
                    //System.out.println("  Funkcja: " + f.name + " (" + f.type + ")");
                    //System.out.println("  Parametry: " + f.parameters);
                    fuzzySets.add(
                            new FuzzySet(
                                    def.name + ": " + f.name,
                                    cars,
                                    def.valueName,
                                    createMembershipFunction(f)
                            )
                    );
                    //System.out.println(createMembershipFunction(f).getClass());
                }
               newData.add(new LinguisticVariable(def.name, fuzzySets, 0, 0));
            } else if ("kwantyfikator".equals(def.type) && def.function != null) {
                //System.out.println("  Kwantyfikator: " + def.function.name);
                //System.out.println("  Parametry: " + def.function.parameters);
                newData.add(
                        new Quantifier(
                                def.name,
                                createMembershipFunction(def.function),
                                0.0,
                                1.0,
                                false
                        )
                );
            }
        }
        return newData;
    }

    private static MembershipFunction createMembershipFunction(FunctionDefinition f) {
        if (Objects.equals(f.type, "trapez")) {
            return new Trapezoidal(
                    f.parameters.get("leftBottom"),
                    f.parameters.get("leftUp"),
                    f.parameters.get("rightUp"),
                    f.parameters.get("rightBottom"));
        } else if (Objects.equals(f.type, "trójkąt")) {
            return new Triangular(
                    f.parameters.get("left"),
                    f.parameters.get("vertex"),
                    f.parameters.get("right"));
        } else if (Objects.equals(f.type, "gauss")) {
            return new Gaussian(
                    f.parameters.get("mean"),
                    f.parameters.get("sigma"),
                    f.parameters.get("leftBound"),
                    f.parameters.get("rightBound"));
        }
        throw new IllegalArgumentException("Nieobsługiwany typ funkcji: " + f.type);
    }
}
