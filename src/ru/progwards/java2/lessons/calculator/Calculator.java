package ru.progwards.java2.lessons.calculator;

import java.util.LinkedList;

public class Calculator {

    public static int calculate(String expression) {
        LinkedList<Integer> sumAndSub = new LinkedList<>();
        String str = " ";
        int number=0;
        int nextNumber;
        int result = 0;
        int count = 0;
        int endTemp;
        boolean minus = false;
        for (int i = 0; i < expression.length(); i++) {
            if (i%2 == 0) {
                if (expression.charAt(i) == '(') {
                    endTemp = i;
                    do {  // проверка на вложенные скобки
                        if (expression.indexOf('(', endTemp) !=-1 && expression.indexOf(')', endTemp) > expression.indexOf('(', endTemp)) {
                            count++;
                            endTemp = expression.indexOf('(', endTemp)+1;
                        }
                        if (expression.indexOf('(', endTemp) ==-1 || expression.indexOf(')', endTemp) < expression.indexOf('(', endTemp)) {
                            count--;
                            endTemp = expression.indexOf(')', endTemp+1);
                        }
                    } while (count != 0);

                    // рекурсия на обработку вложенных скобок
                    nextNumber = calculate(expression.substring(i+1,endTemp));
                    i = endTemp;
                } else
                    nextNumber=Integer.parseInt(expression.substring(i, i + 1));

                // обработка арифметических операций - сложение вычитание - в список, умножение/деление - сразу
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
                            sumAndSub.addLast(-number);
                            minus = false;
                        } else
                            sumAndSub.addLast(number);
                        number = nextNumber;

                        break;
                    case "-":
                        if (minus)
                            sumAndSub.addLast(-number);
                        else {
                            sumAndSub.addLast(number);
                            minus = true;
                        }
                        number = nextNumber;
                        break;
                }
            }
            else
                str = expression.substring(i,i+1);

            if (i == expression.length()-1) {
                if (minus) {
                    number = -number;
                }
                sumAndSub.addLast(number);
            }
        }

        // сложение/вычитание
        int size = sumAndSub.size();
        for (int i =0; i < size; i++) {
            result += sumAndSub.poll();
        }
        return result;
    }

    public static void main(String[] args) {
       System.out.println(calculate("3*(2-4)*2"));
       System.out.println(calculate("2*((2+(1+4))-2+3-1*2)*5-6/(1+2)"));
    }
}
