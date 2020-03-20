package ru.progwards.java1.lessons.queues;

public class Calculate {

    public static double calculation1() {
        StackCalc stackCalc = new StackCalc();
        stackCalc.push(2.2);
        stackCalc.push(3.0);
        stackCalc.push(12.1);
        stackCalc.add();
        stackCalc.mul();
        return stackCalc.pop();
    }

    public static double calculation2() {
        StackCalc stackCalc = new StackCalc();
        stackCalc.push(737.22);
        stackCalc.push(24.0);
        stackCalc.add();
        stackCalc.push(55.6);
        stackCalc.push(12.1);
        stackCalc.sub();
        stackCalc.div();
        stackCalc.push(19.0);
        stackCalc.push(3.33);
        stackCalc.sub();
        stackCalc.push(87.0);
        stackCalc.push(2.0);
        stackCalc.push(13.001);
        stackCalc.push(9.2);
        stackCalc.sub();
        stackCalc.mul();
        stackCalc.add();
        stackCalc.mul();
        stackCalc.add();
        return stackCalc.pop();
    }

    public static void main(String[] args) {
        System.out.println(calculation2());
    }
}
