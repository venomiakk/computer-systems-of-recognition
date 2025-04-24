package ksr.proj1;

import ksr.proj1.Classifier.KNN;
import ksr.proj1.Classifier.KnnClassifier;
import ksr.proj1.DataExtraction.*;
import ksr.proj1.Classifier.DistanceMetrics.Distances;
import ksr.proj1.Classifier.DistanceMetrics.EuclideanDistance;
import ksr.proj1.Classifier.Measures.NgramMethod;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;

import java.io.IOException;
import java.util.*;

public class ConsoleInterface {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");


        newClassifierTest();
        classificationActionPlan();

    }

    public static void classificationActionPlan() throws IOException {
        System.out.println("classificationActionPlan");
        Distances distanceMeasure = new EuclideanDistance();
        WordsSimilarityMeasure wordMetric = new NgramMethod();

        //List<Integer> kn = List.of(1,2, 3, 5, 7, 9,12,15,20,30);
        //for (Integer i : kn) {
        //    System.out.println("K = " + i);
        //    KNN knn = new KNN(distanceMeasure, wordMetric, i);
        //}
        KNN knn = new KNN(distanceMeasure, wordMetric, 5);

    }

    public static void newClassifierTest() throws IOException {
        System.out.println("newClassifierTest");

        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        List<ReutersElement> articles =  ReutersXmlData.classificationArticles;

        ReutersInfoData.readData();
        List<String> surnameDict = ReutersInfoData.allPeopleCodes;
        List<String> countryDict = ReutersInfoData.allPlacesCodes;
        List<String> keywordDict = KeywordsExtraction.extractKeywords();

        List<String> stopWords = StopWords.getStopWords();

        Distances dstMetric = new EuclideanDistance();
        WordsSimilarityMeasure wordsSimMeasures = new NgramMethod();
        List<String> features = List.of("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12");

        KnnClassifier knnClassifier = new KnnClassifier(articles, surnameDict, countryDict, keywordDict, stopWords);
        knnClassifier.classify(5, 60, 0, features, dstMetric, wordsSimMeasures);

    }
}
