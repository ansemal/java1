package ru.progwards.java1.lessons.interfaces2;

public class DoubleNumber extends Number {
    double num;

    public DoubleNumber(Double num) {
        this.num = num;
    }

    public double getNum (Number num) {
      return ((DoubleNumber) num).num;
  }

    @Override
    public int compareTo (Number num) {
        return Double.compare(this.num,getNum(num));
    }

    @Override
    public Number mul(Number num) {
        return new DoubleNumber(this.num*getNum(num));
    }

    public Number div(Number num) {
        return new DoubleNumber(this.num/getNum(num));
    }

    @Override
    public Number newNumber(String strNum) {
        return  new DoubleNumber(Double.parseDouble(strNum));
    }

    public String toString() {
        return String.valueOf(num);
    }

    public static void main(String[] args) {
        DoubleNumber dd = new DoubleNumber(25.49316366562466);
        DoubleNumber cc = new DoubleNumber(25.493163665624666);
        System.out.println(dd.compareTo(cc));
    }
}
