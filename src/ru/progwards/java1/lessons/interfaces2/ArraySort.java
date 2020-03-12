package ru.progwards.java1.lessons.interfaces2;

import java.util.Arrays;

public class ArraySort {

    public static void sort(Comparable<Number>[] a) {
        Number rev;
        for (int i=0; i-1<a.length; i++)
            for (int j=i+1; j<a.length; j++)
                if (a[i].compareTo((Number) a[j]) > 0) {
                    rev = (Number) a[i];
                    a[i] = a[j];
                    a[j] = rev;
                }
    }

    public static void main(String[] args) {
        String [] str = {"26","54","15","5","88","0","100","47","12","19"};
        Number temp = new IntNumber(0);
        Number [] mas = new IntNumber[10];
        for (int i = 0; i<mas.length; i++) {
            mas[i]= temp.newNumber(str[i]);
        }
        sort(mas);
        System.out.println(Arrays.toString(mas));

    }
}
