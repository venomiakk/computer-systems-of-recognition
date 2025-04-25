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
    public int correctPredictions = 0;
    public int incorrectPredictions = 0;

    public HashMap<String, Integer> trainSetDistribution = new HashMap<>();
    public HashMap<String, Integer> testSetDistribution = new HashMap<>();

    public float accuracy = 0;
    public Map<String, Map<String, Integer>> confusionMatrix = new HashMap<>();
    public Map<String, Map<String, Float>> scoresMatrix = new HashMap<>();

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

        // Calculate class distribution
        for (FeatureVector vector : this.trainSet){
            this.trainSetDistribution.put(vector.realLabel, trainSetDistribution.getOrDefault(vector.realLabel, 0) + 1);
        }
        for (FeatureVector vector : this.testSet){
            this.testSetDistribution.put(vector.realLabel, testSetDistribution.getOrDefault(vector.realLabel, 0) + 1);
        }

        this.accuracy = calculateAccuracy();
        this.confusionMatrix = createConfusionMatrix();
        this.scoresMatrix = calculateScores();
    }

    private Map<String, Map<String, Float>> calculateScores(){
        Map<String, Map<String, Float>> scoresMap = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : confusionMatrix.entrySet()) {
            Map<String, Float> scores = new HashMap<>();
            int allClassifiedForPPV = 0;
            for (Map.Entry<String, Map<String, Integer>> entry2 : confusionMatrix.entrySet()){
                for (Map.Entry<String, Integer> entry3 : entry2.getValue().entrySet()){
                    if (entry3.getKey().equals(entry.getKey())){
                        allClassifiedForPPV += entry3.getValue();
                    }
                }
            }
            float precision = (allClassifiedForPPV > 0) ?
                    (float) entry.getValue().get(entry.getKey()) / (float) allClassifiedForPPV : 0f;
            // correctlyClassified / allOfClass
            float recall = (float) entry.getValue().get(entry.getKey()) / (float) testSetDistribution.get(entry.getKey());
            float f1numerator = 2f * precision * recall;
            float f1denominator = precision + recall;
            float f1 = (f1denominator > 0) ? f1numerator / f1denominator : 0f;
            scores.put("precision", precision);
            scores.put("recall", recall);
            scores.put("f1", f1);
            scoresMap.put(entry.getKey(), scores);
        }

        return scoresMap;
    }

    public void printScoresMatrix(){
        System.out.println("Scores matrix:");
        int label_space = 12;
        System.out.print(" ".repeat(label_space + 4));
        Map<String, Float> firstRow = scoresMatrix.values().iterator().next();
        for (String label : firstRow.keySet()) {
            System.out.print(label + " ".repeat(label_space - label.length()));
        }
        System.out.println();
        for (Map.Entry<String, Map<String, Float>> entry : scoresMatrix.entrySet()) {
            String key = entry.getKey();
            int spaces = label_space - key.length();
            System.out.print(" ".repeat(spaces) + key + "    ");
            for (Float score : entry.getValue().values()) {
                String formatted = String.format("%.3f", score);
                int digits = formatted.length();
                System.out.print(formatted + " ".repeat(label_space - digits));
            }
            System.out.println();
        }
    }

    private float calculateAccuracy() {
        int correctPredictions = 0;
        for (FeatureVector vector : this.testSet) {
            if (vector.realLabel.equals(vector.predictedLabel)) {
                correctPredictions++;
            }
        }
        this.correctPredictions = correctPredictions;
        this.incorrectPredictions = this.testSet.size() - correctPredictions;
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
        System.out.print("    | PREDICTED");
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
        System.out.println("-".repeat(label_space));
        System.out.println(" ".repeat(label_space - 4) + "REAL");
    }

    public void printClassificationResults() {
        System.out.println("Execution time: " + this.duration + " milliseconds");
        System.out.println("Execution time: " + this.duration / 1000 + " seconds");
        System.out.println("Execution time: " + this.duration / 60000 + " minutes");
        System.out.println("Classification result:");
        System.out.println("Number of articles: " + this.numOfArticles);
        System.out.println("Percentage of train set: " + this.percentageOfTrainSet);
        System.out.println("Train set size: " + this.trainSet.size());
        System.out.println("Train set distribution: " + this.trainSetDistribution);
        System.out.println("Test set size: " + this.testSet.size());
        System.out.println("Test set distribution: " + this.testSetDistribution);
        System.out.println("K: " + this.k);
        System.out.println("Distance metric: " + this.dstMetric);
        System.out.println("Words similarity measure: " + this.wordsSimMeasure);
        System.out.println("Classification features: " + this.classificationFeatures);
        printConfusionMatrix();
        System.out.println("Correct predictions: " + this.correctPredictions);
        System.out.println("Incorrect predictions: " + this.incorrectPredictions);
        System.out.print("Accuracy: ");
        System.out.printf("%.3f\n", this.accuracy);
        printScoresMatrix();
    }
}
