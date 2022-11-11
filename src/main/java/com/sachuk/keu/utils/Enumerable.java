package com.sachuk.keu.utils;

import java.util.ArrayList;
import java.util.List;

public class Enumerable {

    private static List<Integer> list = new ArrayList<Integer>();
    private static int start;
    private static int count;

    /**
     * Generates a sequence of integral numbers within a specified range </br>
     * <i>Like Enumarable.Range in C#</i>
     *
     * @param start first int value
     * @param count number of int in range
     * @return
     */
    public static List<Integer> range(int start, int count) {
        if (Enumerable.start == start && Enumerable.count == count)
            return list;
        list.clear();
        final int max = start + count;
        for (int i = start; i < max; i++) {
            list.add(i);
        }
        return list;

    }

}