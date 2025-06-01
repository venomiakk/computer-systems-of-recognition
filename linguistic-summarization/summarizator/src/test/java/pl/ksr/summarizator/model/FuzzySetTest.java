package pl.ksr.summarizator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.ksr.summarizator.model.fuzzylogic.FuzzySet;
import pl.ksr.summarizator.model.membership.MembershipFunction;
import pl.ksr.summarizator.model.membership.Trapezoidal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FuzzySetTest {

    private FuzzySet fuzzySet;

    @BeforeEach
    void setUp() {
        // Initialize any common setup for the tests here
        MembershipFunction membershipFunction = new Trapezoidal(29.0, 29.0, 100.0, 150.0);
        List<CarObject> carObjects = List.of(
                new CarObject(List.of("id", "power"), List.of(0, 90.0)),
                new CarObject(List.of("id", "power"), List.of(1, 150.0)),
                new CarObject(List.of("id", "power"), List.of(2, 50.0)),
                new CarObject(List.of("id", "power"), List.of(3, 29.0)),
                new CarObject(List.of("id", "power"), List.of(4, 129.0))
        );
        fuzzySet = new FuzzySet("TestFuzzySet", carObjects, "power", membershipFunction);
    }

    @Test
    void overview() {
        fuzzySet.getFuzzySet().forEach((key, value) ->
                System.out.println(
                        "Car ID: " + key.getCarProperties().get("id") +
                                ", Power: " + key.getCarProperties().get("power") +
                                ", Membership: " + value));
        assertTrue(true);
    }

    @Test
    void testGetSupport() {
        List<CarObject> support = fuzzySet.getSupport();
        assertEquals(4, support.size(), "Support size should be 4");
        assertFalse(support.isEmpty(), "Support should not be empty");
        assertTrue(support.stream().anyMatch(car -> car.getCarProperties().get("power").equals(100.0)), "Support should contain car with power 100.0");
        assertTrue(support.stream().anyMatch(car -> car.getCarProperties().get("power").equals(129.0)), "Support should contain car with power 150.0");
    }

    @Test
    void testGetCardinality() {
        double cardinality = fuzzySet.getCardinality();
        assertEquals(3.42, cardinality, "Cardinality should be 3.42");
    }

    @Test
    void testGetDegreeOfFuzziness() {
        double degreeOfFuzziness = fuzzySet.getDegreeOfFuzziness();
        assertEquals(0.8, degreeOfFuzziness, "Degree of fuzziness should be approximately 0.80");
    }
}