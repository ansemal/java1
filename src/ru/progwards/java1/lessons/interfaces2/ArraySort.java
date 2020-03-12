package ru.progwards.java1.lessons.interfaces2;

import java.util.Arrays;

public class ArraySort {

    public static void sort(Comparable<Number>[] a) {
        Arrays.sort(a);
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
