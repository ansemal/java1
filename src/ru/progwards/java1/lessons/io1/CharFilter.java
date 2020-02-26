package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class CharFilter {

    public static void filterFile(String inFileName, String outFileName, String filter) throws Exception {
            FileReader reader = new FileReader(inFileName);
            Scanner scanner = new Scanner(reader);
            try {
                while (scanner.hasNextLine()) {
                    String temp = scanner.nextLine();
                    for (int i = 0; i < filter.length(); i++) {
                        temp = temp.replace(Character.toString(filter.charAt(i)), "");
                    }
                        FileWriter writer = new FileWriter(outFileName,true);
                        try {
                            writer.write(temp);
                        } finally {
                            writer.close();
                        }
                }
            } finally {
                scanner.close();
                reader.close();
            }
    }


    public static void main(String[] args) {
        try {
            filterFile("tmp.txt", "out.txt", " .,-()");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
