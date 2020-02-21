package ru.progwards.java1.lessons.bigints;

import java.math.BigInteger;
import java.util.Arrays;

public class ArrayInteger {
    byte [] digits;

    ArrayInteger(int n) {
        digits = new byte [n];
    }

    void fromInt(BigInteger value) {
        BigInteger temp= new BigInteger("10");
        BigInteger cifra;
        int i = 0;
        do {
            cifra = value.mod(temp);
            digits [i] = cifra.byteValueExact();
            value = value.divide(temp);
            i++;
        } while (value.intValue() > 0);
    }

    BigInteger toInt() {
        String str = "";
        for (byte digit : digits) {
            str = digit + str;
        }
        return new BigInteger (str);
    }

    boolean add(ArrayInteger num) {
        byte perenos = 0;
        byte perenosTemp;
        for (byte i=0; i<this.digits.length; i++) {
            if ((this.digits [i] + num.digits [i] + perenos) > 9) {
                if (i == this.digits.length-1) {
                    Arrays.fill(this.digits, (byte) 0);
                    return false;
                }
                perenosTemp = (byte) ((this.digits [i] +  num.digits [i] + perenos) / 10);
                this.digits [i] = (byte) ((this.digits [i] + num.digits [i] + perenos) % 10);
                perenos = perenosTemp;
            }
            else {
                this.digits[i] = (byte) ((this.digits[i] + num.digits[i] + perenos) % 10);
                perenos = 0;
            }

        }


        return true;
    }

    public static void main(String[] args) {
        ArrayInteger aaa = new ArrayInteger(6);
        ArrayInteger bbb = new ArrayInteger(6);
        BigInteger big = new BigInteger("523456");
        aaa.fromInt(big);
        bbb.fromInt(big);
        System.out.println(aaa.toInt());
        System.out.println(aaa.add(bbb));
        System.out.println(aaa.toInt());
    }
}
