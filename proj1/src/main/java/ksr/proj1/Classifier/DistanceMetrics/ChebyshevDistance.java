package ksr.proj1.Classifier.DistanceMetrics;

import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;

import java.util.ArrayList;

public class ChebyshevDistance implements  Distances{
    @Override
    public double calculateDistance(FeatureVector featureVector1, FeatureVector featureVector2, WordsSimilarityMeasure wordSimMeasure) {
        double maxDistance = 0.0d;
        for (int i = 0; i < featureVector1.features.size(); i++) {
            Object feature1 = featureVector1.features.get(i);
            Object feature2 = featureVector2.features.get(i);

            double currentDist;

            if (feature1 == null || feature2 == null || feature1.getClass() != feature2.getClass()) {
                continue;
            } else if (feature1 instanceof String) {
                double similarity = wordSimMeasure.calc((String) feature1, (String) feature2, 1, 3);
                currentDist = Math.abs(1.0d - similarity);
            } else {
                double val1 = ((Number) feature1).doubleValue();
                double val2 = ((Number) feature2).doubleValue();
                currentDist = Math.abs(val1 - val2);
            }

            if (currentDist > maxDistance) {
                maxDistance = currentDist;
            }
        }
        return maxDistance;
    }
}
