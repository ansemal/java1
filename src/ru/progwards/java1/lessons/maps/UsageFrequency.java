package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class UsageFrequency {
    String str = "";

    public void processFile(String fileName) {
        try (FileReader reader = new FileReader(fileName); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                str += scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<Character, Integer> getLetters() {
        Map <Character, Integer> bukva = new HashMap<>();
        for (int i=0; i<str.length(); i++) {
            if (Character.isAlphabetic(str.charAt(i)) || Character.isDigit(str.charAt(i))) {
                Integer oldVal = bukva.putIfAbsent(str.charAt(i), 1);
                if (oldVal != null)
                    bukva.replace(str.charAt(i), bukva.get(str.charAt(i))+1);
            }
        }
        return bukva;
    }

    public Map<String, Integer> getWords() {
        Map <String, Integer> slovo = new HashMap<>();
        String [] text = str.split("[\\pP\\s=<A(.*?)>]");
        for (String s : text) {
            if (s.isEmpty()) continue;
            Integer oldVal = slovo.putIfAbsent(s, 1);
            if (oldVal != null)
                slovo.replace(s, slovo.get(s) + 1);
        }
        return slovo;
    }

    public static void main(String[] args) {
        UsageFrequency usageFrequency = new UsageFrequency();
        usageFrequency.processFile("wiki.test.tokens");
 //       System.out.println(usageFrequency.getLetters());
        System.out.println(usageFrequency.getWords());

    }
}
