package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class ArraySort {

    public static void sort(int[] a) {
        int rev;
        for (int i=0; i-1<a.length; i++)
            for (int j=i+1; j<a.length; j++)
                if (a[i] > a[j]) {
                    rev = a[i];
                    a[i] = a[j];
                    a[j] = rev;
                }
        System.out.println(Arrays.toString(a));
    }

    public static void main(String[] args) {
        int [] mas= {5,8,7,2,0,9,4,3,8};
        sort(mas);

    }
}
