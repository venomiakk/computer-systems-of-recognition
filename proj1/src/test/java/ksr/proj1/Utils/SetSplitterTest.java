package ksr.proj1.Utils;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SetSplitterTest {

    @Test
    public void shuffleTest(){
        List<String> testList = new java.util.ArrayList<>(List.of("a", "b", "c", "d", "e"));
        Collections.shuffle(testList, new Random(32));
        System.out.println(testList);
        assertEquals("d", testList.get(0));
        assertEquals("a", testList.get(1));
        assertEquals("e", testList.get(2));
        assertEquals("b", testList.get(3));
        assertEquals("c", testList.get(4));

    }

}