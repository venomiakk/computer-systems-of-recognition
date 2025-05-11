package ksr.knn.Utils;

import ksr.knn.FeatureExtraction.FeatureVector;

import java.util.*;
import java.util.stream.Collectors;

public class SetSplitter {

    public static List<FeatureVector> trainSetVectors;
    public static List<FeatureVector> testSetVectors;

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
        trainSetVectors = train;
        testSetVectors = test;
    }
}

