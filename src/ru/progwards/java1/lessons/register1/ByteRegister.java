package ru.progwards.java1.lessons.register1;

public class ByteRegister {
    Bit [] bitArrays = new Bit [8];
    final Bit bit1 = new Bit(true);
    final Bit bit0 = new Bit(false);

    public ByteRegister() {//- инициализация нулями
        for (int i=0; i < 8; i++)
            bitArrays[i] = bit0;
    }

    public ByteRegister(byte value)  {
        this ();
        for (int i=0; i < 8; i++) {
            if ((value & 0b00000001) == 1)
                bitArrays [i] = bit1;
            else bitArrays [i] = bit0;
            value >>= 1;
        }
    }

    public String toString() {      //  - вывод в двоичном виде
        String str = "";
        for (int i=0; i<=7; i++)
            str = bitArrays [i].toString() + str;
        return str;
    }

    public String toDecString() {//- вывод в десятичной системе счисления
        int temp = 0;
        int t;
        String str = "";
        for (int i=0; i<=7; i++) {
            if (bitArrays [i].value)
                t = 1;
            else t =0;
            temp = (int) Math.pow(2, i)*t + temp;
        }
            str = temp + str;
        return str;
    }

    public static void main(String[] args) {
        ByteRegister dd = new ByteRegister((byte) 15);
        System.out.println(dd);
        System.out.println(dd.toDecString());
    }
}
