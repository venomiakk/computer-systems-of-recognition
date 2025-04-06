package ksr.proj1.Utils;

import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.FeatureExtraction.FeatureVector;

import java.util.*;
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

    public static void setSplitVectors(List<FeatureVector> featureVectors, int percentageOfTrainSet, int numberOfArticles) {
        Map<String, Long> originalCounts = featureVectors.stream()
                .collect(Collectors.groupingBy(fv -> fv.realLabel, Collectors.counting()));

        Map<String, Integer> targetCounts = new HashMap<>();
        long totalOriginal = featureVectors.size();
        int allocated = 0;
        for (Map.Entry<String, Long> entry : originalCounts.entrySet()) {
            String label = entry.getKey();
            long originalCount = entry.getValue();
            int targetCount = (int) Math.round((double) originalCount / totalOriginal * numberOfArticles);
            targetCounts.put(label, targetCount);
            allocated += targetCount;
        }
        while (allocated > numberOfArticles) {
            // Zmniejsz z największych
            String labelToReduce = targetCounts.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .get().getKey();
            targetCounts.put(labelToReduce, targetCounts.get(labelToReduce) - 1);
            allocated--;
        }
        while (allocated < numberOfArticles) {
            // Zwiększ tam, gdzie jest jeszcze zapas
            for (String label : targetCounts.keySet()) {
                if (allocated >= numberOfArticles) break;
                if (targetCounts.get(label) < originalCounts.get(label)) {
                    targetCounts.put(label, targetCounts.get(label) + 1);
                    allocated++;
                }
            }
        }

        Map<String, Integer> counters = new HashMap<>();
        List<FeatureVector> selected = new ArrayList<>();

        for (FeatureVector fv : featureVectors) {
            String label = fv.realLabel;
            int currentCount = counters.getOrDefault(label, 0);
            if (currentCount < targetCounts.getOrDefault(label, 0)) {
                selected.add(fv);
                counters.put(label, currentCount + 1);
            }
            if (selected.size() >= numberOfArticles) break;
        }

        Map<String, Integer> testCounts = new HashMap<>();
        Map<String, Integer> testTargetCounts = new HashMap<>();
        for (Map.Entry<String, Integer> entry : targetCounts.entrySet()) {
            int testCount = (int) Math.round((100 - percentageOfTrainSet) / 100.0 * entry.getValue());
            testTargetCounts.put(entry.getKey(), testCount);
        }

        List<FeatureVector> train = new ArrayList<>();
        List<FeatureVector> test = new ArrayList<>();

        for (FeatureVector fv : selected) {
            String label = fv.realLabel;
            int testSoFar = testCounts.getOrDefault(label, 0);
            if (testSoFar < testTargetCounts.getOrDefault(label, 0)) {
                test.add(fv);
                testCounts.put(label, testSoFar + 1);
            } else {
                train.add(fv);
            }
        }

        //System.out.println("Target count:" + targetCounts);
        //System.out.println("Test target count:" + testTargetCounts);
        trainSetVectors = train;
        testSetVectors = test;
        //System.out.println("Train set size: " + trainSetVectors.size());
        //System.out.println("Test set size: " + testSetVectors.size());
    }

    //    public static void setSplitVectors(List<FeatureVector> featureVectors, int percentageOfTrainSet) {
    //        Map<String, Long> placeCounts = featureVectors.stream()
    //                .collect(Collectors.groupingBy(featureVector -> featureVector.realLabel, Collectors.counting()));
    //
    //        Map<String, Long> testSetSizes = placeCounts.entrySet().stream()
    //                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() * percentageOfTrainSet / 100));
    //
    //        testSetVectors = featureVectors.stream()
    //                .filter(featureVector -> testSetSizes.getOrDefault(featureVector.realLabel, 0L) > 0)
    //                .peek(featureVector -> testSetSizes.put(featureVector.realLabel, testSetSizes.get(featureVector.realLabel) - 1))
    //                .collect(Collectors.toList());
    //
    //        trainSetVectors = featureVectors.stream()
    //                .filter(featureVector -> !testSetVectors.contains(featureVector))
    //                .collect(Collectors.toList());
    //}

    public static void setSplitVectors(List<FeatureVector> featureVectors, int percentageOfTrainSet){
        int numberOfArticles = featureVectors.size();
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

