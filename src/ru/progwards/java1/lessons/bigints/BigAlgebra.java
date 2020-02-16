package ru.progwards.java1.lessons.bigints;

import java.math.BigDecimal;

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


    public static void main(String[] args) {
        BigDecimal aaa = new BigDecimal("2.5");
        System.out.println(fastPow(aaa, 3));

    }
}
