package pl.ksr.summarizator.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class carObject {
    Map<String, Object> carProperties = new HashMap<>();

    public carObject(List<String> headers, List<Object> value) {
        for (int i = 0; i < headers.size(); i++) {
            carProperties.put(headers.get(i), value.get(i));
        }
    }

    public Map<String, Object> getCarProperties() {
        return carProperties;
    }
}