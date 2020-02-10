package ru.progwards.java1.lessons.interfaces;

public class CalculateFibonacci {
    private static CacheInfo lastFibo;

    public static class CacheInfo {
        public int n;
        public int fibo ;
    }

    public static int fiboNumber(int n) {
        if (lastFibo == null)
            lastFibo = new CacheInfo();
        if (lastFibo.n == n) return lastFibo.fibo;
        int a = 0;
        int b = 1;
        int c = 0;
        if (n == 1) {
            lastFibo.n = 1;
            return lastFibo.fibo = 1;
        }
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        lastFibo.n = n;
        return lastFibo.fibo = c;
    }

    public static CacheInfo getLastFibo() {
         return lastFibo;
    }

    public static void clearLastFibo() {
        lastFibo = null;
    }

    public static void main(String[] args) {
        System.out.println(fiboNumber(9));
        System.out.println(fiboNumber(25));
        System.out.println(getLastFibo());
        System.out.println( lastFibo.n + ", " + lastFibo.fibo);
        clearLastFibo();
        System.out.println(fiboNumber(12));
        System.out.println(fiboNumber(3));

    }

}
