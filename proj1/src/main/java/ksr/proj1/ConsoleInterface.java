package ksr.proj1;

import ksr.proj1.DataExtraction.StopWords;
import ksr.proj1.FeatureExtraction.FeatureExtractor;
import ksr.proj1.DataExtraction.ReutersInfoData;
import ksr.proj1.DataExtraction.ReutersXmlData;
import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.FeatureExtraction.FeatureVector;
import ksr.proj1.DataExtraction.KeywordsExtraction;
import ksr.proj1.Utils.SetSplit;

import java.io.IOException;
import java.util.*;

public class ConsoleInterface {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        //testingReadingXml();
        //testingReadingInfo();
        classificationActionPlan();
        //StopWords.getStopWords();
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
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();

        //! split data into training and test sets
        SetSplit setSplit = new SetSplit(ReutersXmlData.classificationArticles, ReutersXmlData.classificationArticles.size(), 80);
        List<ReutersElement> trainSet = setSplit.trainSet;
        List<ReutersElement> testSet = setSplit.testSet;

        //! generate dictionaries
        ReutersInfoData.readData();
        List<String> surnameDict = ReutersInfoData.allPeopleCodes;
        List<String> countryDict = ReutersInfoData.allPlacesCodes;
        List<String> keywordDict = KeywordsExtraction.extractKeywords(trainSet);
        List<String> stopWords = StopWords.getStopWords();

        //! extract features
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);

        ReutersElement testElement = trainSet.get(10204);
        FeatureVector fv = featureExtractor.extractFeatures(testElement);
        System.out.println(fv);
        System.out.println(testElement);

        //! classify
    }
}
