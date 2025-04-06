package ksr.proj1.Classifier;

import ksr.proj1.DataExtraction.*;
import ksr.proj1.FeatureExtraction.FeatureExtractor;
import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Distances.Distances;
import ksr.proj1.Metrics.WordsSimilirityMetrics;
import ksr.proj1.Utils.KeyWordDao;
import ksr.proj1.Utils.SetSplit;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class KNN {
    private List<FeatureVector> trainingSet = new ArrayList<>();
    private List<FeatureVector> testingSet = new ArrayList<>();
    private static List<String> surnameDict = new ArrayList<>();
    private static List<String> countryDict = new ArrayList<>();
    private static List<String> keywordDict = new ArrayList<>();
    private static List<String> stopWords = new ArrayList<>();

    private static List<ReutersElement> articles = new ArrayList<>();

    public KNN(Distances metricMethod, WordsSimilirityMetrics wordMetric, int kn) throws IOException {
        KeyWordDao dao = new KeyWordDao();
        ReutersInfoData.readData();
        surnameDict = ReutersInfoData.allPeopleCodes;
        countryDict = ReutersInfoData.allPlacesCodes;
        //keywordDict = dao.loadKeywordsFromFile("top1000Keywords.txt");
        keywordDict = KeywordsExtraction.extractKeywords();
        stopWords = StopWords.getStopWords();
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
        createSeperateSets(featureExtractor);
        for (FeatureVector vector : testingSet) {
            classify(metricMethod, wordMetric, vector, kn);
        }
        //for (FeatureVector vector : trainingSet) {
        //    System.out.println(vector);
        //}
        showIncorrectLables();


    }

    public void createSeperateSets(FeatureExtractor featureExtractor) throws IOException {
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();

        List<FeatureVector> articleVectors = new ArrayList<>();
        for (ReutersElement article : ReutersXmlData.classificationArticles) {
            articleVectors.add(featureExtractor.extractFeatures(article));
        }
        SetSplit.setSplitVectors(articleVectors, 60, 1000);
        trainingSet = SetSplit.trainSetVectors;
        testingSet = SetSplit.testSetVectors;


        //SetSplit setSplit = new SetSplit(ReutersXmlData.classificationArticles,  60);
        //List<ReutersElement> trainSet = setSplit.trainSet;
        //List<ReutersElement> testSet = setSplit.testSet;
        //for(ReutersElement element : trainSet) {
        //    FeatureVector featureVector = featureExtractor.extractFeatures(element);
        //    this.trainingSet.add(featureVector);
        //}
        //
        //for(ReutersElement element : testSet) {
        //    FeatureVector featureVector = featureExtractor.extractFeatures(element);
        //    this.testingSet.add(featureVector);
        //}

        // REMOVE UNNECESSARY FEATURES
        List<String> allfeatures = List.of("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12");
        List<String> stringfeatures = List.of("c3", "c4", "c5", "c7", "c8");
        List<String> numericfeatures = List.of("c1", "c2", "c6", "c9", "c10", "c11", "c12");
        List<String> features = allfeatures;
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
            for (FeatureVector vector : testingSet) {
                vector.features.remove(indexToRemove);
            }
            for (FeatureVector vector : trainingSet) {
                vector.features.remove(indexToRemove);
            }
        }
        //System.out.println(trainingSet.getFirst());
        //System.out.println(testingSet.getFirst());
        normalizeVectors();
    }

    public List<FeatureVector> findNeighbours(Distances metricMethod, WordsSimilirityMetrics wordMetric, FeatureVector vector, int k) {
        //List<FeatureVector> nearestNeighbors = new ArrayList<>();
        //for (FeatureVector featureVector : trainingSet) {
        //    float distance = metricMethod.countDistance(vector, featureVector, wordMetric);
        //    if (nearestNeighbors.size() < k) {
        //        nearestNeighbors.add(featureVector);
        //    } else {
        //        float maxDistance = 0;
        //        int maxIndex = -1;
        //        for (int i = 0; i < nearestNeighbors.size(); i++) {
        //            float currentDistance = metricMethod.countDistance(vector, nearestNeighbors.get(i), wordMetric);
        //            if (currentDistance > maxDistance) {
        //                maxDistance = currentDistance;
        //                maxIndex = i;
        //            }
        //        }
        //        if (distance < maxDistance) {
        //            nearestNeighbors.set(maxIndex, featureVector);
        //        }
        //    }
        //}
        //return nearestNeighbors;
        return testingSet.stream().
                sorted(Comparator.comparingDouble(fv -> metricMethod.countDistance(vector, fv, wordMetric)))
                .limit(k)
                .collect(Collectors.toList());
    }

    public void classify(Distances metricMethod, WordsSimilirityMetrics wordMetric, FeatureVector vector, int k) {
        final Long[] max = {Long.MIN_VALUE};
        int number = 0;
        List<FeatureVector> nearestNeighbours = findNeighbours(metricMethod, wordMetric, vector, k);
        //System.out.println("Nearest neighbours: " + nearestNeighbours);
        Map<String, Long> grouped = nearestNeighbours.stream()
                .collect(Collectors.groupingBy(fv -> fv.realLabel, Collectors.counting()));

        grouped.values().forEach(count -> {
            if (count > max[0]) {
                max[0] = count;
            }
        });

        for (Long count : grouped.values()) {
            if (count == max[0]) {
                number++;
            }
        }
        //do  komita
        if (number == 1) {
            for (Map.Entry<String, Long> entry : grouped.entrySet()) {
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

            for (FeatureVector featureVector : nearestNeighbours) {
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
                //System.out.println("Min index: " + minIndex);
                vector.predictedLabel = temp.get(minIndex).realLabel;
            }
        }
    }

    public void showIncorrectLables() {
        //for(FeatureVector vector : testingSet) {
        //    if (!vector.predictedLabel.equals(vector.realLabel)) {
        //        System.out.println("Predicted label: " + vector.predictedLabel + " Real label: " + vector.realLabel);
        //    }
        //}
        Map<String, Map<String, Integer>> confusionMatrix = new HashMap<>();
        int correctPreds = 0;
        int wrongPreds = 0;
        for (FeatureVector vector : testingSet) {
            if (vector.predictedLabel.equals(vector.realLabel)) {
                correctPreds++;
            } else {
                wrongPreds++;
            }
            confusionMatrix.computeIfAbsent(vector.realLabel, k -> new HashMap<>())
                    .merge(vector.predictedLabel, 1, Integer::sum);
        }

        float acc = (float) correctPreds / (correctPreds + wrongPreds);
        System.out.println("Accuracy: " + acc);
        System.out.println("correctPreds: " + correctPreds);
        System.out.println("wrongPreds: " + wrongPreds);
        //calculate accuracy


        //print confusion matrix
        //wrong
        //System.out.println("Confusion Matrix:");
        //for (Map.Entry<String, Map<String, Integer>> real : confusionMatrix.entrySet()) {
        //    String realLabel = real.getKey();
        //    Map<String, Integer> predictedLabels = real.getValue();
        //    System.out.print(realLabel + ": ");
        //    for (Map.Entry<String, Integer> predicted : predictedLabels.entrySet()) {
        //        String predictedLabel = predicted.getKey();
        //        int count = predicted.getValue();
        //        System.out.print("    " + predictedLabel + ": " + count + ", ");
        //    }
        //    System.out.println();
        //}
        //System.out.println("Precision and Recall for each class:");
        //for (String classLabel : confusionMatrix.keySet()) {
        //    int tp = confusionMatrix.get(classLabel).getOrDefault(classLabel, 0);
        //    int fp = 0;
        //    int fn = 0;
        //
        //    // Sum up false positives (FP) and false negatives (FN)
        //    for (Map.Entry<String, Map<String, Integer>> real : confusionMatrix.entrySet()) {
        //        if (!real.getKey().equals(classLabel)) {
        //            fp += real.getValue().getOrDefault(classLabel, 0);
        //            fn += real.getValue().getOrDefault(classLabel, 0);
        //        }
        //    }
        //
        //    // Calculate precision and recall
        //    float precision = tp / (float)(tp + fp);
        //    float recall = tp / (float)(tp + fn);
        //
        //    System.out.println("Class: " + classLabel);
        //    System.out.println("Precision: " + precision);
        //    System.out.println("Recall: " + recall);
        //    System.out.println();
        //}
    }

    private void normalizeVectors() {
        //TODO: Should this consider null values?
        int numberOfFeatures = this.trainingSet.getFirst().features.size();

        for (int i = 0; i < numberOfFeatures; i++) {
            List<Double> numericValues = new ArrayList<>();
            for (FeatureVector vector : this.trainingSet) {
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
            List<Double> normalizedValues2 = new ArrayList<>();
            List<Object> values = new ArrayList<>();
            for (FeatureVector vector : this.trainingSet) {
                Object fv = vector.features.get(i);
                if (fv instanceof Number) {
                    double normalizedValue = (((Number) fv).doubleValue() - min) / (max - min);
                    vector.features.set(i, normalizedValue);
                    values.add(normalizedValue);
                    normalizedValues2.add((Double) normalizedValue);
                }
            }
            for (FeatureVector vector : this.testingSet) {
                Object fv = vector.features.get(i);

                if (fv instanceof Number) {
                    double normalizedValue = (((Number) fv).doubleValue() - min) / (max - min);
                    vector.features.set(i, normalizedValue);
                    values.add(normalizedValue);
                }
            }
            //System.out.println(i);
            //System.out.println("Min: " + min);
            //System.out.println("Max: " + max);
            //System.out.println("Training set: min:" + Collections.min(normalizedValues2) + ", max:" + Collections.max(normalizedValues2));

            //System.out.println("Values: " + values);
        }

    }

    private void saveToFile() {

    }

}
