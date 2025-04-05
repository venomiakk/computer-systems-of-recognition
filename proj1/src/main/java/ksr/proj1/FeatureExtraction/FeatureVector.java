package ksr.proj1.FeatureExtraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

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
    public ArrayList<Object> features = new ArrayList<>();
    public HashMap<String, Object> featuresMap = new LinkedHashMap<>();
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
        features.add(c1);
        features.add(c2);
        features.add(c3);
        features.add(c4);
        features.add(c5);
        features.add(c6);
        features.add(c7);
        features.add(c8);
        features.add(c9);
        features.add(c10);
        features.add(c11);
        features.add(c12);


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

    public void convertToHashmap(){
        featuresMap.put("c1", c1);
        featuresMap.put("c2", c2);
        featuresMap.put("c3", c3);
        featuresMap.put("c4", c4);
        featuresMap.put("c5", c5);
        featuresMap.put("c6", c6);
        featuresMap.put("c7", c7);
        featuresMap.put("c8", c8);
        featuresMap.put("c9", c9);
        featuresMap.put("c10", c10);
        featuresMap.put("c11", c11);
        featuresMap.put("c12", c12);
    }

    //@Override
    //public String toString() {
    //    return "FeatureVector{" + '\n' +
    //            "originalID=" + originalID + '\n' +
    //            "realLabel=" + realLabel + '\n' +
    //            "reutersElementIndex=" + reutersElementIndex + '\n' +
    //            "predictedLabel=" + predictedLabel + '\n' +
    //            "c1: " + features.get(0) + "\n" +
    //            "c2: " + features.get(1) + "\n" +
    //            "c3: " + features.get(2) + "\n" +
    //            "c4: " + features.get(3) + "\n" +
    //            "c5: " + features.get(4) + "\n" +
    //            "c6: " + features.get(5) + "\n" +
    //            "c7: " + features.get(6) + "\n" +
    //            "c8: " + features.get(7) + "\n" +
    //            "c9: " + features.get(8) + "\n" +
    //            "c10: " + features.get(9) + "\n" +
    //            "c11: " + features.get(10) + "\n" +
    //            "c12: " + features.get(11) + "\n" +
    //            "}";
    //}

    @Override
    public String toString() {
        return "FeatureVector{" +
                "originalID=" + originalID +
                ", realLabel='" + realLabel + '\'' +
                ", reutersElementIndex=" + reutersElementIndex +
                ", predictedLabel='" + predictedLabel + '\'' +
                ", features=" + features +
                '}';
    }
}
