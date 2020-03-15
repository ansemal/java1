package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Creator {

    public static Collection<Integer> fillEven(int n) {
        ArrayList<Integer> list = new ArrayList<>(n-1);
        int chet = 2;
        for (int i = 0; i<=n-1; i++) {
            list.add(i, chet);
            chet += 2;
        }
        return list;
    }

    public static Collection<Integer> fillOdd(int n) {
        LinkedList <Integer> list = new LinkedList<>();
        int nechet = 1;
        for (int i = 0; i <= n-1; i++) {
            list.push(nechet);
            nechet += 2;
        }
        return list;
    }

    public static Collection<Integer> fill3(int n) {
        List<Integer> list = new ArrayList<>(n*3);
        for (int i = 0; i <= n*3-1; i+=3) {
            list.add(i, i);
            list.add(i+1, i*i);
            list.add(i+2, i*i*i);
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(fillEven(10));
        System.out.println(fillOdd(10));
        System.out.println(fill3(20));
    }
}
