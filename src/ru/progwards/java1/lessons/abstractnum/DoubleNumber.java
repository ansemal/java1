package ru.progwards.java1.lessons.abstractnum;

public class DoubleNumber extends Number {
    Double num;

    public DoubleNumber(Double num) {
        this.num = num;
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
}
