package ksr.proj1.Classifier;

import ksr.proj1.Classifier.DistanceMetrics.Distances;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;
import ksr.proj1.FeatureExtraction.FeatureVector;

import java.util.*;

public class ClassificationResult {
    public List<FeatureVector> trainSet = new ArrayList<>();
    public List<FeatureVector> testSet = new ArrayList<>();
    public int k = 0;
    public int percentageOfTrainSet = 0;
    public int numOfArticles = 0;
    public List<String> classificationFeatures = new ArrayList<>();
    public String dstMetric = null;
    public String wordsSimMeasure = null;
    public long duration = 0;

    public HashMap<String, Integer> trainSetDistribution = new HashMap<>();
    public HashMap<String, Integer> testSetDistribution = new HashMap<>();

    public float accuracy = 0;
    public Map<String, Map<String, Integer>> confusionMatrix = new HashMap<>();
    ClassificationResult(List<FeatureVector> trainSet, List<FeatureVector> testSet, int k, int percentageOfTrainSet,
                         int numOfArticles, List<String> classificationFeatures, Distances dstMetric,
                         WordsSimilarityMeasure wordsSimMeasure, long duration) {
        this.trainSet = trainSet;
        this.testSet = testSet;
        this.k = k;
        this.percentageOfTrainSet = percentageOfTrainSet;
        this.numOfArticles = numOfArticles;
        this.classificationFeatures = classificationFeatures;
        this.dstMetric = dstMetric.getClass().getSimpleName();
        this.wordsSimMeasure = wordsSimMeasure.getClass().getSimpleName();
        this.duration = duration;
        System.out.println("Results: " + this.dstMetric + " " + this.wordsSimMeasure + " " + this.k + " " +
                this.percentageOfTrainSet + " " + this.numOfArticles + " " + this.classificationFeatures);
        System.out.println("Execution time: " + duration + " milliseconds");
        System.out.println("Execution time: " + duration / 1000 + " seconds");
        System.out.println("Execution time: " + duration / 60000 + " minutes");

        // Calculate class distribution
        for (FeatureVector vector : this.trainSet){
            this.trainSetDistribution.put(vector.realLabel, trainSetDistribution.getOrDefault(vector.realLabel, 0) + 1);
        }
        for (FeatureVector vector : this.testSet){
            this.testSetDistribution.put(vector.realLabel, testSetDistribution.getOrDefault(vector.realLabel, 0) + 1);
        }
        System.out.println("Train set distribution: " + this.trainSetDistribution);
        System.out.println("Test set distribution: " + this.testSetDistribution);

        this.accuracy = calculateAccuracy();
        System.out.println("Accuracy: " + this.accuracy);
        // Create confusion matrix
        this.confusionMatrix = createConfusionMatrix();
        printConfusionMatrix();
        //TODO Calculate accuracy
        //TODO Calculate precision
        //TODO Calculate recall
        //TODO Calculate F1 score
    }

    private float calculateAccuracy() {
        int correctPredictions = 0;
        for (FeatureVector vector : this.testSet) {
            if (vector.realLabel.equals(vector.predictedLabel)) {
                correctPredictions++;
            }
        }
        return (float) correctPredictions / this.testSet.size();
    }

    private Map<String, Map<String, Integer>> createConfusionMatrix() {
        Map<String, Map<String, Integer>> confusionMatrix = new HashMap<>();
        Set<String> labels = new HashSet<>();
        for (FeatureVector vector : this.testSet) {
            labels.add(vector.realLabel);
            confusionMatrix.computeIfAbsent(vector.realLabel, k -> new HashMap<>())
                    .merge(vector.predictedLabel, 1, Integer::sum);
        }
        for (Map.Entry<String, Map<String, Integer>> entry : confusionMatrix.entrySet()) {
            Map<String, Integer> predictedCounts = entry.getValue();
            for (String label : labels) {
                predictedCounts.putIfAbsent(label, 0);
            }
        }
        return confusionMatrix;
    }

    public void printConfusionMatrix() {
        System.out.println("Confusion matrix:");
        int label_space = 12;
        System.out.print(" ".repeat(label_space + 4));
        for (String label : confusionMatrix.keySet()) {
            System.out.print(label + " ".repeat(label_space - label.length()));
        }
        System.out.println();
        for (Map.Entry<String, Map<String, Integer>> entry : confusionMatrix.entrySet()) {
            String key = entry.getKey();
            int spaces = label_space - key.length();
            System.out.print(" ".repeat(spaces) + key + "    ");
            for (Integer count : entry.getValue().values()) {
                int digits = String.valueOf(count).length();
                System.out.print(count + " ".repeat(label_space - digits));
            }
            System.out.println();
        }
    }
}
