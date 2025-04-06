package ksr.proj1;

import ksr.proj1.Classifier.KNN;
import ksr.proj1.Classifier.KNN2;
import ksr.proj1.DataExtraction.*;
import ksr.proj1.Distances.Distances;
import ksr.proj1.Distances.chebyshevDistance;
import ksr.proj1.Distances.euclideanDistance;
import ksr.proj1.FeatureExtraction.FeatureExtractor;
import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.Metrics.NgramMethod;
import ksr.proj1.Metrics.WordsSimilirityMetrics;
import ksr.proj1.Distances.manhattanDistance;
import ksr.proj1.Utils.SetSplit;

import java.io.IOException;
import java.util.*;

public class ConsoleInterface {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        //testingReadingXml();
        //testingReadingInfo();
        //StopWords.getStopWords();
        //keywordsExtractionTest();
        classificationActionPlan();
        //newKnnTest();

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

    public static void classificationActionPlan() throws IOException {
        //! read data
        long startTime = System.currentTimeMillis();

        Distances mangattanDistance = new euclideanDistance();
        WordsSimilirityMetrics wordMetric = new NgramMethod();
        List<Integer> kn = List.of(1,2, 3, 5, 7, 9,12,15,20,30);
        for (Integer i : kn) {
            System.out.println("K = " + i);
            KNN knn = new KNN(mangattanDistance, wordMetric, i);
        }


        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime; // Calculate duration in milliseconds
        System.out.println("Execution time: " + duration + " milliseconds");
        System.out.println("Execution time: " + duration / 1000 + " seconds");
        System.out.println("Execution time: " + duration / 60000 + " minutes");

        //ReutersInfoData.readData();
//        List<String> surnameDict = ReutersInfoData.allPeopleCodes;
//        List<String> countryDict = ReutersInfoData.allPlacesCodes;
//        List<String> keywordDict = KeywordsExtraction.extractKeywords(trainSet);
//        List<String> stopWords = StopWords.getStopWords();
//
//        //! extract features
//        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
//
//        ReutersElement testElement = trainSet.get(10204);
//        FeatureVector fv = featureExtractor.extractFeatures(testElement);
//        System.out.println(fv);
//        System.out.println(testElement);

        //! classify
    }

    public static void keywordsExtractionTest() {
        System.out.println("Extracting keywords");
        System.out.println(KeywordsExtraction.extractKeywords());
    }

    public static void newKnnTest() throws IOException {
        System.out.println("New KNN test");

        long startTime = System.currentTimeMillis();

        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        List<ReutersElement> articles =  ReutersXmlData.classificationArticles;

        ReutersInfoData.readData();
        List<String> surnameDict = ReutersInfoData.allPeopleCodes;
        List<String> countryDict = ReutersInfoData.allPlacesCodes;
        List<String> keywordDict = KeywordsExtraction.extractKeywords();

        List<String> stopWords = StopWords.getStopWords();

        Distances manhattanDistance = new chebyshevDistance();
        WordsSimilirityMetrics wordMetric = new NgramMethod();
        List<String> features = List.of("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12");

        KNN2 knn = new KNN2(articles, surnameDict, countryDict, keywordDict, stopWords);
        knn.knnClassify(5, 60, features, manhattanDistance, wordMetric);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime; // Calculate duration in milliseconds
        System.out.println("Execution time: " + duration + " milliseconds");
        System.out.println("Execution time: " + duration / 1000 + " seconds");
        System.out.println("Execution time: " + duration / 60000 + " minutes");

    }
}
