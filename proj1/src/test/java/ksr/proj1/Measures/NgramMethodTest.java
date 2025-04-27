package ksr.proj1.Measures;

import ksr.proj1.Classifier.Measures.NgramMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NgramMethodTest {
    NgramMethod m = new NgramMethod();
    @Test
    void NgramWithConstraints1() {
        String word1 = "test";
        String word2 = "test";
        double similiarity = m.calc(word1, word2, 1, 2);
        Assertions.assertEquals(1.0f, similiarity);
    }

    @Test
    void NgramWithConstraints2() {
        String word1 = "string";
        String word2 = "stringify";
        double similiarity = m.calc(word1, word2, 1, 2);
        DecimalFormat df_obj = new DecimalFormat("#.###");
        Assertions.assertEquals(df_obj.format(0.647),  df_obj.format(similiarity));
    }

    @Test
    void NgramWithConstraints3() {
        String word1 = "enterprise";
        String word2 = "hollow";
        double similiarity = m.calc(word1, word2, 1, 2);
        DecimalFormat df_obj = new DecimalFormat("#.###");
        Assertions.assertEquals(df_obj.format(0),  df_obj.format(similiarity));
    }


}
