package ru.progwards.java1.lessons.register1;

public class Counter {

    public static void inc(ByteRegister value) {
        for (int i=0; i <8; i++) {
            if (value.bitArrays[i].value)
                value.bitArrays[i] = value.bit0;
            else {
                value.bitArrays[i] = value.bit1;
                break;
            }
        }
    }

    public static void dec(ByteRegister value) {
        for (int i=0; i <8; i++) {
            if (!value.bitArrays[i].value)
                value.bitArrays[i] = value.bit1;
            else {
                value.bitArrays[i] = value.bit0;
                break;
            }
        }
    }

    public static void main(String[] args) {
        ByteRegister aaa = new ByteRegister ((byte) 155);
        inc(aaa);
        dec(aaa);

    }
}
