package ksr.proj1.DataExtraction;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TfIdfKeyWordExtraction {

    public List<String> stopWords = StopWords.getStopWords();
    public HashMap<String, Float> wordCount = new HashMap<>();
    public TfIdfKeyWordExtraction(List<String> documents) throws IOException {

        for (String document : documents) {
            document = document.replaceAll("[^\\sa-zA-Z^-]", "");
            String[] words = document.split("\\s+");
            for (String word : words) {

                if (!word.isEmpty() && !stopWords.contains(word.toLowerCase())) {
                    word = word.toLowerCase();
                    wordCount.put(word, tfIdf(word, document, documents));
                }
            }
        }
    }

    public float tf(String word, String document) {
        String[] words = document.split("\\s+");
        int count = 0;
        for (String w : words) {
            if (w.equalsIgnoreCase(word)) {
                count++;
            }
        }
        return (float) count / words.length;
    }

    public float idf(String word, List<String> documents) {
        int count = 0;
        for (String document : documents) {
            if (document.toLowerCase().contains(word.toLowerCase())) {
                count++;
            }
        }
        return (float) Math.log(documents.size() / (1 + count));
    }

    public float tfIdf(String word, String document, List<String> documents) {
        return tf(word, document) * idf(word, documents);
    }
}
