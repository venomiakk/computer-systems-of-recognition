package pl.ksr.summarizator.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class summarizator {
    public static List<carObject> readCsv() {
        boolean header = true;
        List<String> headers = new ArrayList<>();
        List<carObject> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/selected_cars.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split( ",");
                if (header) {
                    headers.addAll(Arrays.asList(values));
                    header = false;
                    continue;
                }
                carObject car = new carObject(headers,Arrays.asList(values));
                records.add(car);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }
}
