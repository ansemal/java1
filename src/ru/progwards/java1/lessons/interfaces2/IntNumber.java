package ru.progwards.java1.lessons.interfaces2;

public class IntNumber extends Number{
    int num;

    public IntNumber(int num) {
        this.num = num;
    }

    @Override
    public int getNum () {
        return num;
    }

    @Override
    public int compareTo (Number num) {
        int inum = num.getNum();
        return Integer.compare(this.num, inum);
    }

    @Override
    public Number mul(Number num) {
        int inum = num.getNum();
        return new IntNumber(this.num*inum);
    }

    @Override
    public Number div(Number num) {
        int inum = num.getNum();
        return new IntNumber(this.num/inum);
    }

    @Override
    public Number newNumber(String strNum) {
        return  new IntNumber(Integer.parseInt(strNum));
    }

    public String toString() {
        return String.valueOf(num);

    }
}
