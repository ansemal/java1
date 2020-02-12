package ru.progwards.java1.lessons.interfaces;

import ru.progwards.java1.lessons.interfaces.CompareWeight.CompareResult;

public class ArraySort {


    public static void sort(CompareWeight [] a) {
        CompareWeight ArraySort;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++)
                if (a[i].compareWeight(a[j]) == CompareResult.GREATER) {
                    ArraySort = a[i];
                    a[i] = a[j];
                    a[j] = ArraySort;
                }
        }
    }



    public static void main(String[] args) {
        Food AAA = new Food(25);
        Food AA1 = new Food(255);
        Food AA2 = new Food(55);
        Food AA3 = new Food(2515);
        Food AA4 = new Food(235);
        CompareWeight [] a = {AAA,AA1,AA2,AA3,AA4};
        sort(a);

    }
}
