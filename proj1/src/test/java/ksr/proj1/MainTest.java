package ksr.proj1;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    public void tieBreakingTest() {
        List<String> nn = List.of("c", "b", "a", "a", "b", "c", "b", "a");
        Map<String, Integer> labelCounts = new LinkedHashMap<>();
        for (String label : nn) {
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
        }

        int maxCount = Collections.max(labelCounts.values());
        List<String> candidates = labelCounts.entrySet().stream()
                .filter(e -> e.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .toList();

        Assertions.assertEquals("b", candidates.getFirst());
    }

    @Test
    public void listStreamTest() {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(22);
        list.add(16);
        list.add(4);
        list.add(5);
        list.add(1);

        List<Integer> sortedList = list.stream()
                .sorted(Comparator.reverseOrder())
                .toList();

        System.out.println(list);
        System.out.println(sortedList);
    }
}
