package ksr.proj1;

import ksr.proj1.FeatureExtraction.FeatureExtractor;
import ksr.proj1.DataExtraction.ReutersInfoData;
import ksr.proj1.DataExtraction.ReutersXmlData;
import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.Utils.ListUtils;

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

        //split data into training and test sets


        // generate dictionaries
        ReutersInfoData.readData();

        // extract features
        List<ReutersElement> allSet = ListUtils.deepCopyList(ReutersXmlData.classificationArticles);

        //ReutersElement testElement = allSet.get(10139);
        ReutersElement testElement = allSet.get(10204);
        //System.out.println(testElement);

        FeatureExtractor featureExtractor = new FeatureExtractor(ReutersInfoData.allPeopleCodes, ReutersInfoData.allPlacesCodes);
        featureExtractor.extractFeatures(testElement);
        System.out.println(testElement);
        System.out.println(testElement.featuresString());

        // classify
    }
}
