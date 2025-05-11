package ksr.knn.FeatureExtraction;

import ksr.knn.DataExtraction.ReutersElement;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FeatureExtractor {

    public Set<String> surnameDict = new HashSet<>();
    public Set<String> countryDict = new HashSet<>();
    public Set<String> keywordDict = new HashSet<>();
    public Set<String> stopWords = new HashSet<>();

    public FeatureExtractor(List<String> surnameDict, List<String> countryDict, List<String> keywordDict, List<String> stopWords) {
        this.surnameDict = new HashSet<>(surnameDict);
        this.countryDict = new HashSet<>(countryDict);
        this.keywordDict = new HashSet<>(keywordDict);
        this.stopWords = new HashSet<>(stopWords);
    }

    public FeatureExtractor() {

    }

    // test constructor


    public FeatureVector extractFeatures(ReutersElement element) throws IOException {
        String elementText = element.body;
        int wordCount = this.countTextWords(element.body);
        int c1 = this.extractC1(elementText);
        float c2 = this.extractC2(elementText, wordCount);
        String c3 = this.extractC3(elementText);
        String c4 = this.extractC4(elementText);
        List<String> properNouns = this.getProperNouns(elementText);
        String c5 = this.extractC5(elementText, properNouns);
        int c6 = this.extractC6(elementText);
        String c7 = this.extractC7(elementText);
        String c8 = this.extractC8(elementText);
        float c9 = this.extractC9(elementText);
        float c10 = extractC10(properNouns.size(), wordCount);
        int c11 = this.extractC11(elementText);
        int c12 = this.extractC12(elementText);

        return new FeatureVector(element.id, element.label, element.index, c1, c2, c3, c4, c5, c6, c7, c8,
                c9, c10, c11, c12);
    }

    int extractC1(String elementText) {
        /*
         * Text length
         * */
        // Remove all whitespace characters
        elementText = elementText.replaceAll("\\s+", "");
        return elementText.length();
    }

    float extractC2(String elementText, int wordCount) {
        /*
         * Punctuation density
         */
        if (wordCount == 0) {
            return 0;
        }

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
        /*
         * Most common surname
         */
        String[] words = clearTextForWords(elementText);
        if (words == null || words.length == 0 || surnameDict.isEmpty()) {
            return null;
        }
        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty() && Character.isUpperCase(word.charAt(0))) {
                filteredWords.add(word.toLowerCase());
            }
        }
        // Count occurrences of each surname
        Map<String, Integer> surnameCount = new LinkedHashMap<>();
        for (String word : filteredWords) {
            if (surnameDict.contains(word)) {
                surnameCount.put(word, surnameCount.getOrDefault(word, 0) + 1);
            }
        }
        // Return the most common surname
        return surnameCount.isEmpty() ? null : Collections.max(surnameCount.entrySet(),
                Map.Entry.comparingByValue()).getKey();
    }

    String extractC4(String elementText) {
        /*
         * Most common country
         * */
        String[] words = clearTextForWords(elementText);
        if (words == null || words.length == 0 || countryDict.isEmpty()) {
            return null;
        }
        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                filteredWords.add(word.toLowerCase());
            }
        }
        Map<String, Integer> countryCount = new LinkedHashMap<>();
        for (String word : filteredWords) {
            if (countryDict.contains(word)) {
                countryCount.put(word, countryCount.getOrDefault(word, 0) + 1);
            }
        }
        //System.out.println(countryCount);
        return countryCount.isEmpty() ? null : Collections.max(countryCount.entrySet(),
                Map.Entry.comparingByValue()).getKey();
    }

    String extractC5(String elementText, List<String> properNouns) {
        /*
         * Most common proper nouns
         * */
        if (properNouns == null || properNouns.isEmpty()) {
            return null;
        }

        List<Integer> properNounsCount = new ArrayList<>();
        for (String properNoun : properNouns) {
            properNounsCount.add(Collections.frequency(properNouns, properNoun));
        }

        int maxIndex = properNounsCount.indexOf(Collections.max(properNounsCount));

        return properNouns.get(maxIndex);
    }

    int extractC6(String elementText) {
        String[] words = clearTextForWords(elementText);
        if (words == null || words.length == 0) {
            return 0;
        }
        int keywordCount = 0;
        for (String word : words) {
            if (keywordDict.contains(word.toLowerCase())) {
                keywordCount++;
            }
        }
        return keywordCount;
    }

    String extractC7(String elementText) {
        String[] words = clearTextForWords(elementText);
        if (words == null || words.length == 0) {
            return null;
        }

        Map<String, Integer> keywordCount = new HashMap<>();
        Map<String, Integer> firstOccurrence = new HashMap<>();
        int position = 0;

        for (String word : words) {
            String lowerCaseWord = word.toLowerCase();
            if (keywordDict.contains(lowerCaseWord)) {
                keywordCount.put(lowerCaseWord, keywordCount.getOrDefault(lowerCaseWord, 0) + 1);
                firstOccurrence.putIfAbsent(lowerCaseWord, position);
            }
            position++;
        }

        String mostFrequentKeyword = null;
        int maxCount = 0;
        int earliestPosition = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : keywordCount.entrySet()) {
            String keyword = entry.getKey();
            int count = entry.getValue();
            int occurrence = firstOccurrence.get(keyword);

            if (count > maxCount || (count == maxCount && occurrence < earliestPosition)) {
                mostFrequentKeyword = keyword;
                maxCount = count;
                earliestPosition = occurrence;
            }
        }

        return mostFrequentKeyword;
    }

    String extractC8(String elementText){
        String[] words = clearTextForWords(elementText);
        if (words == null || words.length == 0) {
            return null;
        }

        Map<String, Integer> keywordCount = new HashMap<>();
        Map<String, Integer> lastOccurrence = new HashMap<>();
        int position = 0;

        for (String word : words) {
            String lowerCaseWord = word.toLowerCase();
            if (keywordDict.contains(lowerCaseWord)) {
                keywordCount.put(lowerCaseWord, keywordCount.getOrDefault(lowerCaseWord, 0) + 1);
                lastOccurrence.put(lowerCaseWord, position);
            }
            position++;
        }

        String leastFrequentKeyword = null;
        int minCount = Integer.MAX_VALUE;
        int latestPosition = -1;

        for (Map.Entry<String, Integer> entry : keywordCount.entrySet()) {
            String keyword = entry.getKey();
            int count = entry.getValue();
            int occurrence = lastOccurrence.get(keyword);

            if (count < minCount || (count == minCount && occurrence > latestPosition)) {
                leastFrequentKeyword = keyword;
                minCount = count;
                latestPosition = occurrence;
            }
        }

        return leastFrequentKeyword;
    }


    float extractC9(String elementText) {
        String[] words = clearTextForWords(elementText);
        if (words == null || words.length == 0) {
            return 0;
        }
        int keywordCount = 0;
        for (String word : words) {
            if (keywordDict.contains(word.toLowerCase())) {
                keywordCount++;
            }
        }
        return (float) keywordCount / words.length;
    }

    float extractC10(int properNounsCount, int wordCount) {
        /*
         * Proper nouns density
         */
        if (wordCount == 0) {
            return 0;
        }
        return (float) properNounsCount / wordCount;
    }

    int extractC11(String elementText) {
        String[] words = clearTextForWords(elementText);
        if (words == null || words.length == 0) {
            return 0;
        }
        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty() && Character.isUpperCase(word.charAt(0))) {
                filteredWords.add(word.toLowerCase());
            }
        }
        int count = 0;
        for (String word : filteredWords) {
            if (surnameDict.contains(word)) {
                count++;
            }
        }
        return count;
    }

    int extractC12(String elementText) {
        String[] words = clearTextForWords(elementText);
        if (words == null || words.length == 0) {
            return 0;
        }
        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                filteredWords.add(word.toLowerCase());
            }
        }
        int count = 0;
        for (String word : filteredWords) {
            if (countryDict.contains(word)) {
                count++;
            }
        }
        return count;
    }

    int countTextWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        // Split on any whitespace character sequence
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

        return words.size();
    }

    String[] clearTextForWords(String elementText) {
        if (elementText == null || elementText.isEmpty()) {
            return null;
        }
        // Remove all non-alphabetic characters except for hyphens
        elementText = elementText.replaceAll("[^\\sa-zA-Z^-]", "");
        // Split on any whitespace character sequence
        return elementText.split("\\s+");
    }

    List<String> getProperNouns(String elementText) {
        // not perfect but works... kind of
        Pattern pattern = Pattern.compile("\\b[A-Z][a-z]+(?:\\s[A-Z][a-z]+)+\\b");
        Matcher matcher = pattern.matcher(elementText);

        List<String> properNouns = new ArrayList<>();
        while (matcher.find()) {
            properNouns.add(matcher.group());
        }
        properNouns.replaceAll(String::toLowerCase);
        for (String stopWord : stopWords) {
            properNouns.removeIf(stopWord::equals);
        }
        return properNouns;
    }
}
