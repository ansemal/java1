package ru.progwards.java1.lessons.abstractnum;

public class IntNumber extends Number{
    int num;

    public IntNumber(int num) {
        this.num = num;
    }

    @Override
    public Number mul(Number num) {
        String str = num.toString();
        IntNumber inum = (IntNumber) newNumber(str);
        return new IntNumber(this.num*inum.num);
    }

    @Override
    public Number div(Number num) {
        String str = num.toString();
        IntNumber inum = (IntNumber) newNumber(str);
        return new IntNumber(this.num/inum.num);
    }

    @Override
    public Number newNumber(String strNum) {
        return  new IntNumber(Integer.parseInt(strNum));
    }

    public String toString() {
        return String.valueOf(num);

    }
}
