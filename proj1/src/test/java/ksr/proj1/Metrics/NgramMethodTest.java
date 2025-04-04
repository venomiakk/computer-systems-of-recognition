package ksr.proj1.Metrics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NgramMethodTest {
    NgramMethod m = new NgramMethod();
    @Test
    void NgramWithConstraints1() {

        //TODO: implement proper tests
        String word1 = "test";
        String word2 = "test";
        float similiarity = m.calc(word1, word2, 1, 2);
        Assertions.assertEquals(1.0f, similiarity);
    }

    @Test
    void NgramWithConstraints2() {
        //TODO: implement proper tests
        String word1 = "string";
        String word2 = "stringify";
        float similiarity = m.calc(word1, word2, 1, 2);
        DecimalFormat df_obj = new DecimalFormat("#.###");
        Assertions.assertEquals(df_obj.format(0.647),  df_obj.format(similiarity));
    }

    @Test
    void NgramWithConstraints3() {
        //TODO: implement proper tests
        String word1 = "string";
        String word2 = "stringify";
        float similiarity = m.calc(word1, word2, 1, 2);
        DecimalFormat df_obj = new DecimalFormat("#.###");
        Assertions.assertEquals(df_obj.format(0.647),  df_obj.format(similiarity));
    }


}
