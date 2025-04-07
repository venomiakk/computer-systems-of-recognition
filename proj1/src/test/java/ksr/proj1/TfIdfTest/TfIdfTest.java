package ksr.proj1.TfIdfTest;

import ksr.proj1.DataExtraction.ReutersElement;
import ksr.proj1.DataExtraction.ReutersXmlData;
import ksr.proj1.DataExtraction.TfIdfKeyWordExtraction;
import ksr.proj1.Utils.KeyWordDao;
import ksr.proj1.Utils.SetSplit;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
        KeyWordDao dao = new KeyWordDao();
        ReutersXmlData.readXmlFiles();
        ReutersXmlData.selectArticlesForClassification();
        SetSplit setSplit = new SetSplit(ReutersXmlData.classificationArticles,60);
        List<ReutersElement> trainSet = setSplit.trainSet;
        List<String> trainSetString = new ArrayList<>();
        for (ReutersElement element : trainSet) {
            trainSetString.add(element.body);
        }

        TfIdfKeyWordExtraction tfIdfKeyWordExtraction = new TfIdfKeyWordExtraction();
        List<Map.Entry<String, Float>> top1000Entries = tfIdfKeyWordExtraction.keyWords(1000, trainSetString);
        // Print the top 1000 entries
        for (Map.Entry<String, Float> entry : top1000Entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Save the top 1000 entries to a file
        dao.saveKeywordsToFile(new ArrayList<>(top1000Entries.stream().map(Map.Entry::getKey).toList()), "top1000Keywords.txt");
    }


}
