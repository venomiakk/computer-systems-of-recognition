package ksr.proj1;

import ksr.proj1.Extraction.FeatureExtraction;
import ksr.proj1.Extraction.ReutersInfoData;
import ksr.proj1.Extraction.ReutersXmlData;
import ksr.proj1.Extraction.ReutersElement;
import ksr.proj1.Utlis.ListUtils;

import java.util.*;

public class ConsoleInterface {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //testingReadingXml();
        //testingReadingInfo();
        classificationActionPlan();
    }

    public static void testingReadingXml() {
        System.out.println("Reading xml data");
        ReutersXmlData.readXmlFiles();
        System.out.println(ReutersXmlData.allArticles.size());
        System.out.println(ReutersXmlData.allArticles.getFirst());
        ReutersXmlData.selectArticlesForClassification();
        System.out.println(ReutersXmlData.classificationArticles.size());
        Set<String> testSet = new HashSet<String>();
        for (ReutersElement element : ReutersXmlData.classificationArticles) {
            testSet.add(element.places.getFirst());
        }
        System.out.println(ReutersXmlData.classificationArticles.getFirst());
        System.out.println(testSet);
    }

    public static void testingReadingInfo() {
        System.out.println("Reading info data");
        ReutersInfoData.readData();
        //System.out.println(ReutersInfoData.allExchangesCodes.size());
        //System.out.println(ReutersInfoData.allExchangesCodes.getFirst());
        System.out.println(ReutersInfoData.allPlacesCodes.size());
        System.out.println(ReutersInfoData.allPlacesCodes.getFirst());
        //System.out.println(ReutersInfoData.allTopicsCodes.size());
        //System.out.println(ReutersInfoData.allTopicsCodes.getFirst());
        //System.out.println(ReutersInfoData.allOrgsCodes.size());
        //System.out.println(ReutersInfoData.allOrgsCodes.getFirst());
        System.out.println(ReutersInfoData.allPeopleCodes.size());
        System.out.println(ReutersInfoData.allPeopleCodes.getFirst());

    }

    public static void classificationActionPlan() {
        //read data

        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        System.out.println(ReutersXmlData.classificationArticles.size());
        ReutersInfoData.readData();

        //split data into training and test sets

        // Count occurrences of each class
        //Map<String, Integer> classCountMap = new HashMap<>();
        //for (ReutersElement article : ReutersXmlData.classificationArticles) {
        //    String className = article.label;
        //    classCountMap.put(className, classCountMap.getOrDefault(className, 0) + 1);
        //}
        //System.out.println("Class distribution:");
        //for (Map.Entry<String, Integer> entry : classCountMap.entrySet()) {
        //    System.out.println(entry.getKey() + ": " + entry.getValue());
        //}

        // generate dictionaries

        // extract features
        List<ReutersElement> allSet = ListUtils.deepCopyList(ReutersXmlData.classificationArticles);
        //int min = 90000;
        //int minIndex = 0;
        //for (ReutersElement element : allSet) {
        //    FeatureExtraction.extractFeatures(element);
        //    if (element.c1 < min && element.c1 > 0) {
        //        min = element.c1;
        //        minIndex = element.index;
        //    }
        //}
        //System.out.println("Min c1: " + min + " for article " + minIndex);
        //System.out.println(allSet.get(minIndex - 1));
        //System.out.println(allSet.get(minIndex - 1).featuresString());

        ReutersElement testElement = allSet.get(10139);
        FeatureExtraction.extractFeatures(testElement);
        System.out.println(testElement);
        System.out.println(testElement.featuresString());

        // classify
    }
}
