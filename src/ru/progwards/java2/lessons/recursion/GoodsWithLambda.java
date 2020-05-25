package ru.progwards.java2.lessons.recursion;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GoodsWithLambda {
    List <Goods> list;



    void setGoods(List<Goods> list) {
        this.list = list;
    }

    List<Goods> sortByName() {
        list.sort(Comparator.comparing(a -> a.name));
        return list;
    }

    List<Goods> sortByNumber() {
        list.sort((a,b) -> a.number.compareToIgnoreCase(b.number));
        return list;
    }

    List<Goods> sortByPartNumber() {
        list.sort((a,b) -> a.number.substring(0,3).compareToIgnoreCase(b.number.substring(0,3)));
        return list;
    }

    List<Goods> sortByAvailabilityAndNumber() {
        list.sort((a,b) -> a.available != b.available ? Integer.compare(a.available, b.available) : a.number.compareToIgnoreCase(b.number));
        return list;
    }

    List<Goods> expiredAfter(Instant date) {
        return list.stream().sorted(Comparator.comparing(x -> x.expired)).filter(a -> a.expired.isBefore(date)).collect(Collectors.toList());
    }

    List<Goods> сountLess(int count) {

        return list.stream().sorted(Comparator.comparingInt(a -> a.available)).takeWhile(a -> a.available < count).collect(Collectors.toList());
    }

    List<Goods> сountBetween(int count1, int count2) {
        return list.stream().sorted(Comparator.comparingInt(a -> a.available)).filter(a -> a.available > count1 && a.available < count2).collect(Collectors.toList());
//        return list.stream().sorted(Comparator.comparingInt(a -> a.available)).dropWhile(a -> {System.out.println(a); return a.available > count1;}).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        GoodsWithLambda gWL = new GoodsWithLambda();
        Goods good1 = new Goods("Колбаса", "a15356g8", 74, 400.0, Instant.parse("2020-06-07T00:00:00.00Z"));
        Goods good2 = new Goods("Сыр", "B55016g8", 68, 600.0, Instant.parse("2020-06-05T00:00:00.00Z"));
        Goods good3 = new Goods("Творог", "A1C016g8", 256, 200.0, Instant.parse("2020-05-31T00:00:00.00Z"));
        Goods good4 = new Goods("Молоко", "A15676g8", 74, 50.0, Instant.parse("2020-05-30T00:00:00.00Z"));
        Goods good5 = new Goods("Йогурт", "b11016g8", 104, 100.0, Instant.parse("2020-05-28T00:00:00.00Z"));
        List <Goods> listGoods = new ArrayList<Goods>(List.of(good1,good2,good3,good4,good5));
        gWL.setGoods(listGoods);
//        System.out.println(gWL.sortByName());
//        System.out.println(gWL.sortByNumber());
//        System.out.println(gWL.sortByPartNumber());
//        System.out.println(gWL.sortByAvailabilityAndNumber());
//        System.out.println(gWL.expiredAfter(Instant.parse("2020-05-31T00:00:00.00Z")));
//        System.out.println(gWL.сountLess(100));
        System.out.println(gWL.сountBetween(80,200));


    }
}
