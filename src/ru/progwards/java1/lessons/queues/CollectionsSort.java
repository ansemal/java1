package ru.progwards.java1.lessons.queues;

import java.util.*;

public class CollectionsSort {

    public static void mySort(Collection<Integer> data) {
        Integer temp;
        Integer [] a = new Integer [data.size()];
        data.toArray(a);
        data.clear();
        for (int i=0; i-1<a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
    //                может не понял, что надо было реализовать - возможен был и такой вариант, только перед этим надо всё удалить и немного изменить
   //                 Collections.swap((List <Integer>) data, i, j);
                }
            }
        }
        Collections.addAll(data,a);
    }

    public static void minSort(Collection<Integer> data) {
        Collection <Integer> dataTemp = new ArrayList<>();
        while (data.size() > 0) {
            dataTemp.add(Collections.min(data));
            data.remove(Collections.min(data));
        }
        data.addAll(dataTemp);
    }

    static void collSort(Collection<Integer> data) {
        Collections.sort((List <Integer>) data);
    }

    public static Collection<String> compareSort() {
        final int COUNT = 15000;

        // заполнение сразу по алфавиту, чтобы при равенстве времени сразу было выполнено условие - по алфавиту
        List <String> fast = new ArrayList<>();
        Collections.addAll(fast,  "collSort", "minSort", "mySort");

        // создание и заполнение списка случайными числами
        List <Integer> dataMy = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < COUNT; i++) {
            dataMy.add(rnd.nextInt());
        }

        // тестирование collSort и перемешивание исходного массива
        long start = System.currentTimeMillis();
        collSort(dataMy);
        long finish = System.currentTimeMillis() - start;
        Collections.shuffle(dataMy, rnd);

        // тестирование minSort и перемешивание исходного массива
        start = System.currentTimeMillis();
        minSort(dataMy);
        long finish1 = (System.currentTimeMillis() - start);
        Collections.shuffle(dataMy, rnd);

        // тестирование mySort
        start = System.currentTimeMillis();
        mySort(dataMy);
        long finish2 = System.currentTimeMillis() - start;

        // сортировка по производительности
        if (finish1 < finish)
            Collections.swap(fast, 0, 1);
        if (finish2 < finish)
            Collections.rotate(fast, 1);
        if (finish2 > finish && finish2 < finish1)
            Collections.swap(fast, 1, 2);
        return fast;
    }
}
