package ru.progwards.java1.lessons.register1;

public class ShiftRegister {
    public static void left(ByteRegister value) {
        for (int i=7; i > 0; i--)
            value.bitArrays[i] = value.bitArrays[i - 1];
        value.bitArrays [0].value = false;
    }

    public static void right(ByteRegister value) {
        for (int i =0; i<7; i++)
            value.bitArrays[i] = value.bitArrays[i+1];
        value.bitArrays [7].value = false;
    }

    public static void main(String[] args) {
        ByteRegister aaa = new ByteRegister ((byte) 156);
        left(aaa);
        right(aaa);
    }
}
