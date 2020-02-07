package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class DIntArray {
    private int [] mas = new int [0];

    DIntArray() {
    }

    public void add(int num) {
        int [] mas1 = Arrays.copyOf(mas,mas.length+1);
        mas1 [mas.length] = num;
        mas = Arrays.copyOf(mas1,mas1.length);
    }

    public void atInsert(int pos, int num) {
        int [] mas2 = new int [mas.length+1];
        for (int i=0; i<mas.length; i++) {
            if (i < pos) mas2[i] = mas[i];
            else mas2 [i + 1] = mas[i];
        }
        mas2 [pos] = num;
        mas = Arrays.copyOf(mas2,mas2.length);
    }

    public void atDelete(int pos) {
            int[] mas3 = new int[mas.length - 1];
            for (int i = 0; i < mas.length - 1; i++) {
                if (i < pos)  mas3[i] = mas[i];
                else if (i > pos)   mas3[i - 1] = mas[i];
                else continue;
            }
            mas = Arrays.copyOf(mas3, mas3.length);
    }

    public int at(int pos) {
        return mas[pos];
    }

    public static void main(String[] args) {
        DIntArray b = new DIntArray();
        b.add(4);
        b.add(8);
        b.add(-48);
        b.add(0);
        b.atInsert(2,555);
        b.atDelete(4);
        b.at(3);
    }
}
