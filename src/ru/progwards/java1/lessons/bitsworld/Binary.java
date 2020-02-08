package ru.progwards.java1.lessons.bitsworld;

public class Binary {
    int num;

    public Binary(byte num) {
        this.num = num;
    }

    public String toString() {
        int temp;
        String str = "";
        for (int i=0; i<=7; i++) {
            temp = num & 0b00000001;
            str = temp + str;
            num >>= 1;
        }
        return str;
    }

    public static void main(String[] args) {
        Binary prob = new Binary((byte) -1);
        System.out.println(prob.toString());
    }
}
