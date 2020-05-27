package ru.progwards.java2.lessons.calculator;

import java.util.LinkedList;

public class Calculator {

    public static int calculate(String expression) {
        LinkedList<Integer> sumAndSub = new LinkedList<>();
        String str = " ";
        int number=0;
        int nextNumber;
        int result = 0;
        boolean minus = false;
        for (int i = 0; i < expression.length(); i++) {
            if (i%2 == 0) {
                if (expression.charAt(i) == '(') {
                    int end = expression.indexOf(')', i+1);
                    nextNumber = calculate(expression.substring(i+1,end));
                    i = end;
                } else
                    nextNumber=Integer.parseInt(expression.substring(i, i + 1));

                switch (str) {
                    case " ":
                        number = nextNumber;
                        break;
                    case "*":
                        number *= nextNumber;
                        break;
                    case "/":
                        number /= nextNumber;
                        break;
                    case "+":
                        if (minus) {
                            number -= number * 2;
                            sumAndSub.addLast(number);
                            number = nextNumber;
                            minus = false;
                        } else {
                            sumAndSub.addLast(number);
                            number = nextNumber;
                        }
                        break;
                    case "-":
                        if (minus) {
                            number -= number * 2;
                            sumAndSub.addLast(number);
                            number = Integer.parseInt(expression.substring(i, i + 1));
                        } else {
                            sumAndSub.addLast(number);
                            number = Integer.parseInt(expression.substring(i, i + 1));
                            minus = true;
                        }
                        break;
                }
            }
            else
                str = expression.substring(i,i+1);

            if (i == expression.length()-1) {
                if (minus) {
                    number -= number * 2;
                }
                sumAndSub.addLast(number);
            }
        }

        int size = sumAndSub.size();
        for (int i =0; i < size; i++) {
            result += sumAndSub.poll();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(calculate("2*(3+4)*5-6/(1+2)"));
    }
}
