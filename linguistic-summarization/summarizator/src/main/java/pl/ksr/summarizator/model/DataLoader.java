package pl.ksr.summarizator.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataLoader {
    private static final String path = "data/selected_cars.csv";

    public static List<CarObject> loadCars(){
        boolean header = true;
        List<String> headers = new ArrayList<>();
        List<CarObject> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split( ",");
                if (header) {
                    headers.addAll(Arrays.asList(values));
                    header = false;
                    continue;
                }
                CarObject car = new CarObject(headers,Arrays.asList(values));
                records.add(car);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }
}
