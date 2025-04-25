package ksr.proj1.Classifier.Measures;

import java.util.ArrayList;
import java.util.HashSet;

public class JaccardMeasure implements WordsSimilarityMeasure {
    @Override
    public double calc(String word1, String word2, int minLength, int maxLength) {
        String tempWord1 = word1.toLowerCase().replaceAll("\\s+", "");
        String tempWord2 = word2.toLowerCase().replaceAll("\\s+", "");
        ArrayList<Character> intersection = new ArrayList<>();
        int length = Math.min(tempWord1.length(), tempWord2.length());
        String shorterWord = tempWord1.length() < tempWord2.length() ? tempWord1 : tempWord2;
        String longerWord = tempWord1.length() < tempWord2.length() ? tempWord2 : tempWord1;

        for (int i = 0; i < length; i++) {
            if (longerWord.contains(String.valueOf(shorterWord.charAt(i)))) {
                intersection.add(shorterWord.charAt(i));
            }
        }

        HashSet<Character> union = new HashSet<>();
        for (char c : tempWord1.toCharArray()) {
            union.add(c);
        }
        for (char c : tempWord2.toCharArray()) {
            union.add(c);
        }

        return (double) intersection.size() / union.size();
    }
}
