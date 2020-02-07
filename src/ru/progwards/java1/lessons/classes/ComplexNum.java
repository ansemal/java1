package ru.progwards.java1.lessons.classes;

public class ComplexNum {
    int a, b;

    public ComplexNum(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return a + " + " + b + "i";
   }

    public ComplexNum add(ComplexNum num) {
        return new ComplexNum (a + num.a, b + num.b);
    }

    public ComplexNum sub(ComplexNum num) {
        return new ComplexNum (a - num.a, b - num.b);
    }

    public ComplexNum mul(ComplexNum num) {
        return new ComplexNum (a*num.a - b*num.b, b*num.a + a*num.b);
    }

    public ComplexNum div(ComplexNum num) {
        return new ComplexNum ((a*num.a + b*num.b)/(num.a*num.a + num.b*num.b), (b*num.a - a*num.b)/(num.a*num.a + num.b*num.b));
    }

    public static void main(String[] args) {
        ComplexNum z1 = new ComplexNum(2, 5);
        ComplexNum z2 = new ComplexNum(4,7);
        System.out.println(z1.add(z2));
        System.out.println(z1.sub(z2));
        System.out.println(z1.mul(z2));
        System.out.println(z1.div(z2));
    }
}
