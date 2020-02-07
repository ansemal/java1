package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class Eratosthenes {

    private boolean [] sieve;

    public Eratosthenes(int N) {
        sieve = new boolean [N];
        Arrays.fill(sieve, true);
        sift();
    }

    private void sift() {
        for (int p=2; p<= sieve.length; p++) {
            int q=2;
            for (int i=q*p; q*p<=sieve.length; q++)
                sieve[q*p-1] = false;
        }
    }

    public boolean isSimple(int n) {
        return sieve [n-1];
    }

    public static void main(String[] args) {
        Eratosthenes AA = new Eratosthenes(30);
        System.out.println(AA.isSimple(25));
    }

}
