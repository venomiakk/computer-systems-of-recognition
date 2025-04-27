package ksr.proj1;

import ksr.proj1.Classifier.ClassificationResult;
import ksr.proj1.Classifier.DistanceMetrics.ChebyshevDistance;
import ksr.proj1.Classifier.DistanceMetrics.Distances;
import ksr.proj1.Classifier.DistanceMetrics.EuclideanDistance;
import ksr.proj1.Classifier.DistanceMetrics.ManhattanDistance;
import ksr.proj1.Classifier.KnnClassifier;
import ksr.proj1.Classifier.Measures.NgramMethod;
import ksr.proj1.Classifier.Measures.WordsSimilarityMeasure;
import ksr.proj1.DataExtraction.*;
import ksr.proj1.Utils.SaveToXlsx;

import java.io.IOException;
import java.util.List;

public class Experiments {
    public static void preliminaryClassifierTest() throws IOException {
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        List<ReutersElement> articles = ReutersXmlData.classificationArticles;
        ReutersInfoData.readData();
        List<String> surnameDict = ReutersInfoData.allPeopleCodes;
        List<String> countryDict = ReutersInfoData.allPlacesCodes;
        List<String> keywordDict = KeywordsExtraction.extractKeywords();
        List<String> stopWords = StopWords.getStopWords();

        List<String> features = List.of("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12");
        List<Integer> kValues = List.of(1, 2, 3, 5, 9, 12, 15, 20, 30, 50);
        KnnClassifier knnClassifier = new KnnClassifier(articles, surnameDict, countryDict, keywordDict, stopWords);
        WordsSimilarityMeasure wordsSimMeasures = new NgramMethod();

        long startTime = System.currentTimeMillis();

        Distances dstMetric = new EuclideanDistance();
        for (int k : kValues) {
            ClassificationResult cr = knnClassifier.classify(k, 60, 1000, features, dstMetric, wordsSimMeasures);
            //cr.printClassificationResults();
            SaveToXlsx.saveData("Ekspertment wstępny na ograniczonym zbiorze z metryką euklidesową i k="+k, cr);
        }

        dstMetric = new ManhattanDistance();
        for (int k : kValues) {
            ClassificationResult cr = knnClassifier.classify(k, 60, 1000, features, dstMetric, wordsSimMeasures);
            //cr.printClassificationResults();
            SaveToXlsx.saveData("Ekspertment wstępny na ograniczonym zbiorze z metryką uliczną i k="+k, cr);
        }

        dstMetric = new ChebyshevDistance();
        for (int k : kValues) {
            ClassificationResult cr = knnClassifier.classify(k, 60, 1000, features, dstMetric, wordsSimMeasures);
            //cr.printClassificationResults();
            SaveToXlsx.saveData("Ekspertment wstępny na ograniczonym zbiorze z metryką chebysheva i k="+k, cr);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Czas wykonania [ms]: " + duration);
        System.out.println("Czas wykonania [s]: " + duration / 1000.0 );
        System.out.println("Czas wykonania [min]: " + duration / 60000.0 );
    }

    public static void experiment1kValues() throws IOException {
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        List<ReutersElement> articles = ReutersXmlData.classificationArticles;
        ReutersInfoData.readData();
        List<String> surnameDict = ReutersInfoData.allPeopleCodes;
        List<String> countryDict = ReutersInfoData.allPlacesCodes;
        List<String> keywordDict = KeywordsExtraction.extractKeywords();
        List<String> stopWords = StopWords.getStopWords();

        List<String> features = List.of("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12");
        WordsSimilarityMeasure wordsSimMeasures = new NgramMethod();
        Distances dstMetric = new EuclideanDistance();
        KnnClassifier knnClassifier = new KnnClassifier(articles, surnameDict, countryDict, keywordDict, stopWords);
        List<Integer> kValues = List.of(1, 2, 3, 5, 9, 12, 15, 20, 30, 50);
        for (int k : kValues) {
            ClassificationResult cr = knnClassifier.classify(k, 60, 0, features, dstMetric, wordsSimMeasures);
            //cr.printClassificationResults();
            SaveToXlsx.saveData("Ekspertment 1 z metryką euklidesową i k="+k, cr);
        }
    }

    public static void experiment2setSplt() throws IOException{
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        List<ReutersElement> articles = ReutersXmlData.classificationArticles;
        ReutersInfoData.readData();
        List<String> surnameDict = ReutersInfoData.allPeopleCodes;
        List<String> countryDict = ReutersInfoData.allPlacesCodes;
        List<String> keywordDict = KeywordsExtraction.extractKeywords();
        List<String> stopWords = StopWords.getStopWords();

        List<String> features = List.of("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12");
        WordsSimilarityMeasure wordsSimMeasures = new NgramMethod();
        Distances dstMetric = new EuclideanDistance();
        KnnClassifier knnClassifier = new KnnClassifier(articles, surnameDict, countryDict, keywordDict, stopWords);
        int k = 10;

        ClassificationResult cr1 = knnClassifier.classify(k, 10, 0, features, dstMetric, wordsSimMeasures);
        //cr1.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 2 z metryką euklidesową i k="+k, cr1);

        ClassificationResult cr2 = knnClassifier.classify(k, 30, 0, features, dstMetric, wordsSimMeasures);
        //cr2.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 2 z metryką euklidesową i k="+k, cr2);

        ClassificationResult cr3 = knnClassifier.classify(k, 50, 0, features, dstMetric, wordsSimMeasures);
        //cr3.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 2 z metryką euklidesową i k="+k, cr3);

        ClassificationResult cr4 = knnClassifier.classify(k, 70, 0, features, dstMetric, wordsSimMeasures);
        //cr4.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 2 z metryką euklidesową i k="+k, cr4);

        ClassificationResult cr5 = knnClassifier.classify(k, 90, 0, features, dstMetric, wordsSimMeasures);
        //cr5.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 2 z metryką euklidesową i k="+k, cr5);
    }

    public static void experiment3metrics() throws IOException{
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        List<ReutersElement> articles = ReutersXmlData.classificationArticles;
        ReutersInfoData.readData();
        List<String> surnameDict = ReutersInfoData.allPeopleCodes;
        List<String> countryDict = ReutersInfoData.allPlacesCodes;
        List<String> keywordDict = KeywordsExtraction.extractKeywords();
        List<String> stopWords = StopWords.getStopWords();

        List<String> features = List.of("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12");
        WordsSimilarityMeasure wordsSimMeasures = new NgramMethod();
        KnnClassifier knnClassifier = new KnnClassifier(articles, surnameDict, countryDict, keywordDict, stopWords);
        int k = 10;
        int split = 60;
        Distances dstMetric = new EuclideanDistance();
        ClassificationResult cr1 = knnClassifier.classify(k, split, 0, features, dstMetric, wordsSimMeasures);
        //cr1.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 3 z metryką euklidesową i k="+k, cr1);

        dstMetric = new ManhattanDistance();
        ClassificationResult cr2 = knnClassifier.classify(k, split, 0, features, dstMetric, wordsSimMeasures);
        //cr2.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 3 z metryką uliczną i k="+k, cr2);

        dstMetric = new ChebyshevDistance();
        ClassificationResult cr3 = knnClassifier.classify(k, split, 0, features, dstMetric, wordsSimMeasures);
        //cr3.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 3 z metryką chebysheva i k="+k, cr3);
    }

    public static void experiment4features() throws IOException{
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        List<ReutersElement> articles = ReutersXmlData.classificationArticles;
        ReutersInfoData.readData();
        List<String> surnameDict = ReutersInfoData.allPeopleCodes;
        List<String> countryDict = ReutersInfoData.allPlacesCodes;
        List<String> keywordDict = KeywordsExtraction.extractKeywords();
        List<String> stopWords = StopWords.getStopWords();

        WordsSimilarityMeasure wordsSimMeasures = new NgramMethod();
        KnnClassifier knnClassifier = new KnnClassifier(articles, surnameDict, countryDict, keywordDict, stopWords);
        int k = 10;
        int split = 60;
        Distances dstMetric = new EuclideanDistance();

        List<String> features1 = List.of("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12");
        ClassificationResult cr1 = knnClassifier.classify(k, split, 0, features1, dstMetric, wordsSimMeasures);
        //cr1.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 4 z metryką euklidesową i k="+k, cr1);

        List<String> features2 = List.of("c1", "c2", "c3", "c4", "c5", "c6");
        ClassificationResult cr2 = knnClassifier.classify(k, split, 0, features2, dstMetric, wordsSimMeasures);
        //cr2.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 4 z metryką euklidesową i k="+k, cr2);

        List<String> features3 = List.of("c7", "c8", "c9", "c10", "c11", "c12");
        ClassificationResult cr3 = knnClassifier.classify(k, split, 0, features3, dstMetric, wordsSimMeasures);
        //cr3.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 4 z metryką euklidesową i k="+k, cr3);

        List<String> features4 = List.of("c3", "c4", "c5", "c7", "c8");
        ClassificationResult cr4 = knnClassifier.classify(k, split, 0, features4, dstMetric, wordsSimMeasures);
        //cr4.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 4 cechy tekstowe z metryką euklidesową i k="+k, cr4);

        List<String> features5 = List.of("c1", "c2", "c6", "c9", "c10", "c11", "c12");
        ClassificationResult cr5 = knnClassifier.classify(k, split, 0, features5, dstMetric, wordsSimMeasures);
        //cr5.printClassificationResults();
        SaveToXlsx.saveData("Ekspertment 4 cechy numeryczne z metryką euklidesową i k="+k, cr5);
    }

    public static void runAllExperiments() throws IOException {
        long startTime = System.currentTimeMillis();
        experiment1kValues();
        experiment2setSplt();
        experiment3metrics();
        experiment4features();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Czas wykonania [ms]: " + duration);
        System.out.println("Czas wykonania [s]: " + duration / 1000.0 );
        System.out.println("Czas wykonania [min]: " + duration / 60000.0 );
    }
}
