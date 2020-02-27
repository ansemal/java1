package ru.progwards.java1.lessons.egts;

public class EgtsDirectionAndSpeed {
    public static int getSpeed(short speedAndDir) {
        int result = speedAndDir << 17;
        return result >>> 17;
    }

    public static int getDirection(byte dirLow, short speedAndDir) {
        int dir = speedAndDir >>> 15;
        int gradus = dirLow & 0b00000000_00000000_00000000_11111111;
        if (dir == 0) {
            return gradus;
        } else return gradus+180;
    }

    public static void main(String[] args) {
        System.out.println(getSpeed((short) 0b10000000_10010101));
        System.out.println(getDirection((byte) 0b10010101, (short) 0b00000000_10010101));
    }
}
