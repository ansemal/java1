package ru.progwards.java2.lessons.patterns.proxyGPS;

import java.util.ArrayList;

public class SpeedGps implements Speed{
    public ArrayList<GPS> gpspoints;
    final static double EARTHRADIUS = 6371000; //радиус земли в метрах
    GPS lastGPS;                               // последняя точка gps
    double lastSpeed;

    @Override
    public void parse(String name) {
        gpspoints = SaxParseGpxXml.parsing(name);
    }

    // расстояние между двумя точками
    public double distBetween(GPS gps1, GPS gps2) {
        double dLat = Math.toRadians(gps2.lat - gps1.lat);
        double dLon = Math.toRadians(gps2.lon - gps1.lon);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(gps1.lat))
                * Math.cos(Math.toRadians(gps2.lat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return c * EARTHRADIUS;
    }

    // вычисление скорости
//    @Override
//    public double speed (GPS gpsPoint) {
//        return (lastGPS!=null) ? distBetween(lastGPS, gpsPoint)/(gpsPoint.time-lastGPS.time): 0;
//    }

    // вычисление ускорения
    @Override
    public double acceleration (GPS gpsPoint) {
        double speed = (lastGPS!=null) ? distBetween(lastGPS, gpsPoint)/ (gpsPoint.time-lastGPS.time): 0;
        double acceleration = (lastGPS!=null) ? (speed - lastSpeed)/ (gpsPoint.time-lastGPS.time) : 0;
        lastSpeed = speed;
        return acceleration;
    }

    @Override
    public void receiveGpsPoint(GPS gpsPoint) {
        double acceleration = acceleration(gpsPoint);
        lastGPS = gpsPoint;
        result(acceleration);
    }

    @Override
    public void trackToGpsPoint(String name) {
        parse(name);
        for (GPS point : gpspoints)
            receiveGpsPoint(point);
    }

    @Override
    public void result(double acceleration) {
        System.out.printf("Точка нанесена на карту, Текущее ускорение - %.2f м/с2\n", acceleration);
    }

    public static void main(String[] args) {
        SpeedGps speedGPS = new SpeedGps();
        speedGPS.trackToGpsPoint("NeskuchniyClean.gpx");
    }
}
