package ru.progwards.java1.lessons.interfaces2;

public abstract class Number implements Comparable <Number>{

    @Override
    public int compareTo (Number num) {
        return 2;
    }

    public Number mul(Number num) {
        return null;
    }

    public Number div(Number num) {
        return null;
    }

    public Number newNumber(String strNum) {
        return null;
    }

    public int getNum () {
        return 0;
    }

    public String toString() {
        return null;
    }
}
