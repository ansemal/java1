package ru.progwards.java1.lessons.register2;

public class Register{
    Bit[] bitArrays;
    final Bit bit1 = new Bit(true);
    final Bit bit0 = new Bit(false);

    public Register(int reg) {
        bitArrays = new Bit[reg];
        for (int i = 0; i < 8; i++)
            bitArrays[i] = bit0;
    }

    public int tip (Register value) {
        if (value.bitArrays.length == 32)
            return 32;
        else
            return 8;
    }

//    private Register toTwosComplement(Register value) {


        /*for (int i = 0; i< 7; i++) {
                if (bitArrays[i].value)
                    bitArrays[i] = bit0;
                else
                    bitArrays[i] = bit1;
                System.out.print(bitArrays[i].toString());
            }
            System.out.println();
            for (int i = 0; i< 7; i++) {
                if (bitArrays [i].value)
                    bitArrays [i] = bit0;
                else if (!bitArrays [i].value) {
                    bitArrays[i] = bit1;
                    break;
                }
                System.out.println(bitArrays[i].toString());
            }
            System.out.println();*/

//        return value;

//    }
}



