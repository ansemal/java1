package ru.progwards.java1.lessons.abstractnum;

public class Test {
    public static void main(String[] args) {
        Figure3D intCube = new Cube(new IntNumber(3));
        System.out.println(intCube.volume());
        Figure3D doubleCube = new Cube(new DoubleNumber(3.0));
        System.out.println(doubleCube.volume());
        Figure3D intPyramid = new Pyramid(new IntNumber(3));
        System.out.println(intPyramid.volume());
        Figure3D doublePyramid = new Pyramid(new DoubleNumber(3.0));
        System.out.println(doublePyramid.volume());
    }
}
