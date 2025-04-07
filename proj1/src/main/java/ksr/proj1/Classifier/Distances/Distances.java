package ksr.proj1.Classifier.Distances;

import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Metrics.WordsSimilirityMetrics;

public interface Distances {
    public float countDistance(FeatureVector featureVector1, FeatureVector featureVector2, WordsSimilirityMetrics metric);
}
