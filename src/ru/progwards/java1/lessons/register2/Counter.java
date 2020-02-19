package ru.progwards.java1.lessons.register2;

public class Counter {

    public static void inc(Register value) {
        int razmer = value.tip(value);
        for (int i=0; i <razmer; i++) {
            if (value.bitArrays[i].value)
                value.bitArrays[i] = value.bit0;
            else {
                value.bitArrays[i] = value.bit1;
                break;
            }
        }
    }

    public static void dec(Register value) {
        int razmer = value.tip(value);
        for (int i=0; i <razmer; i++) {
            if (!value.bitArrays[i].value)
                value.bitArrays[i] = value.bit1;
            else {
                value.bitArrays[i] = value.bit0;
                break;
            }
        }
    }

    public static void main(String[] args) {
        ByteRegister aaa = new ByteRegister ((byte) -1);
        IntRegister bbb = new IntRegister(-1);
        System.out.println(bbb);
        System.out.println(aaa);
        inc(aaa);
        inc(bbb);
        System.out.println(aaa);
        System.out.println(bbb);
        dec(aaa);
        dec(bbb);
        System.out.println(aaa);
        System.out.println(bbb);

    }
}
