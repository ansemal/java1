package ru.progwards.java1.lessons.queues;

import java.util.LinkedList;

public class StackCalc {
    LinkedList <Double> stack = new LinkedList<>();

    public void push(double value) {
        stack.push(value);
    }

    public double pop() {
        return stack.poll();
    }

    public void add() {
        stack.push(stack.pop() + stack.pop());
    }

    public void sub() {
        stack.push(- stack.pop() + stack.pop());
    }

    public void mul() {
        stack.push(stack.pop() * stack.pop());
    }

    public void div() {
        stack.set(1, stack.get(1) / stack.get(0));
        stack.remove(0);
    }
}
