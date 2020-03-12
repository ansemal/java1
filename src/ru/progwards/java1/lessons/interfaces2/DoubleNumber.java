package ru.progwards.java1.lessons.interfaces2;

public class DoubleNumber extends Number {
    double num;

    public DoubleNumber(Double num) {
        this.num = num;
    }

    @Override
    public int getNum () {
        return (int) num;
    }

    @Override
    public int compareTo (Number num) {
        int inum = num.getNum();
        return Double.compare(this.num,inum);
    }

    @Override
    public Number mul(Number num) {
        int inum = num.getNum();
        return new DoubleNumber(this.num*inum);
    }

    public Number div(Number num) {
        int inum = num.getNum();
        return new DoubleNumber(this.num/inum);
    }

    @Override
    public Number newNumber(String strNum) {
        return  new DoubleNumber(Double.parseDouble(strNum));
    }

    public String toString() {
        return String.valueOf(num);
    }

    public static void main(String[] args) {
        DoubleNumber dd = new DoubleNumber(15.0);
        DoubleNumber cc = new DoubleNumber(15.0);
        System.out.println(dd.compareTo(cc));
    }
}
