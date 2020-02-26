package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.util.Scanner;

public class LineCount {
    public static int calcEmpty(String fileName) {
        int i = 0;
        try {
            FileReader reader = new FileReader(fileName);
            Scanner scanner = new Scanner(reader);
            try {
                while (scanner.hasNextLine()) {
                    if (scanner.nextLine().isEmpty()) {
                        i++;
                    }
                }
            } finally {
                reader.close();
            }
            return i;
        } catch (Exception e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        System.out.println(calcEmpty("tmp.txt"));
    }
}
