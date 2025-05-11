package ksr.knn.DataExtraction;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class KeywordsExtractionTest {

    @Test
    void extractKeywords() throws IOException {
        List<String> kwList = KeywordsExtraction.extractKeywords();
        System.out.println(kwList);
    }
}