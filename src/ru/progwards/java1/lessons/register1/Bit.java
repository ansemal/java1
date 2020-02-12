package ru.progwards.java1.lessons.register1;

public class Bit {
    public boolean value;

    public Bit() {
    }

    public Bit(boolean value) {
        this ();
        this.value = value;
    }

    public String toString() {
        if (value) return "1";
        return "0";
    }
}
