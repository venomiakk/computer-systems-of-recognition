package ksr.knn.Classifier.DistanceMetrics;

import ksr.knn.FeatureExtraction.FeatureVector;
import ksr.knn.Classifier.Measures.WordsSimilarityMeasure;

public interface Distances {
    double calculateDistance(FeatureVector featureVector1, FeatureVector featureVector2,
                             WordsSimilarityMeasure wordSimMeasure);
}
