package ksr.proj1.Classifier.DistanceMetrics;

import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;

public class ManhattanDistance implements Distances {
    @Override
    public double calculateDistance(FeatureVector featureVector1, FeatureVector featureVector2, WordsSimilarityMeasure wordSimMeasure) {
        double distance = 0.0d;
        for(int i=0; i< featureVector1.features.size();i++){
            Object feature1 = featureVector1.features.get(i);
            Object feature2 = featureVector2.features.get(i);
            //TODO: czy dodawanie 1.0d jest zawsze ok?
            if (feature1 == null || feature2 == null || feature1.getClass() != feature2.getClass()) {
                distance += 1.0d;
            }
            else if (feature1 instanceof String) {
                double similarity = wordSimMeasure.calc((String) feature1, (String) feature2, 1, 3);
                distance += Math.abs((1 - similarity));
            } else {
                double val1 = ((Number) feature1).doubleValue();
                double val2 = ((Number) feature2).doubleValue();
                distance += Math.abs(val1 - val2);
            }
        }
        return distance;
    }
}
