package ru.progwards.java1.lessons.register2;

public class ShiftRegister {
    public static void left(Register value) {
        int razmer = value.tip(value);
        for (int i=razmer-1; i > 0; i--)
            value.bitArrays[i] = value.bitArrays[i - 1];
        value.bitArrays [0] = value.bit0;
    }

    public static void right(Register value) {
        int razmer = value.tip(value);
        for (int i =0; i<razmer-1; i++)
            value.bitArrays[i] = value.bitArrays[i+1];
        value.bitArrays [razmer-1] = value.bit0;
    }

    public static void main(String[] args) {
        ByteRegister aaa = new ByteRegister ((byte) -5);
        System.out.println(aaa);
        left(aaa);
        System.out.println(aaa);
        right(aaa);
        System.out.println(aaa);
    }
}
