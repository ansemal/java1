package ru.progwards.java1.lessons.compare_if_cycles;

public class CyclesGoldenFibo {
    public static boolean containsDigit(int number, int digit) {
        int a = number;
        while (number >= digit) {
            if (number == digit || a == digit)
                return true;
            a = number % 10;
            number /= 10;
            if (number == 0)
                break;
        }
        return false;
    }

    public static int fiboNumber(int n) {
        int a = 0;
        int b = 1;
        int c = 0;
        if (n == 1)
            return 1;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return  c;
    }
    public static boolean isGoldenTriangle(int a, int b, int c) {
        if (a == b) {
            double x = (double) a / c;
            return x <= 1.61903 && x >= 1.61703;
        }
        else if (a == c) {
            double y = (double) a / b;
            return y <= 1.61903 && y >= 1.61703;
        }
        else if (c == b) {
            double z = (double) c / a;
            return z <= 1.61903 && z >= 1.61703;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(containsDigit(25064,0));
        for (int i=1; i<=15; i++)
            if (i < 15)
                System.out.print(fiboNumber(i) + ", ");
            else
                System.out.println(fiboNumber(i));
        for (int a = 1; a <= 100; a++)
            for (int b = 1; b <= 100; b++)
                for (int c = 1; c <= 100; c++)
                    if (isGoldenTriangle(a, b, c))
                        System.out.println("стороны " + a + ", " + b + ", " + c);
    }
}
