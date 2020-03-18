package ru.progwards.java1.lessons.sets;

import java.io.FileReader;
import java.util.Scanner;
import java.util.TreeSet;

public class LettersInFile {

    public static String process(String fileName) throws Exception {
        String str, result = "";
        TreeSet <Character> treeSet = new TreeSet <Character>();
        try (FileReader reader = new FileReader(fileName); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                str = scanner.nextLine();
                for (int i=0; i<str.length(); i++) {
                    if (Character.isAlphabetic(str.charAt(i))) {
                        treeSet.add(str.charAt(i));
                    }
                }
            }
        }
        for (Character ch: treeSet) {
            result += ch.toString();
        }
    return result;

    }

    public static void main(String[] args) {
        try {
            System.out.println(process("log.txt"));
        } catch (Exception e) {
            System.out.println("Вот такое исключение " + e.getMessage());
        }
    }
}
