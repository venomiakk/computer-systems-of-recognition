package ksr.proj1.Classifier.DistanceMetrics;

import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;

public interface Distances {
    //TODO: poprawić wszystkie obliczenia na double i string
    //TODO: kiedy w jednym jest tekst a w drugim nie to 1
    //TODO: zmienić porzedział ngramów
    double calculateDistance(FeatureVector featureVector1, FeatureVector featureVector2,
                             WordsSimilarityMeasure wordSimMeasure);
}
