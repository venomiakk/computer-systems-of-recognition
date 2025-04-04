package ksr.proj1.Classifier;

import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.DataExtraction.ReutersInfoData;
import ksr.proj1.DataExtraction.ReutersXmlData;
import ksr.proj1.DataExtraction.StopWords;
import ksr.proj1.FeatureExtraction.FeatureExtractor;
import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Metrics.Distances;
import ksr.proj1.Metrics.WordsSimilirityMetrics;
import ksr.proj1.Utils.KeyWordDao;
import ksr.proj1.Utils.SetSplit;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KNN {
    private List<FeatureVector> trainingSet = new ArrayList<>();
    private List<FeatureVector> testingSet = new ArrayList<>();
    private static List<String> surnameDict = new ArrayList<>();
    private static List<String> countryDict = new ArrayList<>();
    private static List<String> keywordDict = new ArrayList<>();
    private static List<String> stopWords = new ArrayList<>();
    public KNN(Distances metricMethod, WordsSimilirityMetrics wordMetric) throws IOException {
        KeyWordDao dao = new KeyWordDao();
        ReutersInfoData.readData();
        surnameDict = ReutersInfoData.allPeopleCodes;
        countryDict = ReutersInfoData.allPlacesCodes;
        keywordDict = dao.loadKeywordsFromFile("top1000Keywords.txt");
        stopWords = StopWords.getStopWords();
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
        createSeperateSets(featureExtractor);
        for(FeatureVector vector : testingSet) {
            classify(metricMethod, wordMetric, vector, 5);
        }
        showIncorrectLables();


    }

    public void createSeperateSets(FeatureExtractor featureExtractor) throws IOException {
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        SetSplit setSplit = new SetSplit(ReutersXmlData.classificationArticles,  60);
        List<ReutersElement> trainSet = setSplit.trainSet;
        List<ReutersElement> testSet = setSplit.testSet;
        for(ReutersElement element : trainSet) {
            FeatureVector featureVector = featureExtractor.extractFeatures(element);
            trainingSet.add(featureVector);
        }

        for(ReutersElement element : testSet) {
            FeatureVector featureVector = featureExtractor.extractFeatures(element);
            this.testingSet.add(featureVector);
        }
    }

    public List<FeatureVector> findNeighbours(Distances metricMethod, WordsSimilirityMetrics wordMetric, FeatureVector vector, int k) {
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

    public void classify(Distances metricMethod, WordsSimilirityMetrics wordMetric, FeatureVector vector, int k) {
        final Long[] max = {Long.MIN_VALUE};
        int number = 0;
        List<FeatureVector> nearestNeighbours = findNeighbours(metricMethod, wordMetric, vector, k);
        Map<String, Long> grouped = nearestNeighbours.stream()
                .collect(Collectors.groupingBy(fv -> fv.realLabel, Collectors.counting()));

        grouped.values().forEach(count -> {
            if (count > max[0]) {
                max[0] = count;
            }
        });

        for(Long count : grouped.values()) {
            if (count == max[0]) {
                number++;
            }
        }

        if (number == 1) {
            for(Map.Entry<String, Long> entry : grouped.entrySet()) {
                if (entry.getValue() == max[0]) {
                    vector.predictedLabel = entry.getKey();
                }
            }
        } else {
            List<FeatureVector> temp = new ArrayList<>();
            List<String> maxLabels = grouped.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(max[0]))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            for(FeatureVector featureVector : nearestNeighbours) {
                if (maxLabels.contains(featureVector.realLabel)) {
                    temp.add(featureVector);
                }
            }

            float min = Float.MAX_VALUE;
            int minIndex = -1;

            for (int i = 0; i < temp.size(); i++) {
                float currentDistance = metricMethod.countDistance(vector, temp.get(i), wordMetric);
                if (currentDistance < min) {
                    min = currentDistance;
                    minIndex = i;
                }
            }

            if (minIndex != -1) {
                System.out.println("Min index: " + minIndex);
                vector.predictedLabel = temp.get(minIndex).realLabel;
            }
        }
    }
    public void showIncorrectLables(){
        for(FeatureVector vector : testingSet) {
            if (!vector.predictedLabel.equals(vector.realLabel)) {
                System.out.println("Predicted label: " + vector.predictedLabel + " Real label: " + vector.realLabel);
            }
        }
    }

}
