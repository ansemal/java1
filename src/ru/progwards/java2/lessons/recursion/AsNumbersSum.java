package ru.progwards.java2.lessons.recursion;

public class AsNumbersSum {
    static StringBuilder stringBuilder = new StringBuilder();
    static int[] termArr = new int[100];

    public static String asNumbersSum(int number) {
        termOfNumber(number, number, 0);
        stringBuilder.delete(stringBuilder.length()-3, stringBuilder.length());
        return stringBuilder.toString();
    }

    public static void stringBuild (int index, int up) {
        if (up== index-1)
            stringBuilder.append(termArr[up]).append(" = ");
        else {
            stringBuilder.append(termArr[up]).append("+");
            stringBuild(index, up + 1);
        }
    }

    static void termOfNumber(int num, int ost, int i) {
        if ( num < 0 ) return;
        if ( num == 0 ) {
            stringBuild(i, 0);
        }
        else {
            if ( num >= ost) {
                termArr[i] = ost;
                termOfNumber(num - ost, ost, i + 1);
            }
            if ( ost > 1) termOfNumber(num, ost - 1, i);
        }
    }

    public static void main(String[] args) {
        System.out.println(asNumbersSum(6));
    }
}