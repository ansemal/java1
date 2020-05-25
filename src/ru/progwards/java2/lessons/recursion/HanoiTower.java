package ru.progwards.java2.lessons.recursion;

import java.util.ArrayList;
import java.util.LinkedList;

public class HanoiTower {
    int size;
    int pos;
    String pin = "  I  ";
    String strPrintDown = "=================";
    ArrayList <LinkedList<Integer>> hanoi = new ArrayList<>();
    LinkedList <Integer> hanoiStartTower = new LinkedList<>();
    ArrayList <LinkedList<Integer>> hanoiPrint = new ArrayList<>();

    public HanoiTower(int size, int pos) {
        this.size = size;
        this.pos = pos;
    }

    void init () {
        for (int i=size; i > 0; i--) {
            hanoiStartTower.push(i);
        }
        for (int i=0; i < 3; i++) {
            hanoiPrint.add(i, new LinkedList<>());
            if (i == pos)
                hanoi.add(i, hanoiStartTower);
            else
                hanoi.add(i, new LinkedList<>());
        }
        setTrace(true);
    }

    public void move (int from, int to, boolean on) {
        moveTower(hanoi.get(from).size(), from, to, 3-from-to, on);
        setTrace(!on);
    }

    public void moveTower (int number, int from, int to, int free, boolean on) {
        // выход из рекурсии
        if (number==0)
            return;
        moveTower(number-1, from, free, to, on);
        // перенос
        hanoi.get(to).push(hanoi.get(from).pop());
        setTrace(on);
        moveTower(number-1, free, to, from, on);
    }

    void print() {
        StringBuilder strPrint;
        for (int i = 0; i< size; i++) {
            strPrint = new StringBuilder();
            for (int y = 0; y< hanoiPrint.size(); y++) {
                if (y!=2) {
                    if (hanoiPrint.get(y).get(i) != 0)
                        strPrint.append("<").append(String.format("%03d", hanoiPrint.get(y).get(i))).append(">").append('.');
                    else
                        strPrint.append(pin).append(' ');
                } else {
                    if (hanoiPrint.get(y).get(i) != 0)
                        strPrint.append("<").append(String.format("%03d", hanoiPrint.get(y).get(i))).append(">");
                    else
                        strPrint.append(pin);
                }
            }
            System.out.println(strPrint);
        }
        System.out.println(strPrintDown);
        System.out.println();
    }

    void setTrace(boolean on) {
        if (on) {
            for (int i = 0; i < 3; i++) {
                hanoiPrint.get(i).clear();
                hanoiPrint.get(i).addAll(hanoi.get(i));
            }
            for (LinkedList<Integer> integers : hanoiPrint) {
                for (int i = integers.size(); i < size; i++) {
                    integers.push(0);
                }
            }
            print();
        }
    }

    public static void main(String[] args) {
        HanoiTower hanoiTower = new HanoiTower(5,0);
        hanoiTower.init();
        hanoiTower.move(0,2, true);
    }
}
