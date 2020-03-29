package ru.progwards.java1.lessons.datetime;

public class StatisticInfo {
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
