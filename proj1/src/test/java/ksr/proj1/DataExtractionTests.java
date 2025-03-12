package ksr.proj1;

import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.DataExtraction.ReutersXmlData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DataExtractionTests {

    @Test
    void xmlDataReadRepeatabilityTest() {
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        List<ReutersElement> elements1 = new ArrayList<>();
        for (ReutersElement element : ReutersXmlData.classificationArticles) {
            elements1.add(new ReutersElement(element.id, element.cgiSplit, element.lewisSplit, element.date,
                    element.topics, element.places, element.people, element.orgs, element.exchanges, element.companies,
                    element.title, element.author, element.dateline, element.body, element.label, element.lp));
        }

        ReutersXmlData.allArticles.clear();
        ReutersXmlData.classificationArticles.clear();

        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();

        if (elements1.size() != ReutersXmlData.classificationArticles.size()) {
            Assertions.fail();
        }
        for (int i = 0; i < elements1.size(); i++) {
            if (elements1.get(i).lp != ReutersXmlData.classificationArticles.get(i).lp) {
                Assertions.fail();
            }
        }

        Assertions.assertTrue(true);
    }
}