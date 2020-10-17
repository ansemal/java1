package ru.progwards.java2.lessons.patterns.proxyGPS;

public class Client {

    public static void main(String[] args) {
        SpeedGps speedGPS = new SpeedGps();
        FilterSpeedGps filterSpeedGPS = new FilterSpeedGps(speedGPS);
//        filterSpeedGPS.trackToGpsPoint("Kitz3.gpx");
//        filterSpeedGPS.trackToGpsPoint("mayrhoff1.gpx");
        filterSpeedGPS.trackToGpsPoint("NeskuchniyClean.gpx");
//        filterSpeedGPS.trackToGpsPoint("mayrhoff1.gpx.MA");

    }
}
