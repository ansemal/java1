package ru.progwards.java1.lessons.register2;

public class ByteRegister extends Register{

    public ByteRegister() {//- инициализация нулями
        super(8);
    }

    public ByteRegister(byte value)  {
        this ();
        for (int i=0; i <=7; i++) {
            if ((value & 0b00000001) == 1)
                bitArrays [i] = bit1;
            else bitArrays [i] = bit0;
            value >>= 1;
        }
    }

    public static void main(String[] args) {
        ByteRegister dd = new ByteRegister((byte) 12);
        System.out.println(dd);
        System.out.println(dd.toDecString());
    }
}
