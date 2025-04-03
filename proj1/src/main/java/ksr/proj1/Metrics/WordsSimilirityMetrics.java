package ksr.proj1.Metrics;

public interface WordsSimilirityMetrics {
    public float calc(String word1, String word2, int minLength, int maxLength);
}
