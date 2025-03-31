package ksr.proj1.FeatureExtraction;

import ksr.proj1.DataExtraction.ReutersInfoData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeatureExtractorTest {

    private static List<String> surnameDict = new ArrayList<>();
    private static List<String> countryDict = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        ReutersInfoData.readData();
        surnameDict = ReutersInfoData.allPeopleCodes;
        countryDict = ReutersInfoData.allPlacesCodes;
    }

    @Test
    void extractC1() {
        //TODO: implement proper tests
        FeatureExtractor featureExtractor = new FeatureExtractor();
        int count = featureExtractor.extractC1("test, - test.");
        Assertions.assertEquals(11, count);
    }

    @Test
    void extractC2() {
        //TODO: implement proper tests
        FeatureExtractor featureExtractor = new FeatureExtractor();
        float count = featureExtractor.extractC2("test, test.", 2);
        Assertions.assertEquals(1, count);
    }

    @Test
    void extractC3() {
        //TODO: test this method
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict);
        String text = """
                Alfonsin Stich 19,70 bond Stoltenberg, Mohammed-ahmed-al-razaz, asffw - dsad.
                """;
        String surname = featureExtractor.extractC3(text);
        System.out.println(surname);
        Assertions.assertTrue(true);
    }
}