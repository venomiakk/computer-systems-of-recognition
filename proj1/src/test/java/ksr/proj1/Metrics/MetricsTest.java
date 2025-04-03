package ksr.proj1.Metrics;

import ksr.proj1.FeatureExtraction.FeatureVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MetricsTest {
    Metrics m = new Metrics();
    FeatureVector featureVector1 = new FeatureVector(
            2157, "usa", 1, 1, 1, "Smith", "USA", "John", 1, "finance", "inflation", 1f, 1f, 5, 3
    );

    // Creating the second FeatureVector object
    FeatureVector featureVector2 = new FeatureVector(
            1234, "canada", 2, 2, 2, "Smith", "USA", "John", 2, "finance", "inflation", 2f, 2f, 6, 4
    );

    @Test
    void euclideanDistanceTest() {
        Assertions.assertEquals(2.6457512, m.euclideanDistance(featureVector1, featureVector2, new NgramMethod()), 0.0001);
    }

    @Test
    void manhhatanDistanceTest() {
        Assertions.assertEquals(2.6457512, m.manhattanDistance(featureVector1, featureVector2, new NgramMethod()), 0.0001);
    }

    @Test
    void chebyszewDistanceTest() {
        Assertions.assertEquals(1, m.chebyshevDistance(featureVector1, featureVector2, new NgramMethod()), 0.0001);
    }
}
