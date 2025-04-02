package ksr.proj1.FeatureExtraction;

public class FeatureVector {
    /**
     * Class representing a single Reuters article.
     * Contains all the attributes of the article.
     * Most important attributes:
     * - id: unique identifier of the article taken from the XML file
     * - cgiSplit: split of the article according to the CGI scheme
     * - lewisSplit: split of the article according to the Lewis scheme
     * - body: the content of the article, used for classification
     * - label: label of the article [usa, france, japan, west-germany, uk, canada]
     * - reutersElementIndexindex: reutersElementIndexindex of the article in the classification list
     * Feature attributes:
     * - c1: int, number of characters in the text
     * - c2: float, ratio of punctuation marks to words
     * - c3: string, most common surname in the text
     * - c4: string, most common country in the text
     * - c5: string, most common proper noun in the text
     * - c6: int, number of keyword occurrences in the text
     * - c7: string, most common keyword in the text
     * - c8: string, least common keyword in the text
     * - c9: float, ratio of keywords to words in the text
     * - c10: float, ratio of the number of proper names to the number of words in the text
     * - c11: int, number of surnames in the text
     * - c12: int, number of country names in the text
     */

    int originalID;

    // classification attributes
    public String realLabel;
    public int reutersElementIndex;
    public String predictedLabel = null;

    //features
    public int c1;
    public float c2;
    public String c3;
    public String c4;
    public String c5;
    public int c6;
    public String c7;
    public String c8;
    public float c9;
    public float c10;
    public int c11;
    public int c12;

    public FeatureVector(int originalID, String realLabel, int reutersElementIndex, int c1, float c2, String c3, String c4, String c5,
                         int c6, String c7, String c8, float c9, float c10, int c11, int c12) {
        this.originalID = originalID;
        this.realLabel = realLabel;
        this.reutersElementIndex = reutersElementIndex;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
        this.c6 = c6;
        this.c7 = c7;
        this.c8 = c8;
        this.c9 = c9;
        this.c10 = c10;
        this.c11 = c11;
        this.c12 = c12;
    }

    @Override
    public String toString() {
        return "FeatureVector{" + '\n' +
                "originalID=" + originalID + '\n' +
                "realLabel='" + realLabel + '\n' +
                "reutersElementIndex=" + reutersElementIndex + '\n' +
                "predictedLabel='" + predictedLabel + '\n' +
                "c1: " + c1 + "\n" +
                "c2: " + c2 + "\n" +
                "c3: " + c3 + "\n" +
                "c4: " + c4 + "\n" +
                "c5: " + c5 + "\n" +
                "c6: " + c6 + "\n" +
                "c7: " + c7 + "\n" +
                "c8: " + c8 + "\n" +
                "c9: " + c9 + "\n" +
                "c10: " + c10 + "\n" +
                "c11: " + c11 + "\n" +
                "c12: " + c12 + "\n" +
                "}";
    }
}
