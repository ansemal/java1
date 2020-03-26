package ru.progwards.java1.lessons.register2;


public class Register{
    Bit[] bitArrays;
    final Bit bit1 = new Bit(true);
    final Bit bit0 = new Bit(false);

    public Register(int reg) {
        bitArrays = new Bit[reg];
        for (int i = 0; i < reg; i++)
            bitArrays[i] = bit0;
    }

    public int tip (Register value) {
        if (value.bitArrays.length == 32)
            return 32;
        else
            return 8;
    }

    @Override
    public String toString() {
        String str = "";
        for (Bit bitArray : bitArrays)
            str = bitArray.toString() + str;
        return str;
    }

    public String toDecString() {//- вывод в десятичной системе счисления
        int temp = 0;
        int t, stepen;
        String str;
        for (int i = 0; i < bitArrays.length; i++) {
            //если прямой код и 1 или дополнительный код и 0
            if ((bitArrays[i].value && !bitArrays [bitArrays.length-1].value) ||
                    (!bitArrays[i].value && bitArrays [bitArrays.length-1].value) )
                t = 1;
            else t = 0;
            stepen = 2;
            for (int j=0; j<=i; j++) {
                if (j==0) stepen = 1;
                if (j>1) stepen *= 2;
                temp = stepen * t + temp;
            }
        }
        if (bitArrays [bitArrays.length-1].value) {
            temp += 1;
            str = "-" + temp;
        } else str = Integer.toString(temp);
        return str;
    }
}



