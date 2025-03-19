package ksr.proj1.Extraction;

import java.util.ArrayList;
import java.util.List;

public class FeatureExtraction {
    public static void extractFeatures(ReutersElement element) {
        int wordCount = countTextWords(element.body);
        element.c1 = extractC1(element);
        element.c2 = extractC2(element, wordCount);
    }

    private static int extractC1(ReutersElement element) {
        String text = element.body;
        text = text.replaceAll("\\s+", "");
        return text.length();
    }

    private static float extractC2(ReutersElement element, int wordCount) {
        //TODO is that all punctuation marks?
        String text = element.body;
        int punctuationCount = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '.' || text.charAt(i) == ',' || text.charAt(i) == '!' || text.charAt(i) == '?' ||
                    text.charAt(i) == ';' || text.charAt(i) == ':') {
                punctuationCount++;
            }
        }

        return (float) punctuationCount / wordCount;
    }

    private static int countTextWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        // Split on any whitespace character sequence
        //TODO split on " " or "\\s+"?
        String[] wordArray = text.split(" ");

        for (int i = 0; i < wordArray.length; i++) {
            wordArray[i] = wordArray[i].replaceAll("[^a-zA-Z]", "");
        }

        // Filter out any remaining empty strings
        List<String> words = new ArrayList<>();
        for (String word : wordArray) {
            if (!word.isEmpty()) {
                words.add(word);
            }
        }

        return  words.size();
    }

}
