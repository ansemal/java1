package ru.progwards.java1.lessons.basics;

public class AccuracyDoubleFloat {
    public static double volumeBallDouble(double radius) {
        return 4.0/3.0*3.14*radius*radius*radius;
    }
    public static float volumeBallFloat(float radius) {
        return 4F/3F*3.14F*radius*radius*radius;
    }
    public static double calculateAccuracy(double radius) {
        return volumeBallDouble(radius) - volumeBallFloat((float) radius);
    }

    public static void main(String[] args) {
        double radius = 6371.2;
        System.out.println(volumeBallDouble(radius));
        System.out.println(volumeBallFloat((float) radius));
        System.out.println(calculateAccuracy(radius));
    }

}
