package ksr.proj1.Classifier;

import ksr.proj1.Classifier.DistanceMetrics.Distances;
import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;
import ksr.proj1.FeatureExtraction.FeatureExtractor;
import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Utils.SetSplitter;

import java.io.IOException;
import java.util.*;

public class KnnClassifier {
    private List<String> surnameDict = new ArrayList<>();
    private List<String> countryDict = new ArrayList<>();
    private List<String> keywordDict = new ArrayList<>();
    private List<String> stopWords = new ArrayList<>();
    private List<ReutersElement> articles = new ArrayList<>();
    private final HashMap<String, Integer> featureMap = new LinkedHashMap<>();
    private final List<FeatureVector> classArticleVectors = new ArrayList<>();

    public KnnClassifier(List<ReutersElement> articles, List<String> surnameDict, List<String> countryDict,
                         List<String> keywordDict, List<String> stopWords) throws IOException {
        this.surnameDict = surnameDict;
        this.countryDict = countryDict;
        this.keywordDict = keywordDict;
        this.stopWords = stopWords;
        this.articles = articles;

        this.featureMap.put("c1", 0);
        this.featureMap.put("c2", 1);
        this.featureMap.put("c3", 2);
        this.featureMap.put("c4", 3);
        this.featureMap.put("c5", 4);
        this.featureMap.put("c6", 5);
        this.featureMap.put("c7", 6);
        this.featureMap.put("c8", 7);
        this.featureMap.put("c9", 8);
        this.featureMap.put("c10", 9);
        this.featureMap.put("c11", 10);
        this.featureMap.put("c12", 11);

        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
        for (ReutersElement article : this.articles) {
            this.classArticleVectors.add(featureExtractor.extractFeatures(article));
        }
    }

    public ClassificationResult classify(int k, int percentageOfTrainSet, int numOfArticles, List<String> classificationFeatures,
                         Distances dstMetric, WordsSimilarityMeasure wordsSimMeasure) {
        long startTime = System.currentTimeMillis();

        List<FeatureVector> articleVectors = new ArrayList<>();
        for (FeatureVector vector : this.classArticleVectors) {
            articleVectors.add(vector.clone());
        }

        // Remove unnecessary features
        removeUnnecessaryFeatures(classificationFeatures, articleVectors);

        // Split data into training and testing sets
        if (numOfArticles <= 0) {
            numOfArticles = articleVectors.size();
        }
        if (numOfArticles > articleVectors.size()) {
            numOfArticles = articleVectors.size();
        }
        if (percentageOfTrainSet <= 0) {
            percentageOfTrainSet = 1;
        }
        if (percentageOfTrainSet >= 100) {
            percentageOfTrainSet = 99;
        }
        SetSplitter.setSplitVectors(articleVectors, percentageOfTrainSet, numOfArticles);
        List<FeatureVector> trainSet = SetSplitter.trainSetVectors;
        List<FeatureVector> testSet = SetSplitter.testSetVectors;

        // Normalize vectors (based on the training set)
        normalizeVectors(trainSet, testSet);
        //System.out.println(trainSet.getFirst());
        ////System.out.println(testSet.getFirst());
        //for (Object f : trainSet.getFirst().features) {
        //    if (f == null) {
        //        System.out.println("null");
        //    }
        //    else{
        //        System.out.println(f + "  " + f.getClass());
        //    }
        //}
        // Find k nearest neighbors for each test vector
        if (k <= 0) {
            k = 1;
        }
        if (k > trainSet.size()) {
            k = trainSet.size();
        }
        int progress_index = 1;
        for (FeatureVector testVector : testSet) {
            System.out.print("\r" + progress_index + "/" + testSet.size());
            progress_index++;
            List<FeatureVector> neighbors = findNearestNeighbors(testVector, trainSet, k, dstMetric, wordsSimMeasure);
            // Classify the test vector based on the majority class of its neighbors
            assignLabel(testVector, neighbors);
            //System.out.println(testVector);

        }
        System.out.println("\n");
        //for (FeatureVector testVector : testSet) {
        //    System.out.println(testVector);
        //}
        // Calculate results (accuracy, precision, recall, F1 score)
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        ClassificationResult cr = new ClassificationResult(trainSet, testSet, k, percentageOfTrainSet,
                numOfArticles, classificationFeatures, dstMetric, wordsSimMeasure, duration);

        return cr;
    }

    private void assignLabel(FeatureVector testVector, List<FeatureVector> neighbors) {
        Map<String, Integer> classCount = new LinkedHashMap<>();
        for (FeatureVector neighbor : neighbors) {
            String label = neighbor.realLabel;
            classCount.put(label, classCount.getOrDefault(label, 0) + 1);
        }
        testVector.predictedLabel = Collections.max(classCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private List<FeatureVector> findNearestNeighbors(FeatureVector testVector, List<FeatureVector> trainingSet, int k,
                                                     Distances dstMetric, WordsSimilarityMeasure wordsSimMeasure) {

        PriorityQueue<FeatureVector> heap = new PriorityQueue<>(Comparator.comparingDouble(trainFeatureVector ->
                -dstMetric.calculateDistance(trainFeatureVector, testVector, wordsSimMeasure)));

        for (FeatureVector trainFeatureVector : trainingSet) {
            //System.out.println(trainFeatureVector);
            heap.offer(trainFeatureVector);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        List<FeatureVector> neighbors = new ArrayList<>(heap);
        neighbors.sort(Comparator.comparingDouble(trainFeatureVector ->
                dstMetric.calculateDistance(trainFeatureVector, testVector, wordsSimMeasure)));
        return neighbors;
    }

    private void normalizeVectors(List<FeatureVector> trainingSet, List<FeatureVector> testingSet) {
        int numberOfFeatures = trainingSet.getFirst().features.size();

        for (int feature = 0; feature < numberOfFeatures; feature++) {
            List<Double> numericValues = new ArrayList<>();
            for (FeatureVector vector : trainingSet) {
                Object fv = vector.features.get(feature);
                if (fv instanceof Number) {
                    double value = ((Number) fv).doubleValue();
                    numericValues.add(value);
                }
            }

            if (numericValues.isEmpty()) {
                continue;
            }
            double min = Collections.min(numericValues);
            double max = Collections.max(numericValues);

            for (FeatureVector vector : trainingSet) {
                Object fv = vector.features.get(feature);
                if (fv instanceof Number) {
                    if (min == max) {
                        vector.features.set(feature, 0.0);
                    } else {
                        double normalizedValue = (((Number) fv).doubleValue() - min) / (max - min);
                        vector.features.set(feature, normalizedValue);
                    }

                }
            }
            for (FeatureVector vector : testingSet) {
                Object fv = vector.features.get(feature);
                if (fv instanceof Number) {
                    if (min == max) {
                        vector.features.set(feature, 0.0);
                    } else {
                        double normalizedValue = (((Number) fv).doubleValue() - min) / (max - min);
                        vector.features.set(feature, normalizedValue);
                    }
                }
            }
        }
    }

    private void removeUnnecessaryFeatures(List<String> classificationFeatures, List<FeatureVector> articleVectors) {
        List<Integer> featureIndexes = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : this.featureMap.entrySet()) {
            if (!classificationFeatures.contains(entry.getKey())) {
                featureIndexes.add(entry.getValue());
            }
        }
        for (int i = featureIndexes.size() - 1; i >= 0; i--) {
            int index = featureIndexes.get(i);
            for (FeatureVector vector : articleVectors) {
                vector.features.remove(index);
            }
        }
    }
}
