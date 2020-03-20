package ru.progwards.java1.lessons.queues;

public class Order implements Comparable <Order>{
    double sum;
    int num;
    static int allNum;
    int priority;

    public Order(double sum) {
        this.sum = sum;
        num = ++allNum;
        if (sum > 20000) {
            priority = 1;
        } else if (sum <= 10000) {
            priority = 3;
        } else priority = 2;
    }

    public double getSum() {
        return sum;
    }

    public int getNum() {
        return num;
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(num, o.num);
    }

    @Override
    public String toString() {
        return "Order{" + "num=" + num + "сумма=" + sum + '}';
    }
}
