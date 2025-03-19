package ksr.proj1.Utlis;

public class ListUtils {
    // deep copy of list
    public static <T> java.util.List<T> deepCopyList(java.util.List<T> list) {
        java.util.List<T> newList = new java.util.ArrayList<>();
        for (T element : list) {
            newList.add(element);
        }
        return newList;
    }
}
