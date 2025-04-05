package ksr.proj1.Utils;

import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.FeatureExtraction.FeatureVector;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SetSplit {
    public List<ReutersElement> trainSet;
    public List<ReutersElement> testSet;

    public static List<FeatureVector> trainSetVectors;
    public static List<FeatureVector> testSetVectors;

    public SetSplit(List<ReutersElement> articles, int percentageOfTrainSet) {
        Map<String, Long> placeCounts = articles.stream()
                .flatMap(article -> article.places.stream())
                .collect(Collectors.groupingBy(place -> place, Collectors.counting()));


        Map<String, Long> testSetSizes = placeCounts.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() * percentageOfTrainSet / 100));


        testSet = articles.stream()
                .filter(article -> article.places.stream().anyMatch(place -> testSetSizes.getOrDefault(place, 0L) > 0))
                .peek(article -> article.places.forEach(place -> testSetSizes.put(place, testSetSizes.get(place) - 1)))
                .collect(Collectors.toList());

        trainSet = articles.stream()
                .filter(article -> !testSet.contains(article))
                .collect(Collectors.toList());

    }

    public static void setSplitVectors(List<FeatureVector> featureVectors, int percentageOfTrainSet) {
        Map<String, Long> placeCounts = featureVectors.stream()
                .collect(Collectors.groupingBy(featureVector -> featureVector.realLabel, Collectors.counting()));

        Map<String, Long> testSetSizes = placeCounts.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() * percentageOfTrainSet / 100));

        testSetVectors = featureVectors.stream()
                .filter(featureVector -> testSetSizes.getOrDefault(featureVector.realLabel, 0L) > 0)
                .peek(featureVector -> testSetSizes.put(featureVector.realLabel, testSetSizes.get(featureVector.realLabel) - 1))
                .collect(Collectors.toList());

        trainSetVectors = featureVectors.stream()
                .filter(featureVector -> !testSetVectors.contains(featureVector))
                .collect(Collectors.toList());

    }
}

