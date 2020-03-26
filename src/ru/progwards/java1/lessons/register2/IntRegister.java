package ru.progwards.java1.lessons.register2;

public class IntRegister extends Register  {

    public IntRegister() {//- инициализация нулями
        super (32);
    }

    public IntRegister (int value) {
        this ();
        for (int i=0; i <= 31; i++) {
            if ((value & 0b00000001) == 1)
                bitArrays [i] = bit1;
            else bitArrays [i] = bit0;
            value >>= 1;
        }
    }

    public static void main(String[] args) {
        IntRegister aa = new IntRegister(1560);
        System.out.println(aa.toString());
        System.out.println(aa.toDecString());
    }
}
