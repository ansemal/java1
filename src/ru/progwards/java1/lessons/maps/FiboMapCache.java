package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.util.*;

public class FiboMapCache {
    private static TreeMap<Integer, BigDecimal> fiboCache = new TreeMap<>();
    boolean cacheOn;
    BigDecimal fibo;

    public FiboMapCache(boolean cacheOn) {
        this.cacheOn = cacheOn;
        if (!cacheOn) clearCahe();
    }

    public BigDecimal fiboNumber(int n) {
        if (cacheOn && fiboCache == null)
            fiboCache  = new TreeMap<>();
        if (cacheOn && fiboCache.containsKey(n)) {
                return fiboCache.get(n);
        } else if (cacheOn &&  n>3 && fiboCache.lowerKey(n)>2) {
            int bliz = fiboCache.lowerKey(n);
            int a = fiboCache.get(bliz-1).intValue();
            int b = fiboCache.get(bliz).intValue();
            int c = fiboCache.get(bliz).intValue();
            for (int i = bliz+1; i <= n; i++) {
                c = a + b;
                a = b;
                b = c;
                fiboCache.put(i, new BigDecimal(c));
            }
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
                if (cacheOn) {
                    fiboCache.put(i, new BigDecimal(c));
            }
            fibo = new BigDecimal(c);
            }
        }
        return fibo;
    }

    public void clearCahe() {
        fiboCache = null;
        cacheOn = false;
    }

    public static void test() {
        FiboMapCache fiboMapCache = new FiboMapCache(true);
        long start = System.currentTimeMillis();
        for (int i=1; i<=1000; i++) {
            fiboMapCache.fiboNumber(i);
        }
        long finish = System.currentTimeMillis() - start;
        System.out.println("fiboNumber cacheOn=true время выполнения " + finish);

        fiboMapCache.clearCahe();

        start = System.currentTimeMillis();
        for (int i=1; i<=1000; i++) {
            fiboMapCache.fiboNumber(i);
        }
        finish = System.currentTimeMillis() - start;
        System.out.println("fiboNumber cacheOn=true время выполнения " + finish);
    }

    public static void main(String[] args) {
        test();
    }
}
