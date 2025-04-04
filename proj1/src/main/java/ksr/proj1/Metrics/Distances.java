package ksr.proj1.Metrics;

import ksr.proj1.FeatureExtraction.FeatureVector;

public interface Distances {
    public float countDistance(FeatureVector featureVector1, FeatureVector featureVector2, WordsSimilirityMetrics metric);
}
