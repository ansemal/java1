package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Finder {

    public static Collection<Integer> findMinSumPair(Collection<Integer> numbers) {
        int sum = 0;
        int sumTemp;
        List<Integer> minIndex = new ArrayList<>();
        for (int i = 0; i < numbers.size()-1; i++) {
            sumTemp = ((List <Integer>) numbers).get(i) + ((List <Integer>) numbers).get(i + 1);
            if (i == 0) {
                sum = sumTemp;
            }
            if (sum > sumTemp) {
                sum = sumTemp;
                minIndex.clear();
                minIndex.add(i);
                minIndex.add(i+1);
            } else if (sum == sumTemp) {
                minIndex.add(i);
                minIndex.add(i+1);
            }
        }
        return minIndex;
    }

    public static Collection<Integer> findLocalMax(Collection<Integer> numbers) {
        int ind0, ind1, ind2;
        List<Integer> localMax = new ArrayList<>();
        for (int i=1; i <numbers.size()-1; i++) {
            ind0 = ((List <Integer>) numbers).get(i-1);
            ind1 = ((List <Integer>) numbers).get(i);
            ind2 = ((List <Integer>) numbers).get(i+1);
            if (ind1 > ind0 && ind1 > ind2) {
                localMax.add(ind1);
                i++;
            }
        }
        return localMax;
    }

    public static boolean findSequence(Collection<Integer> numbers) {
        for (int i = 1; i <= numbers.size(); i++) {
            if (!numbers.contains(i))
                return false;
        }
        return true;
    }

    public static String findSimilar(Collection<String> names) {
        int count = 1;
        int countTemp = 0;
        String itog = "";
        for (int i = 0; i < names.size()-1; i++) {
            if (((List<String>) names).get(i).equals(((List<String>) names).get(i + 1))) {
                count++;
            } else {
                countTemp = count;
                count = 1;
            }
            if (countTemp < count || i == 0) {
                itog = ((List<String>) names).get(i) + ":" + count;
            }
        }
        return itog;
    }
}
