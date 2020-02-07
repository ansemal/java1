package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class DIntArray {
    private int [] mas;

    DIntArray() {
    }

    public void add(int num) {
        int [] mas1 = Arrays.copyOf(mas,mas.length+1);
        mas1 [mas.length] = num;
        System.out.println(Arrays.toString(mas1));
    }

    public void atInsert(int pos, int num) {
        int [] mas2 = new int [mas.length+1];
        for (int i=0; i<mas.length; i++) {
            if (i < pos-1)
                mas2[i] = mas[i];
            else mas2 [i + 1] = mas[i];
        }
        mas2 [pos-1] = num;
        System.out.println(Arrays.toString(mas2));
    }

    public void atDelete(int pos) {
        int [] mas3 = new int [mas.length-1];
        for (int i=0; i<mas.length; i++) {
            if (i < pos-1)
                mas3[i] = mas[i];
            else if (i > pos-1)
                mas3 [i -1] = mas[i];
            else continue;
        }
        System.out.println(Arrays.toString(mas3));
    }

    public int at(int pos) {
        System.out.println(Integer.toString(mas[pos-1]));
        return mas[pos-1];
    }

    public static void main(String[] args) {
        DIntArray b = new DIntArray();
        b.add(4);
        b.atInsert(2,555);
        b.atDelete(5);
        b.at(3);
    }
}
