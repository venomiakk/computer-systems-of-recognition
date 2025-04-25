package ksr.proj1.Classifier.Measures;

public interface WordsSimilarityMeasure {
    double calc(String word1, String word2, int minLength, int maxLength);
}
