package ru.progwards.java2.lessons.classloader;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Profiler {
    public static int timeTemp = 0;   // - время работы вложенной секции
    private static Integer countVlozSec = 0;  // - счетчик работающих вложенных секций
    static boolean vlozInc;                      // вход в новую секцию?

    static TreeMap<String, StatisticInfo> profilMapResult = new TreeMap<>();
    static Map<String, LocalDateTime> rabota = new TreeMap<>();
    static Map <String, Integer> vlozTempTime = new HashMap<>();

    public static void enterSection(String name) {
        countVlozSec++;
        if (countVlozSec > 1 && !vlozInc) {    // если при открытых секциях после закрытия не всех снова пошёл вход
            for (var entry: vlozTempTime.entrySet()) {           // сохраняем время работы вложенных секций до этого
                entry.setValue(entry.getValue() + timeTemp);
            }
            timeTemp = 0;
        }
        vlozTempTime.put(name, 0);
        LocalDateTime start = LocalDateTime.now();
        rabota.put(name, start);
        vlozInc = true;
    }

    public static void exitSection(String name) {
        int fulltimeS;
        countVlozSec--;
        LocalDateTime start = rabota.get(name);
        LocalDateTime finish = LocalDateTime.now();
        fulltimeS = (int) Duration.between(start, finish).toMillis();
        int selftimeS = fulltimeS - timeTemp - vlozTempTime.get(name);
        vlozTempTime.remove(name);
        if (countVlozSec != 0) {
            timeTemp = fulltimeS;
        } else timeTemp = 0;

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
        for (int i = 0; i < 1; i++) {
            int j = (int) Math.pow(5, i);
        }
        exitSection("1");
    }
}
