package ru.progwards.java1.lessons.interfaces;

public class CalculateFibonacci {
    private static CacheInfo lastFibo;

    public static class CacheInfo {
        public int n;
        public int fibo ;
    }

    public static int fiboNumber(int n) {
       CacheInfo tempFibo = new CacheInfo();
//        tempFibo = getLastFibo();
//       if (tempFibo.n == n)
//           return tempFibo.fibo;
        int a = 0;
        int b = 1;
        int c = 0;
        if (n == 1) {
//            lastFibo.n = 1;
            tempFibo.n = 1;
            tempFibo.fibo = 1;
            lastFibo = tempFibo;
            return 1;
        }
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
//        lastFibo.n = n;
        tempFibo.n = n;
        tempFibo.fibo = c;
        lastFibo = tempFibo;
//        lastFibo.n = tempFibo.n;
//        lastFibo.fibo = tempFibo.fibo;
        return  c;
    }

    public static CacheInfo getLastFibo() {
//        lastFibo.n =1;
         return lastFibo;
    }

    public static void clearLastFibo() {
        lastFibo = null;
    }

    public static void main(String[] args) {
//       CacheInfo ff = new CacheInfo();
//       ff.n = 5;
//       ff.fibo = 25;
//       CalculateFibonacci.lastFibo.n = ff.n;
//       CalculateFibonacci.lastFibo.fibo = 0;
        System.out.println(fiboNumber(9));
        System.out.println(fiboNumber(25));
        System.out.println(getLastFibo());
  //      clearLastFibo();
        System.out.println( lastFibo.n + ", " + lastFibo.fibo);

    }

}
