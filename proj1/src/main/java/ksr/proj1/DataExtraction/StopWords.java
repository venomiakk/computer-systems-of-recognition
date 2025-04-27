package ksr.proj1.DataExtraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StopWords {
    public static List<String> stopWords = null;
    public static List<String> getStopWords() throws IOException {
        List<String> stopwords = Files.readAllLines(Paths.get("src/main/resources/data/stop_words_english.txt"));
        //System.out.println(stopwords);
        stopWords = stopwords;
        return stopwords;
    }
}
