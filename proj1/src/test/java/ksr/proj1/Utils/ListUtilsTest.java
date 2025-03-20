package ksr.proj1.Utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListUtilsTest {

    @Test
    void deepCopyList() {
        List<Integer> list = new java.util.ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> newList = ListUtils.deepCopyList(list);
        assertEquals(list, newList);
        assertNotSame(list, newList);
        newList.set(0, 4);
        assertNotEquals(list, newList);
    }
}