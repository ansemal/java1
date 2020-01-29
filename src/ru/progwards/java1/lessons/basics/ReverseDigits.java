package ru.progwards.java1.lessons.basics;

public class ReverseDigits {
    public static int reverseDigits(int number) {
        Integer x = number / 100;
        Integer x1 = number % 100;
        Integer y = x1 / 10;
        Integer z = x1 % 10;
        String str1 = Integer.toString (z);
        String str2 = Integer.toString (y);
        String str3 = Integer.toString (x);
        return Integer.valueOf(str1+str2+str3);
    }
    public static void main(String[] args) {
        System.out.println(reverseDigits(487));
    }
}
