package ksr.proj1.Classifier.DistanceMetrics;

import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;

public class EuclideanDistance implements Distances {
    @Override
    public float calculateDistance(FeatureVector featureVector1, FeatureVector featureVector2, WordsSimilarityMeasure metric) {
        // TODO: handle null values properly, check getClass() condition
        float distance = 0.0f;
        for(int i=0; i< featureVector1.features.size();i++){
            if(featureVector1.features.get(i) == null || featureVector2.features.get(i) == null){
                continue;
            }
            if (featureVector1.features.get(i).getClass() == featureVector2.features.get(i).getClass()) {
                if (featureVector1.features.get(i) instanceof String) {
                    String word1 = (String) featureVector1.features.get(i);
                    String word2 = (String) featureVector2.features.get(i);
                    float similarity = metric.calc(word1, word2, 1, 2);
                    distance += (float) Math.pow((1 - similarity), 2);
                }else if (featureVector1.features.get(i) instanceof Integer) {
                    distance += (float) Math.pow((int) featureVector1.features.get(i) - (int) featureVector2.features.get(i), 2);
                }
                else if (featureVector1.features.get(i) instanceof Float) {
                    distance += (float) Math.pow((float) featureVector1.features.get(i) - (float) featureVector2.features.get(i), 2);
                }
            }
        }
        return (float) Math.sqrt(distance);
    }

}
