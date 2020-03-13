package ru.progwards.java1.lessons.interfaces2;

public class IntNumber extends Number{
    int num;

    public IntNumber(int num) {
        this.num = num;
    }

    public int getNum (Number num) {
        return ((IntNumber) num).num;
    }

    @Override
    public int compareTo (Number num) {
        return Integer.compare(this.num, getNum(num));
    }

    @Override
    public Number mul(Number num) {
        return new IntNumber(this.num*getNum(num));
    }

    @Override
    public Number div(Number num) {
        return new IntNumber(this.num/getNum(num));
    }

    @Override
    public Number newNumber(String strNum) {
        return  new IntNumber(Integer.parseInt(strNum));
    }

    public String toString() {
        return String.valueOf(num);
    }

    public static void main(String[] args) {
        IntNumber dd = new IntNumber(10);
        IntNumber cc = new IntNumber(112);
        System.out.println(dd.compareTo(cc));
        System.out.println(dd.mul(cc));
    }
}
