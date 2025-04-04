package ksr.proj1.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KeyWordDao {

    public void saveKeywordsToFile(List<String> keywords, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String keyword : keywords) {
                writer.write(keyword);
                writer.newLine();
            }
        }
    }

    public List<String> loadKeywordsFromFile(String filePath) throws IOException {
        List<String> keywords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                keywords.add(line);
            }
        }
        return keywords;
    }
}
