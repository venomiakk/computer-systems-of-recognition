package ksr.proj1.Classifier.DistanceMetrics;

import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;

import java.util.ArrayList;

public class ChebyshevDistance implements  Distances{
    @Override
    public float calculateDistance(FeatureVector featureVector1, FeatureVector featureVector2, WordsSimilarityMeasure metric) {
        // TODO: handle null values properly, check getClass() condition
        ArrayList<Float> distance = new ArrayList<>();
        for(int i=0; i< featureVector1.features.size();i++){
            if(featureVector1.features.get(i) == null || featureVector2.features.get(i) == null){
                continue;
            }
            if (featureVector1.features.get(i).getClass() == featureVector2.features.get(i).getClass()) {
                if (featureVector1.features.get(i) instanceof String) {
                    String word1 = (String) featureVector1.features.get(i);
                    String word2 = (String) featureVector2.features.get(i);
                    float similarity = metric.calc(word1, word2, 1, 2);
                    distance.add(1 - similarity);
                }else if (featureVector1.features.get(i) instanceof Integer) {
                    distance.add((float) Math.abs((int) featureVector1.features.get(i) - (int) featureVector2.features.get(i)));
                }
                else if (featureVector1.features.get(i) instanceof Float) {
                    distance.add(Math.abs((float) featureVector1.features.get(i) - (float) featureVector2.features.get(i)));
                }
            }
        }
        return distance.stream().max(Float::compare).orElse(0.0f);
    }
}
