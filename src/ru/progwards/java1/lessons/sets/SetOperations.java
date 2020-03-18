package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.Set;

public class SetOperations {

    public static Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        HashSet <Integer> result = new HashSet <Integer> (set1);
        result.retainAll(set2);
        return result;
    }

    public static Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        HashSet <Integer> result = new HashSet <Integer> (set1);
        result.addAll(set2);
        return result;
    }

    public static Set<Integer> difference(Set<Integer> set1, Set<Integer> set2) {
        HashSet <Integer> result = new HashSet <Integer> (set1);
        result.removeAll(set2);
        return result;
    }

    public static Set<Integer> symDifference(Set<Integer> set1, Set<Integer> set2) {
        HashSet <Integer> result = new HashSet <Integer> (set1);
        HashSet <Integer> resultTemp = new HashSet <Integer> (set1);
        result.addAll(set2);
        resultTemp.retainAll(set2);
        result.removeAll(resultTemp);
        return result;
    }
}
