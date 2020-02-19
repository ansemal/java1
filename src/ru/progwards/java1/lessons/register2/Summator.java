package ru.progwards.java1.lessons.register2;

public class Summator{
    public static void add(Register value1, Register value2) {
        int razmer = value1.tip(value1);

        for (int i=0; i<razmer; i++) {
            if (value1.bitArrays[i].value ^ value2.bitArrays [i].value) {
                value1.bitArrays[i] = value1.bit1;
                continue;
            }
            if (!value1.bitArrays[i].value && !value2.bitArrays[i].value) {
                continue;
            }
            if (value1.bitArrays [i].value && value2.bitArrays [i].value) {
                value1.bitArrays [i] = value1.bit0;

                for (int j=i+1; j <=razmer-1 ; j++) {
                    if (value1.bitArrays[j].value) {
                        value1.bitArrays [j] = value1.bit0;
                    }
                    else {
                        value1.bitArrays[j] = value1.bit1;
                        break;
                    }
                }
            }
        }
    }

    public static void sub(Register value1, Register value2) {
        value2 = toTwosComplement(value2);
        add(value1, value2);
    }


    private static Register toTwosComplement(Register value) {
        int razmer = value.tip(value);
        for (int i=0; i <razmer; i++) {
            if (value.bitArrays[i].value)
                value.bitArrays[i] = value.bit0;
            else
                value.bitArrays[i] = value.bit1;
        }
        Counter.inc(value);
        return value;
    }


    public static void main(String[] args) {
        ByteRegister A1 = new ByteRegister((byte)-55);
        ByteRegister A2 = new ByteRegister((byte)-15);
        IntRegister I1 = new IntRegister(-655);
        IntRegister I2 = new IntRegister(-125);
        sub(I1,I2);
        System.out.println(I1);
        System.out.println(I1.toDecString());

    }
}
