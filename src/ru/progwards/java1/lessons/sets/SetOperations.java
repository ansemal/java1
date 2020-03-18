package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.Set;

public class SetOperations {

    public static Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        HashSet <Integer> result = new HashSet <Integer> (set1);
        result.addAll(set2);
        return result;
    }

    public static Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        HashSet <Integer> result = new HashSet <Integer> (set1);
        result.retainAll(set2);
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

    public static void main(String[] args) {
        int [] mas1 = {0,2,3,4,5,6,8,9,10};
        int [] mas2 = {0,1,3,4,5,7,8,9,10};
        Set <Integer> set1 = new HashSet <Integer> ();
        Set <Integer> set2 = new HashSet <Integer> ();

        for (int i = 0; i<mas1.length; i++) {
            set1.add(mas1[i]);
            set2.add(mas2[i]);
        }
        System.out.println(intersection(set1,set2));
    }
}
