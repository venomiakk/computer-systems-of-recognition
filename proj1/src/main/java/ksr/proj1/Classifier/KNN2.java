package ksr.proj1.Classifier;

import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.Classifier.Distances.Distances;
import ksr.proj1.FeatureExtraction.FeatureExtractor;
import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Metrics.WordsSimilirityMetrics;
import ksr.proj1.Utils.SetSplit;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class KNN2 {
    //private List<FeatureVector> trainingSet = new ArrayList<>();
    //private List<FeatureVector> testingSet = new ArrayList<>();
    private List<String> surnameDict = new ArrayList<>();
    private List<String> countryDict = new ArrayList<>();
    private List<String> keywordDict = new ArrayList<>();
    private List<String> stopWords = new ArrayList<>();
    private List<ReutersElement> articles = new ArrayList<>();

    public KNN2(List<ReutersElement> articles, List<String> surnameDict, List<String> countryDict, List<String> keywordDict, List<String> stopWords) {
        this.surnameDict = surnameDict;
        this.countryDict = countryDict;
        this.keywordDict = keywordDict;
        this.stopWords = stopWords;
        this.articles = articles;

    }

    public void knnClassify(int k, int percentageOfTrainSet, List<String> features, Distances metricMethod,
                            WordsSimilirityMetrics wordMetric) throws IOException {
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
        List<FeatureVector> articleVectors = new ArrayList<>();
        for (ReutersElement article : this.articles) {
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

        //System.out.println(articleVectors.getFirst());

        normalizeVectors(articleVectors);
        SetSplit.setSplitVectors(articleVectors, percentageOfTrainSet, 1000);
        List<FeatureVector> trainingSet = SetSplit.trainSetVectors;
        List<FeatureVector> testingSet = SetSplit.testSetVectors;

        //List<FeatureVector> nn = findNeighbours(metricMethod, wordMetric, testingSet.get(63), k, trainingSet);
        //String s = predictClass(nn);
        //System.out.println(s);

        //<RealLabel, <PredictedLabel, Count>>
        Map<String, Map<String, Integer>> confusionMatrix = new HashMap<>();
        int correctPreds = 0;
        int wrongPreds = 0;
        for (FeatureVector vector : testingSet) {
            System.out.println("Processing " + vector.originalID);
            List<FeatureVector> nearestNeighbours = findNeighbours(metricMethod, wordMetric, vector, k, trainingSet);
            vector.predictedLabel = predictClass(nearestNeighbours);
            if (vector.predictedLabel.equals(vector.realLabel)){
                correctPreds++;
            } else {
                wrongPreds++;
            }
            confusionMatrix.putIfAbsent(vector.realLabel, new HashMap<>());
            Map<String, Integer> predictedLabels = confusionMatrix.get(vector.realLabel);
            predictedLabels.put(vector.predictedLabel, predictedLabels.getOrDefault(vector.predictedLabel, 0) + 1);

        }
        System.out.println("correctPreds: " + correctPreds);
        System.out.println("wrongPreds: " + wrongPreds);
        //calculate accuracy
        int correctPredictions = 0;
        int totalPredictions = 0;
        System.out.println("confusionMatrix");
        for (Map.Entry<String, Map<String, Integer>> real : confusionMatrix.entrySet()) {
            String realLabel = real.getKey();
            Map<String, Integer> predictedLabels = real.getValue();
            for (Map.Entry<String, Integer> predicted : predictedLabels.entrySet()) {
                String predictedLabel = predicted.getKey();
                int count = predicted.getValue();
                totalPredictions += count;
                if (realLabel.equals(predictedLabel)) {
                    correctPredictions += count;
                }
            }
        }

        double accuracy = (double) correctPredictions / totalPredictions;
        System.out.println("Correct predictions: " + correctPredictions);
        System.out.println("Wrong predictions: " + (totalPredictions - correctPredictions));
        System.out.println("Total predictions: " + totalPredictions);
        System.out.println("Accuracy: " + accuracy);

        //print confusion matrix
        System.out.println("Confusion Matrix:");
        for (Map.Entry<String, Map<String, Integer>> real : confusionMatrix.entrySet()) {
            String realLabel = real.getKey();
            Map<String, Integer> predictedLabels = real.getValue();
            System.out.print(realLabel + ": ");
            for (Map.Entry<String, Integer> predicted : predictedLabels.entrySet()) {
                String predictedLabel = predicted.getKey();
                int count = predicted.getValue();
                System.out.print(predictedLabel + ": " + count + ", ");
            }
            System.out.println();
        }


    }

    private List<FeatureVector> findNeighbours(Distances metricMethod, WordsSimilirityMetrics wordMetric,
                                               FeatureVector vector, int k, List<FeatureVector> testingSet) {
        return testingSet.stream().
                sorted(Comparator.comparingDouble(fv -> metricMethod.countDistance(vector, fv, wordMetric)))
                .limit(k)
                .collect(Collectors.toList());
    }

    private void normalizeVectors(List<FeatureVector> vectors) {
        //TODO: Should this consider null values?
        int numberOfFeatures = vectors.getFirst().features.size();

        for (int i = 0; i < numberOfFeatures; i++) {
            List<Double> numericValues = new ArrayList<>();
            for (FeatureVector vector : vectors) {
                Object fv = vector.features.get(i);
                if (fv instanceof Number) {
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
                if (fv instanceof Number) {
                    double normalizedValue = (((Number) fv).doubleValue() - min) / (max - min);
                    vector.features.set(i, normalizedValue);
                }
            }
        }

    }

    private String predictClass(List<FeatureVector> neighbours) {
        Map<String, Integer> labelCounts = new LinkedHashMap<>();

        for (FeatureVector neighbour : neighbours) {
            labelCounts.put(neighbour.realLabel,
                    labelCounts.getOrDefault(neighbour.realLabel, 0) + 1);
        }

        int maxCount = Collections.max(labelCounts.values());
        List<String> candidates = labelCounts.entrySet().stream()
                .filter(e -> e.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .toList();

        return candidates.getFirst();
    }
}
