package ru.progwards.java1.lessons.bigints;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigAlgebra {

     static BigDecimal fastPow(BigDecimal num, int pow) {
        String str = Integer.toBinaryString(pow);
        int [] bitArrays = new int [str.length()];

        int temp = pow;
        for (int i=0; i < str.length(); i++) {
                bitArrays[i] = temp & 0b00000000_00000000_00000000_00000001;
                temp >>= 1;
        }

        BigDecimal z = new BigDecimal("1");
        for (int i=str.length()-1; i>=0; i--) {
                z = z.multiply(z);
            if (bitArrays[i] == 1) {
                z = z.multiply(num);
            }
        }
        return z;
    }

    static BigInteger fibonacci(int n) {
         int a = 0;
        int b = 1;
        int c = 0;
        if (n == 1)
            return BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return  BigInteger.valueOf(c);

    }


    public static void main(String[] args) {
        BigDecimal aaa = new BigDecimal("2.5");
        System.out.println(fastPow(aaa, 3));
        System.out.println(fibonacci(8));
    }
}
