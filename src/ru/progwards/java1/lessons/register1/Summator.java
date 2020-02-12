package ru.progwards.java1.lessons.register1;

public class Summator {

    public static boolean add(ByteRegister value1, ByteRegister value2) {
        for (int i=0; i<8; i++) {
            if (value1.bitArrays[i].value ^ value2.bitArrays [i].value) {
                value1.bitArrays[i] = value1.bit1;
                continue;
            }
            if (!value1.bitArrays[i].value && !value2.bitArrays[i].value) {
                value1.bitArrays[i] = value1.bit0;
                continue;
            }
            if (value1.bitArrays [i].value && value2.bitArrays [i].value) {
                value1.bitArrays [i] = value1.bit0;
                System.out.println(value1.bit0);
                for (int j=i+1;; j++) {
                    if (j == 8) return false;
                    if (!value1.bitArrays[j].value) {
                        value1.bitArrays [j] = value1.bit1;
                        break;
                    }
                    else value1.bitArrays [j] = value1.bit0;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ByteRegister A1 = new ByteRegister((byte)25);
        ByteRegister A2 = new ByteRegister((byte)11);
        System.out.println(A1);
        System.out.println(A2);
        System.out.println(add (A1, A2));
        System.out.println(A1);
        System.out.println(A1.toDecString());
    }
}
