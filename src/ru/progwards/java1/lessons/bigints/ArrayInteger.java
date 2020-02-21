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
            digits[i] = cifra.byteValueExact();
            value = value.divide(temp);
            System.out.print(digits[i]);
            i++;
        } while (i < digits.length);
        System.out.println();
    }

    BigInteger toInt() {
        String str = "";
        for (int i = digits.length-1; i>=0; i--) {
            str = str + digits[i];
        }
        return new BigInteger (str);
    }

    boolean add(ArrayInteger num) {
        byte perenos = 0;
        byte perenosTemp;
        int bolshe = 0;
        if (this.digits.length > num.digits.length) {
            bolshe = this.digits.length - num.digits.length;
        }
        for (byte i=0; i<this.digits.length-bolshe; i++) {
            if ((this.digits [i] + num.digits [i] + perenos) > 9) {
                if (i == this.digits.length-1 || this.digits.length < num.digits.length) {
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
        if (this.digits.length > num.digits.length && perenos > 0) {
            for (int q=this.digits.length-bolshe; q < this.digits.length; q++) {
                this.digits[q] = (byte) (this.digits [q] + perenos);
                if (this.digits[q] < 9) {
                    break;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayInteger aaa = new ArrayInteger(6);
        ArrayInteger bbb = new ArrayInteger(4);
        BigInteger big = new BigInteger("555555");
        BigInteger big1 = new BigInteger("5555");
        aaa.fromInt(big);
        bbb.fromInt(big1);
        System.out.println(aaa.toInt());
        System.out.println(aaa.add(bbb));
        System.out.println(aaa.toInt());
    }
}
