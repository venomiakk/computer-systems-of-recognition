package ksr.proj1.DataExtraction;

import ksr.proj1.Utils.KeyWordDao;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TfIdfKeyWordExtraction {

    KeyWordDao dao = new KeyWordDao();
    public TfIdfKeyWordExtraction() {
    }

    public List<Map.Entry<String, Float>> keyWords(int numberOfWords, List<String> documents) throws IOException {
        List<String> stopWords = StopWords.getStopWords();
        Map<String, Float> wordCount = new HashMap<>();
        Map<String, Float> idfMap = computeIdf(documents);

        for (String document : documents) {
            document = document.replaceAll("[^\\sa-zA-Z^-]", "");
            String[] words = document.split("\\s+");
            Map<String, Float> tfMap = computeTf(words, stopWords);

            for (Map.Entry<String, Float> entry : tfMap.entrySet()) {
                String word = entry.getKey();
                float tfidf = entry.getValue() * idfMap.getOrDefault(word, 0f);
                wordCount.merge(word, tfidf, Math::max);
            }
        }
        dao.saveKeywordsToFile(new ArrayList<>(wordCount.keySet()), "kayWordDictionary.txt");
        return getTopNEntries(wordCount, numberOfWords);
    }

    private Map<String, Float> computeTf(String[] words, List<String> stopWords) {
        Map<String, Integer> wordFreq = new HashMap<>();
        int totalWords = 0;

        for (String word : words) {
            if (!word.isEmpty() && !stopWords.contains(word.toLowerCase())) {
                word = word.toLowerCase();
                wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                totalWords++;
            }
        }

        Map<String, Float> tfMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
            tfMap.put(entry.getKey(), (float) entry.getValue() / totalWords);
        }
        return tfMap;
    }

    public Map<String, Float> computeIdf(List<String> documents) throws IOException {
        List<String> stopWords = StopWords.getStopWords();
        Map<String, Integer> docFreq = new HashMap<>();
        int totalDocs = documents.size();

        for (String document : documents) {
            document = document.replaceAll("[^\\sa-zA-Z^-]", "");
            Set<String> uniqueWords = Arrays.stream(document.split("\\s+"))
                    .map(String::toLowerCase)
                    .filter(word -> !stopWords.contains(word))
                    .collect(Collectors.toSet());

            for (String word : uniqueWords) {
                docFreq.put(word, docFreq.getOrDefault(word, 0) + 1);
            }
        }

        Map<String, Float> idfMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : docFreq.entrySet()) {
            idfMap.put(entry.getKey(), (float) Math.log10(totalDocs / (1.0 + entry.getValue())));
        }
        return idfMap;
    }

    public static List<Map.Entry<String, Float>> getTopNEntries(Map<String, Float> map, int n) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Float>comparingByValue().reversed())
                .limit(n)
                .collect(Collectors.toList());
    }
}