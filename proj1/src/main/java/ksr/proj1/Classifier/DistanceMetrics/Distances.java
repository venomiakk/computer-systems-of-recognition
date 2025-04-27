package ksr.proj1.Classifier.DistanceMetrics;

import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;

public interface Distances {
    double calculateDistance(FeatureVector featureVector1, FeatureVector featureVector2,
                             WordsSimilarityMeasure wordSimMeasure);
}
