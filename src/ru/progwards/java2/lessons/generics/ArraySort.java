package ru.progwards.java2.lessons.generics;

import java.util.Arrays;

public class ArraySort  {

    public static <T extends Comparable <T>> void sort(T[] a) {
        T rev;
        for (int i=0; i-1<a.length; i++)
            for (int j=i+1; j<a.length; j++)
                if (a[i].compareTo(a[j]) > 0) {
                    rev = a[i];
                    a[i] = a[j];
                    a[j] = rev;
                }
        System.out.println(Arrays.toString(a));
    }

        public static void main(String[] args) {
        Integer [] mas= {5,8,7,2,0,9,4,3,8};
        String [] mas1 = {"Анна", "Петр", "Игорь", "Жанна", "Виктор"};
        sort(mas);
        sort(mas1);
    }
}
