package ru.progwards.java1.lessons.datetime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Profiler {
    private static int timeVloz = 0;   // - сумма времени вложенных блоков секций в разный момент
    private static int timeTemp = 0;   // - время работы вложенной секции
    private static Integer countVlozSec = 0;  // - счетчик работающих вложенных секций
    static boolean vlozInc;                      // вход в новую секцию?

    static TreeMap<String, StatisticInfo> profilMapResult = new TreeMap<>();
    static Map<String, LocalDateTime> rabota = new TreeMap<>();

    public static class StatisticInfo{
        public String sectionName; // - имя секции
        public int fullTime; // - полное время выполнения секции в миллисекундах.
        public int selfTime; // - чистое время выполнения секции в миллисекундах. Для вложенных секций, из времени выполнения внешней секции нужно вычесть времена выполнения вложенных секций.
        public int count;

        @Override
        public String toString() {
            return "StatisticInfo{" +
                    "sectionName='" + sectionName + '\'' +
                    ", fullTime=" + fullTime +
                    ", selfTime=" + selfTime +
                    ", count=" + count +
                    '}';
        }

        public StatisticInfo (String sectionName, int fullTime, int selfTime, int count) {
            this.sectionName = sectionName;
            this.fullTime = fullTime;
            this.selfTime = selfTime;
            this.count = count;
        }
    }

    public static void enterSection(String name) {
        countVlozSec++;
        if (countVlozSec > 1 && !vlozInc) {    // если при открытых секциях после закрытия не всех снова пошёл вход
            timeVloz = timeTemp;              // сохраняем время работы вложенных секций до этого
            timeTemp = 0;
        }
        LocalDateTime start = LocalDateTime.now();
        rabota.put(name, start);
        vlozInc = true;
    }

    public static void exitSection(String name) {
        int fulltimeS;
        countVlozSec--;
        LocalDateTime start = rabota.get(name);
        LocalDateTime finish = LocalDateTime.now();
        fulltimeS = (int) Duration.between(start, finish).toMillis();//.toMillis();
        int selftimeS = fulltimeS - timeTemp;
        if (countVlozSec != 0 && timeVloz != 0) {
            timeTemp = fulltimeS + timeVloz;
            timeVloz = 0;
        }
        else if (countVlozSec != 0) {
            timeTemp = fulltimeS;
        }

        StatisticInfo stillNot = profilMapResult.putIfAbsent(name, new StatisticInfo(name, fulltimeS, selftimeS, 1));
        if (stillNot != null) {
            profilMapResult.get(name).fullTime += fulltimeS;
            profilMapResult.get(name).selfTime += selftimeS;
            profilMapResult.get(name).count++;
        }
        vlozInc = false;
    }

    public static List<StatisticInfo> getStatisticInfo() {
        ArrayList<StatisticInfo> itog = new ArrayList<>();
        for (var entry: profilMapResult.entrySet())
        itog.add(entry.getValue());
        return itog;
        }

    public static void main(String[] args) {
        enterSection("1");
        for (int i=0; i<3000000; i++) {
            int j = (int)Math.pow(5,i);
        }
        enterSection("2");
        for (int i=0; i<3000000; i++) {
            int j = (int)Math.pow(2,i);
        }
        exitSection("2");

       enterSection("2");
        for (int i=0; i<3000000; i++) {
            int j = (int)Math.pow(2,i);
        }
        exitSection("2");

        exitSection("1");


        System.out.println(getStatisticInfo());
    }
}
