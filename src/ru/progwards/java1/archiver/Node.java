package ru.progwards.java1.archiver;

import java.util.HashMap;
import java.util.Map;

public class Node implements  Comparable<Node>{

    public int freq;   // частота узла
    Map<Character, String> charTable;  // словарь entry:  символ - путь по дереву

    public Node (Character ch, Integer freq) {  //конструктор для отдельных символов
        charTable = new HashMap<>(1);
        charTable.put(ch, "");
        this.freq = freq;
    }

    public Node (Character ch, Integer freq, String str) {  //конструктор когда в файле только один символ
        charTable = new HashMap<>(1);
        charTable.put(ch, str);
        this.freq = freq;
    }

    public Node (Node left, Node right) {   // конструктор для узлов
        freq = left.freq + right.freq;
        charTable = new HashMap<>();
        for (Map.Entry<Character, String> entry: left.charTable.entrySet()) {
            charTable.put(entry.getKey(), "0" + entry.getValue());
        }
        for (Map.Entry<Character, String> entry: right.charTable.entrySet()) {
            charTable.put(entry.getKey(), "1" + entry.getValue());
        }

    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.freq, o.freq);
    }
}
