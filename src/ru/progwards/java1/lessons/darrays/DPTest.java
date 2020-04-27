package ru.progwards.java1.lessons.darrays;

import java.util.Arrays;

public class DPTest {
        static final int count1 = 100;

        public static void main(String[] args) {
            DPArray a1 = new DPArray(10);

            long start = System.currentTimeMillis();
            for(int i=0; i<count1; i++)
                a1.add(i);
            long middle = System.currentTimeMillis();
            System.out.println("DPArray add time ="+(middle-start));

            System.out.println(a1.size());

            for (int i=0; i<a1.array.size(); i++) {
                System.out.println(Arrays.toString(a1.array.get(i)));
            }

            System.out.println(a1.get(45));

            a1.remove(56);

            for (int j = 25; j < 40; j++) {
                a1.add(j, j + 200);
                for (int i = 0; i < a1.array.size(); i++) {
                    System.out.println(Arrays.toString(a1.array.get(i)));
                }
                System.out.println();
            }
            for (int i=0; i<a1.array.size(); i++) {
                System.out.println(Arrays.toString(a1.array.get(i)));
            }
            System.out.println(a1.size());

        }
}
