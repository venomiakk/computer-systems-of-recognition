package ksr.proj1.Classifier;

import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.Distances.Distances;
import ksr.proj1.FeatureExtraction.FeatureExtractor;
import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Metrics.WordsSimilirityMetrics;
import ksr.proj1.Utils.SetSplit;

import java.io.IOException;
import java.util.*;

public class KNN2 {
    private static List<FeatureVector> trainingSet = new ArrayList<>();
    private static List<FeatureVector> testingSet = new ArrayList<>();
    private static List<String> surnameDict = new ArrayList<>();
    private static List<String> countryDict = new ArrayList<>();
    private static List<String> keywordDict = new ArrayList<>();
    private static List<String> stopWords = new ArrayList<>();
    private static List<ReutersElement> articles = new ArrayList<>();

    public KNN2(List<ReutersElement> articles, List<String> surnameDict, List<String> countryDict, List<String> keywordDict, List<String> stopWords) {
        KNN2.surnameDict = surnameDict;
        KNN2.countryDict = countryDict;
        KNN2.keywordDict = keywordDict;
        KNN2.stopWords = stopWords;
        KNN2.articles = articles;

    }

    public void knnClassify(int k, int percentageOfTrainSet, List<String> features, Distances metricMethod,
                            WordsSimilirityMetrics wordMetric) throws IOException {
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
        List<FeatureVector> articleVectors = new ArrayList<>();
        for (ReutersElement article : KNN2.articles) {
            articleVectors.add(featureExtractor.extractFeatures(article));
        }

        // REMOVE UNNECESSARY FEATURES
        HashMap<String, Integer> featureMap = new LinkedHashMap<>();
        featureMap.put("c1", 0);
        featureMap.put("c2", 1);
        featureMap.put("c3", 2);
        featureMap.put("c4", 3);
        featureMap.put("c5", 4);
        featureMap.put("c6", 5);
        featureMap.put("c7", 6);
        featureMap.put("c8", 7);
        featureMap.put("c9", 8);
        featureMap.put("c10", 9);
        featureMap.put("c11", 10);
        featureMap.put("c12", 11);
        List<Integer> featureIndexes = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : featureMap.entrySet()) {
            if (!features.contains(entry.getKey())) {
                featureIndexes.add(entry.getValue());
            }
        }
        for (int i = featureIndexes.size() - 1; i >= 0; i--) {
            int indexToRemove = featureIndexes.get(i);
            for (FeatureVector vector : articleVectors) {
                vector.features.remove(indexToRemove);
            }

        }

        System.out.println(articleVectors.getFirst());

        normalizeVectors(articleVectors);
        SetSplit.setSplitVectors(articleVectors, percentageOfTrainSet);
        trainingSet = SetSplit.trainSetVectors;
        testingSet = SetSplit.testSetVectors;

        List<FeatureVector> nn = findNeighbours(metricMethod, wordMetric, articleVectors.getFirst(), k, trainingSet);
        System.out.println(nn);
        //for (FeatureVector vector : testingSet) {
        //    List<FeatureVector> nearestNeighbours = findNeighbours(metricMethod, wordMetric, vector, k, trainingSet);
        //
        //}


    }

    private List<FeatureVector> findNeighbours(Distances metricMethod, WordsSimilirityMetrics wordMetric,
                                               FeatureVector vector, int k, List<FeatureVector> testingSet) {
        List<FeatureVector> nearestNeighbors = new ArrayList<>();
        for (FeatureVector featureVector : trainingSet) {
            float distance = metricMethod.countDistance(vector, featureVector, wordMetric);
            if (nearestNeighbors.size() < k) {
                nearestNeighbors.add(featureVector);
            } else {
                float maxDistance = 0;
                int maxIndex = -1;
                for (int i = 0; i < nearestNeighbors.size(); i++) {
                    float currentDistance = metricMethod.countDistance(vector, nearestNeighbors.get(i), wordMetric);
                    if (currentDistance > maxDistance) {
                        maxDistance = currentDistance;
                        maxIndex = i;
                    }
                }
                if (distance < maxDistance) {
                    nearestNeighbors.set(maxIndex, featureVector);
                }
            }
        }
        return nearestNeighbors;
    }

    private List<FeatureVector> normalizeVectors(List<FeatureVector> vectors) {
        //TODO: Should this consider null values?
        List<FeatureVector> normalizedVectors = new ArrayList<>();
        int numberOfFeatures = vectors.getFirst().features.size();

        for (int i = 0; i < numberOfFeatures; i++){
            List<Double> numericValues = new ArrayList<>();
            for (FeatureVector vector : vectors) {
                Object fv = vector.features.get(i);
                if (fv instanceof Number){
                    double value = ((Number) fv).doubleValue();
                    numericValues.add(value);
                    if (Double.isNaN(value)) {
                        System.out.println(vector);
                    }
                }
            }


            if (numericValues.isEmpty()) {
                continue;
            }

            double min = Collections.min(numericValues);
            double max = Collections.max(numericValues);

            if (max == min) {
                continue;
            }

            for (FeatureVector vector : vectors) {
                Object fv = vector.features.get(i);
                if (fv instanceof Number){
                    double normalizedValue = (((Number) fv).doubleValue() - min) / (max - min);
                    vector.features.set(i, normalizedValue);
                }
            }
        }

        return normalizedVectors;
    }

}
