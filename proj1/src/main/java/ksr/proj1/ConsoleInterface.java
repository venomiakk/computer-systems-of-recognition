package ksr.proj1;

import ksr.proj1.DataExtraction.ReutersInfoData;
import ksr.proj1.DataExtraction.ReutersXmlData;
import ksr.proj1.DataExtraction.ReutersElement;

import java.util.HashSet;
import java.util.Set;

public class ConsoleInterface {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //testingReadingXml();
        testingReadingInfo();
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
        System.out.println(ReutersInfoData.allExchangesCodes.size());
        System.out.println(ReutersInfoData.allExchangesCodes.getFirst());
        System.out.println(ReutersInfoData.allPlacesCodes.size());
        System.out.println(ReutersInfoData.allPlacesCodes.getFirst());
        System.out.println(ReutersInfoData.allTopicsCodes.size());
        System.out.println(ReutersInfoData.allTopicsCodes.getFirst());
        System.out.println(ReutersInfoData.allOrgsCodes.size());
        System.out.println(ReutersInfoData.allOrgsCodes.getFirst());
        System.out.println(ReutersInfoData.allPeopleCodes.size());
        System.out.println(ReutersInfoData.allPeopleCodes.getFirst());

        System.out.println(ReutersInfoData.commodityCodes.size());
        System.out.println(ReutersInfoData.commodityCodes.getFirst());
        System.out.println(ReutersInfoData.orgsNames.size());
        System.out.println(ReutersInfoData.orgsNames.getFirst());
        System.out.println(ReutersInfoData.exchangeNames.size());
        System.out.println(ReutersInfoData.exchangeNames.get(1) + ", " + ReutersInfoData.exchangeNames.get(2));
        System.out.println(ReutersInfoData.energyCodes.size());
        System.out.println(ReutersInfoData.energyCodes.getFirst());
        System.out.println(ReutersInfoData.energyNames.size());
        System.out.println(ReutersInfoData.energyNames.getFirst());
    }
}
