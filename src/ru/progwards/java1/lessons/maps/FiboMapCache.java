package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.util.*;

public class FiboMapCache {
    private static Map<Integer, BigDecimal> fiboCache = new TreeMap<>();
    boolean cacheOn;
    BigDecimal fibo;

    public FiboMapCache(boolean cacheOn) {
        this.cacheOn = cacheOn;
        if (!cacheOn) clearCahe();
        if (cacheOn && fiboCache == null)
            fiboCache  = new TreeMap<>();
    }

    public BigDecimal fiboNumber(int n) {
        if (cacheOn && fiboCache.containsKey(n)) {
                return fiboCache.get(n);
        }
        else  {
            int a = 0;
            int b = 1;
            int c = 0;
            if (n == 1 ) {
                if (cacheOn) {
                    fiboCache.put(1, BigDecimal.ONE);
                }
                return BigDecimal.ONE;
            }
            for (int i = 2; i <= n; i++) {
                c = a + b;
                a = b;
                b = c;
            }
            fibo = new BigDecimal(c);
            if (cacheOn) {
                fiboCache.putIfAbsent(n, fibo);
            }

        }
        return fibo;
    }

    public void clearCahe() {
        fiboCache = null;
    }

    public static void test() {
        FiboMapCache fiboMapCache = new FiboMapCache(true);
        long start = System.currentTimeMillis();
        for (int i=1; i<=1000; i++) {
            fiboMapCache.fiboNumber(i);
        }
        long finish = System.currentTimeMillis() - start;
        System.out.println("fiboNumber cacheOn=true время выполнения " + finish);

        FiboMapCache fiboMapCacheF = new FiboMapCache(false);
        start = System.currentTimeMillis();
        for (int i=1; i<=1000; i++) {
            fiboMapCacheF.fiboNumber(i);
        }
        finish = System.currentTimeMillis() - start;
        System.out.println("fiboNumber cacheOn=false время выполнения " + finish);

        fiboMapCache = new FiboMapCache(true);
        start = System.currentTimeMillis();
        for (int i=1; i<=1000; i++) {
            fiboMapCache.fiboNumber(i);
        }
        finish = System.currentTimeMillis() - start;
        System.out.println("fiboNumber cacheOn=true время выполнения " + finish);

        fiboMapCacheF = new FiboMapCache(true);
        start = System.currentTimeMillis();
        for (int i=1; i<=1000; i++) {
            fiboMapCacheF.fiboNumber(i);
        }
        finish = System.currentTimeMillis() - start;
        System.out.println("fiboNumber cacheOn=true время выполнения " + finish);
    }

    public static void main(String[] args) {
        test();
    }
}
