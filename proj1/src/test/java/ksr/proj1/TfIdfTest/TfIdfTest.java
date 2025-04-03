package ksr.proj1.TfIdfTest;

import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.DataExtraction.ReutersXmlData;
import ksr.proj1.DataExtraction.TfIdfKeyWordExtraction;
import ksr.proj1.Utils.SetSplit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TfIdfTest {


    public TfIdfTest() throws IOException {
    }

    @Test
    void keyWordExtractionTest() throws IOException {
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        SetSplit setSplit = new SetSplit(ReutersXmlData.classificationArticles, ReutersXmlData.classificationArticles.size(), 40);
        List<ReutersElement> trainSet = setSplit.trainSet;
        List<String> trainSetString = new ArrayList<>();
        for (ReutersElement element : trainSet) {
            trainSetString.add(element.body);
        }
        TfIdfKeyWordExtraction tfIdfKeyWordExtraction = new TfIdfKeyWordExtraction(trainSetString);
        List<Map.Entry<String, Float>> top100Entries = getTop100Entries(tfIdfKeyWordExtraction.wordCount);

        // Print the top 100 entries
        for (Map.Entry<String, Float> entry : top100Entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static List<Map.Entry<String, Float>> getTop100Entries(HashMap<String, Float> map) {
        // Convert the HashMap to a list of entries
        List<Map.Entry<String, Float>> entryList = new ArrayList<>(map.entrySet());

        // Sort the list based on values in descending order
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Return the top 100 entries
        return entryList.subList(0, Math.min(1000, entryList.size()));

    }
}
