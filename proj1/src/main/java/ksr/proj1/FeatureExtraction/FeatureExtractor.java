package ksr.proj1.FeatureExtraction;

import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.Utils.ListUtils;

import java.util.*;

public class FeatureExtractor {

    public Set<String> surnameDict = new HashSet<>();
    public Set<String> countryDict = new HashSet<>();
    public List<String> keywordDict = new ArrayList<>();

    public FeatureExtractor(List<String> surnameDict, List<String> countryDict) {
        this.surnameDict = new HashSet<>(ListUtils.deepCopyList(surnameDict));
        this.countryDict = new HashSet<>(ListUtils.deepCopyList(countryDict));
        //TODO add this later
        //this.keywordDict = keywordDict;
    }

    // test constructor
    FeatureExtractor(){}

    public void extractFeatures(ReutersElement element) {
        String elementText = element.body;
        int wordCount = this.countTextWords(element.body);
        element.c1 = this.extractC1(elementText);
        element.c2 = this.extractC2(elementText, wordCount);
        element.c3 = this.extractC3(elementText);
    }

    int extractC1(String elementText) {
        elementText = elementText.replaceAll("\\s+", "");
        return elementText.length();
    }

    float extractC2(String elementText, int wordCount) {
        //TODO is that all punctuation marks?
        int punctuationCount = 0;
        for (int i = 0; i < elementText.length(); i++) {
            if (elementText.charAt(i) == '.' || elementText.charAt(i) == ',' || elementText.charAt(i) == '!' ||
                    elementText.charAt(i) == '?' || elementText.charAt(i) == ';' || elementText.charAt(i) == ':') {
                punctuationCount++;
            }
        }

        return (float) punctuationCount / wordCount;
    }

    String extractC3(String elementText) {
        if (elementText == null || elementText.isEmpty() || surnameDict.isEmpty()) {
            return null;
        }
        //TODO manage punctuation marks near surnames
        //TODO manage surname orders
        //TODO example: Bond and bond <-- manage this
        elementText = elementText.toLowerCase();
        String[] words = elementText.split("\\s+");
        Map<String, Integer> surnameCount = new HashMap<>();
        for (String word : words) {
            if (surnameDict.contains(word)) {
                surnameCount.put(word, surnameCount.getOrDefault(word, 0) + 1);
            }
        }
        System.out.println(surnameDict);
        System.out.println(surnameCount);
        return surnameCount.isEmpty() ? null : Collections.max(surnameCount.entrySet(),
                                                            Map.Entry.comparingByValue()).getKey();
    }

    String extractC4(String elementText) {
        return "";
    }

    String extractC5(String elementText) {
        return "";
    }

    int extractC6(String elementText) {
        return 0;
    }

    String extractC7(String elementText) {
        return "";
    }

    String extractC8(String elementText) {
        return "";
    }

    float extractC9(String elementText) {
        return 0;
    }

    float extractC10(String elementText) {
        return 0;
    }

    int extractC11(String elementText) {
        return 0;
    }

    int extractC12(String elementText) {
        return 0;
    }

    int countTextWords(String text) {
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
