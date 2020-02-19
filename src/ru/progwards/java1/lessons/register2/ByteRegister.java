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

    public String toString() {      //  - вывод в двоичном виде
        String str = "";
        for (int i=0; i <= 7; i++)
            str = bitArrays [i].toString() + str;
        return str;
    }

    public String toDecString() {//- вывод в десятичной системе счисления
        int temp = 0;
        int t;
        String str = "";

        if (bitArrays [7].value) {
            //перевод из дополнительного кода
            for (int i = 0; i< 7; i++) {
                if (bitArrays[i].value)
                    bitArrays[i] = bit0;
                else
                    bitArrays[i] = bit1;
            }
            for (int i = 0; i< 7; i++) {
                if (bitArrays [i].value)
                    bitArrays [i] = bit0;
                else if (!bitArrays [i].value) {
                    bitArrays[i] = bit1;
                    break;
                }
            }
        }
            for (int i=0; i<7; i++) {
                if (bitArrays[i].value) {
                    t = 2;
                    for (int j = 0; j <= i; j++) {
                        if (j == 0) t = 1;
                        if (j > 1) t *= 2;
                        temp += t;
                    }
                }
            }
        if (!bitArrays [7].value) {
            str = temp + str;
        }
        else
            str = "-" + temp + str;
        return str;
    }


    public static void main(String[] args) {
        ByteRegister dd = new ByteRegister((byte) 127);
        System.out.println(dd);
        System.out.println(dd.toDecString());
    }
}
