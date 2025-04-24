package ksr.proj1.FeatureExtraction;

import ksr.proj1.DataExtraction.KeywordsExtraction;
import ksr.proj1.DataExtraction.ReutersInfoData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class FeatureExtractorTest {

    private static List<String> surnameDict = new ArrayList<>();
    private static List<String> countryDict = new ArrayList<>();
    private static List<String> keywordDict = new ArrayList<>();
    private static List<String> stopWords = new ArrayList<>();
    private static List<String> testList = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        ReutersInfoData.readData();
        surnameDict = ReutersInfoData.allPeopleCodes;
        countryDict = ReutersInfoData.allPlacesCodes;
        keywordDict = List.of("keyword1", "keyword2", "keyword3");
        stopWords = List.of("the", "is", "in", "a", "and");
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
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
        String text = """
                Alfonsin Stich 19,70 bond Stoltenberg, Mohammed-ahmed-al-razaz, asffw - dsad? Stich Alfonsin
                """;
        String surname = featureExtractor.extractC3(text);
        Assertions.assertEquals("alfonsin", surname);
    }

    @Test
    void extractC4() {
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
        String text = """
                Alfonsin 19,70 usa Stoltenberg, vietnam, asffw - dsad? Stich west-germany? Vietnam Alfonsin
                """;
        String country = featureExtractor.extractC4(text);
        System.out.println(country);
        Assertions.assertEquals("vietnam", country);
    }

    @Test
    void extractC5() {
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
        String text = """
                Goo Gle was founded in 1945. Apple Inc. is based in Cupertino. He lives The United Nations  in New York.
                This is a Apple Inc. simple test. Microsoft is a big company The United Nations and OpenAI are leading in AI.
                The Eiffel Tower is in Paris.
                """;
        List<String> properNouns = featureExtractor.getProperNouns(text);
        String keyword = featureExtractor.extractC5(text, properNouns);
        System.out.println(keyword);

        Assertions.assertEquals(keyword, "apple inc");
    }

    @Test
    void extractC11() {
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
        String text = """
                Alfonsin Stich 19,70 bond Stoltenberg, Mohammed-ahmed-al-razaz, asffw - dsad? Stich Alfonsin
                """;
        int count = featureExtractor.extractC11(text);
        Assertions.assertEquals(6, count);
    }

    @Test
    void extractC12() {
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, keywordDict, stopWords);
        String text = """
                Alfonsin 19,70 usa Stoltenberg, vietnam, asffw - dsad? Stich west-germany? Vietnam Alfonsin
                """;
        int count = featureExtractor.extractC12(text);
        Assertions.assertEquals(4, count);
    }

    @Test
    void extractC7() throws IOException {
        List<String> newKeywords = KeywordsExtraction.extractKeywords();
        FeatureExtractor featureExtractor = new FeatureExtractor(surnameDict, countryDict, newKeywords, stopWords);
        String text = """
                Alfonsin animals 19,70 usa Stoltenberg, iron vietnam, asffw - dsad? Stich west-germany? Vietnam Alfonsin
                """;
        String word = featureExtractor.extractC7(text);
        System.out.println(word);
        Assertions.assertEquals("animals", word);
    }
}