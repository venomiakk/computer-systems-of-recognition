package ksr.proj1;

import ksr.proj1.Classifier.ClassificationResult;
import ksr.proj1.Classifier.DistanceMetrics.ChebyshevDistance;
import ksr.proj1.Classifier.DistanceMetrics.ManhattanDistance;
import ksr.proj1.Classifier.KnnClassifier;
import ksr.proj1.Classifier.Measures.JaccardMeasure;
import ksr.proj1.DataExtraction.*;
import ksr.proj1.Classifier.DistanceMetrics.Distances;
import ksr.proj1.Classifier.DistanceMetrics.EuclideanDistance;
import ksr.proj1.Classifier.Measures.NgramMethod;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;

import java.io.IOException;
import java.util.*;

public class ConsoleInterface {
    public static void main(String[] args) throws IOException {
        newClassifierTest();
    }

    public static void newClassifierTest() throws IOException {
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        List<ReutersElement> articles = ReutersXmlData.classificationArticles;

        ReutersInfoData.readData();
        List<String> surnameDict = ReutersInfoData.allPeopleCodes;
        List<String> countryDict = ReutersInfoData.allPlacesCodes;
        List<String> keywordDict = KeywordsExtraction.extractKeywords();

        List<String> stopWords = StopWords.getStopWords();

        Distances dstMetric = new ChebyshevDistance();
        WordsSimilarityMeasure wordsSimMeasures = new NgramMethod();
        List<String> features = List.of("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12");

        KnnClassifier knnClassifier = new KnnClassifier(articles, surnameDict, countryDict, keywordDict, stopWords);
        ClassificationResult cr = knnClassifier.classify(5, 60, 0, features, dstMetric, wordsSimMeasures);
        cr.printClassificationResults();

    }
}
