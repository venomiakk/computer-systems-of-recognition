package ksr.proj1.DataExtraction;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KeywordsExtractionTest {

    @Test
    void extractKeywords() {
        List<String> kwList = KeywordsExtraction.extractKeywords();
        System.out.println(kwList);
    }
}