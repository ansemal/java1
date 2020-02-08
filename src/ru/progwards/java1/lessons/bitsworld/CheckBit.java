package ru.progwards.java1.lessons.bitsworld;

public class CheckBit {

    public static int checkBit(byte value, int bitNumber) {
        int i = 0;
        while (i < bitNumber) {
            value >>= 1;
            i++;
        }
        return value & 0b00000001;
    }

    public static void main(String[] args) {
        System.out.println(checkBit((byte) 9, 3));
    }
}
