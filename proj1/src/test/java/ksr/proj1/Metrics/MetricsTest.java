package ksr.proj1.Metrics;

import ksr.proj1.FeatureExtraction.FeatureVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MetricsTest {
    FeatureVector featureVector1 = new FeatureVector(
            2157, "usa", 1, 1, 1, "Smith", "USA", "John", 1, "finance", "inflation", 1f, 1f, 5, 3
    );

    // Creating the second FeatureVector object
    FeatureVector featureVector2 = new FeatureVector(
            1234, "canada", 2, 2, 2, "Smith", "USA", "John", 2, "finance", "inflation", 2f, 2f, 6, 4
    );

    @Test
    void NgramWithConstraints1() {
        Metrics m = new Metrics();
        Assertions.assertEquals(2.6457512, m.euclideanDistance(featureVector1, featureVector2, new NgramMethod()), 0.0001);
    }
}
