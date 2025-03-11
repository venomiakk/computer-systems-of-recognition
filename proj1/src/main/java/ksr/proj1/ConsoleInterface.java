package ksr.proj1;

import ksr.proj1.DataExtraction.ReutersData;
import ksr.proj1.DataExtraction.ReutersElement;

import java.util.HashSet;
import java.util.Set;

public class ConsoleInterface {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        ReutersData.readXmlFiles();
        System.out.println(ReutersData.allArticles.getFirst());
        ReutersData.selectArticlesForClassification();
        System.out.println(ReutersData.classificationArticles.size());
        Set<String> testSet = new HashSet<String>();
        for (ReutersElement element : ReutersData.classificationArticles) {
            testSet.add(element.places.getFirst());
        }
        System.out.println(testSet);
    }
}
