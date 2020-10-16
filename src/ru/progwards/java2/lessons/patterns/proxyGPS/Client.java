package ru.progwards.java2.lessons.patterns.proxyGPS;

public class Client {

    public static void main(String[] args) {
        SpeedGps speedGPS = new SpeedGps();
        FilterSpeedGps filterSpeedGPS = new FilterSpeedGps(speedGPS);
        filterSpeedGPS.trackToGpsPoint("NeskuchniyClean.gpx");
    }
}
