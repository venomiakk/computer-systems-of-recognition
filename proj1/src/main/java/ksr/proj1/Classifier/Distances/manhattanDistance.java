package ksr.proj1.Classifier.Distances;

import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Metrics.WordsSimilirityMetrics;

public class manhattanDistance implements Distances {
    @Override
    public float countDistance(FeatureVector featureVector1, FeatureVector featureVector2, WordsSimilirityMetrics metric) {
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
                    distance += (1 - similarity);
                }else if (featureVector1.features.get(i) instanceof Integer) {
                    distance += Math.abs((int) featureVector1.features.get(i) - (int) featureVector2.features.get(i));
                }
                else if (featureVector1.features.get(i) instanceof Float) {
                    distance += Math.abs((float) featureVector1.features.get(i) - (float) featureVector2.features.get(i));
                }
            }
        }
        return distance;
    }
}
