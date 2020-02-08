package ru.progwards.java1.lessons.bitsworld;

public class SumBits {

    public static int sumBits(byte value) {
        int bit;
        int result = 0;
        for (int i=1; i<=8; i++) {
            bit = value & 0b00000001;
            if (bit == 1)
                result += 1;
            value >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(sumBits((byte) 8));
    }
}
