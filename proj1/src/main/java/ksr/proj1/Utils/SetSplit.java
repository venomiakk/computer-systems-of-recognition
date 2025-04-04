package ksr.proj1.Utils;

import ksr.proj1.DataExtraction.ReutersElement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SetSplit {
    public List<ReutersElement> trainSet;
    public List<ReutersElement> testSet;

    public SetSplit(List<ReutersElement> articles, int percentageOfTrainSet) {
        Map<String, Long> placeCounts = articles.stream()
                .flatMap(article -> article.places.stream())
                .collect(Collectors.groupingBy(place -> place, Collectors.counting()));


        Map<String, Long> trainSetSizes = placeCounts.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() * percentageOfTrainSet / 100));


        testSet = articles.stream()
                .filter(article -> article.places.stream().anyMatch(place -> trainSetSizes.getOrDefault(place, 0L) > 0))
                .peek(article -> article.places.forEach(place -> trainSetSizes.put(place, trainSetSizes.get(place) - 1)))
                .collect(Collectors.toList());

        trainSet = articles.stream()
                .filter(article -> !testSet.contains(article))
                .collect(Collectors.toList());

    }
}

