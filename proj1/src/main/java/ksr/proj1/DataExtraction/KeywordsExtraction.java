package ksr.proj1.DataExtraction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordsExtraction {
    private static final String filePath = "src/main/resources/data/info/feldman-cia-worldfactbook-data.txt";

    public static List<String> extractKeywords() throws IOException {
        Set<String> keywordSet = new HashSet<>();
        List<String> bgfactLines = new ArrayList<>();
        List<String> types = List.of("NaturalResources", "Capital", "MemberOf", "ExportCommodities",
                "ImportCommodities", "Industries", "Agriculture");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder currentLine = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (line.endsWith("=")) {
                    currentLine.append(line, 0, line.length() - 1);
                } else {
                    currentLine.append(line);
                    for (String type : types) {
                        if (currentLine.toString().contains("bgfact") && currentLine.toString().contains(type)) {
                            bgfactLines.add(currentLine.toString().toLowerCase());
                            break;
                        }

                    }
                    currentLine.setLength(0); // Reset the StringBuilder
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Pattern squareBracketsPattern = Pattern.compile("\\[(.*?)\\]");
        for (String line : bgfactLines) {
            Matcher matcher = squareBracketsPattern.matcher(line);
            while (matcher.find()) {
                String content = matcher.group(1);
                String[] keywords = content.split(",");
                for (String keyword : keywords) {
                    String[] parts = keyword.split(" ");
                    for (String part : parts) {
                        String newPart = part.replaceAll("\"", "").trim();
                        newPart = newPart.replaceAll("\\s+", "");
                        if (newPart.matches(".*[a-zA-Z].*")) {
                            keywordSet.add(newPart);
                        }
                    }
                }
            }
        }
        List<String> stopWords = StopWords.getStopWords();
        stopWords.forEach(keywordSet::remove);

        return keywordSet.stream().toList();
    }

}
