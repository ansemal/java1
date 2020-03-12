package ru.progwards.java1.lessons.interfaces2;

public class DoubleNumber extends Number {
    Double num;

    public DoubleNumber(Double num) {
        this.num = num;
    }

    @Override
    public int compareTo (Number num) {
        String str = num.toString();
        DoubleNumber inum = (DoubleNumber) newNumber(str);
        return Double.compare(this.num,inum.num);
    }

    @Override
    public Number mul(Number num) {
        String str = num.toString();
        DoubleNumber inum = (DoubleNumber) newNumber(str);
        return new DoubleNumber(this.num*inum.num);
    }

    public Number div(Number num) {
        String str = num.toString();
        DoubleNumber inum = (DoubleNumber) newNumber(str);
        return new DoubleNumber(this.num/inum.num);
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
