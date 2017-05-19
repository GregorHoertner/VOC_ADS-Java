package lecture.chapter01.tests;

import lecture.chapter01.Array;
import lecture.chapter01.IndexSearch;

public class IndexSearchTreiber {
    public static void main(String[] args) {
        Array array = new Array(10);

        for (int i = 0; i < 10; i++) {
            array.set(i, new Long(100 + i));
        }

        Long key = new Long(102);
        int index = IndexSearch.indexSearch(array, key);

        System.out.println("Index of " + key + ": " + index);
    }
}
