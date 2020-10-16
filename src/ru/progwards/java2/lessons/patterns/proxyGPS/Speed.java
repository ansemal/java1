package ru.progwards.java2.lessons.patterns.proxyGPS;

public interface Speed {
    //парсинг gps-трекера в xml
    void parse(String name);
    //скорость между двумя точками
    double speed(GPS gps);
    //имитация получения точки при движении
    void receiveGpsPoint(GPS point);
    //"генерация" точек из трека
    void trackToGpsPoint(String name);
    //вывод (нанесение на карту, показатель скорости и т.п.)
    void result(double speedNow);
}
