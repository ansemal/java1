package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.util.*;

public class SalesInfo {
    String [] csv;
    LinkedList <String> person = new LinkedList<>();
    LinkedList <String> tovar = new LinkedList<>();
    LinkedList <Integer> shtuk = new LinkedList<>();
    LinkedList <Double> cena = new LinkedList<>();

    public int loadOrders(String fileName) {
        String stroka;
        int strokInput = 0, strokOutput = 0;
        try (FileReader reader = new FileReader(fileName); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                stroka = scanner.nextLine();
                strokInput++;
                csv  = stroka.split(", ");
                if (csv.length == 4) {
                    try {
                        person.push(csv[0]);
                        tovar.push(csv[1]);
                        shtuk.push(Integer.parseInt(csv[2]));
                        cena.push(Double.parseDouble(csv[3]));
                        strokOutput++;
                    } catch (Exception e) {
                        System.out.println("В строке " + strokInput + " проблема с числами.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return strokOutput;
    }

    public Map<String, Double> getGoods() {
        Double tCena;
        String tTovar;
        Map<String, Double> goods = new TreeMap<>();
        LinkedList <String> tempTovar = new LinkedList<>(tovar);
        LinkedList <Double> tempCena = new LinkedList<>(cena);
        for (int i=0; i < tovar.size(); i++) {
            tTovar = tempTovar.poll();
            tCena = tempCena.poll();
            Double add = goods.putIfAbsent(tTovar, tCena);
            if (add != null && tCena != null)  {
                goods.put(tTovar, tCena + add);
            }
        }
        return goods;
    }

    public Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers() {
        String tPerson;
        LinkedList <String> tempPerson = new LinkedList<>(person);
        LinkedList <Double> tempCena = new LinkedList<>(cena);
        LinkedList <Integer> tempShtuk = new LinkedList<>(shtuk);
        Map<String, AbstractMap.SimpleEntry<Double, Integer>> itog = new TreeMap<>();
        for (int i = 0; i<person.size(); i++) {
            Double tCena = tempCena.poll();
            Integer tShtuk = tempShtuk.poll();
            AbstractMap.SimpleEntry<Double, Integer> cenaShtuk = new AbstractMap.SimpleEntry<>(tCena, tShtuk);
            tPerson = tempPerson.poll();
            AbstractMap.SimpleEntry <Double, Integer> add = itog.putIfAbsent(tPerson, cenaShtuk);
            if (add != null && tCena != null && tShtuk != null) {
                cenaShtuk = new AbstractMap.SimpleEntry<>(tCena + add.getKey(), tShtuk + add.getValue());
                itog.put(tPerson, cenaShtuk);
            }
        }
        return itog;
    }

    public static void main(String[] args) {
        SalesInfo salesInfo = new SalesInfo();
        System.out.println(salesInfo.loadOrders("log.txt"));
        System.out.println(salesInfo.getGoods());
        System.out.println(salesInfo.getCustomers());
    }
}
