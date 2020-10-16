package ru.progwards.java2.lessons.patterns.proxyGPS;

import java.util.ArrayList;

public class FilterSpeedGps implements Speed {
    private final SpeedGps speedGPS;
    double mathExpectation;                  // матожидание
    double dispersion;                       // дисперсия
    double sigma;                            //среднеквадратичное отклонение
    public ArrayList<GPS> gpspoints;
    int count = 0;

    FilterSpeedGps(SpeedGps speedGPS) {
        this.speedGPS = speedGPS;
    }

    @Override
    public void parse(String name) {
        gpspoints = SaxParseGpxXml.parsing(name);
    }

    @Override
    public double speed(GPS gps) {
        return speedGPS.speed(gps);
    }

    @Override
    // получение и обработка новой сгенерированной точки
    public void receiveGpsPoint(GPS gpsPoint) {
        boolean excess = false;
        double speedNow = speed(gpsPoint);

        // первые 50 точек отправляем без фильтрации, остальные фильтруются на выброс
        if (count <=50) {
            calcSigma(speedNow);
            speedGPS.lastGPS = gpsPoint;
            count++;
        } else
            excess = checkPoint(gpsPoint, speedNow);

        //Если точка в пределах - идет на соответствующую обработку или пропускается
        if ((!excess))
            result(speedNow);
        else
            System.out.println("произошел выброс - " + speedNow);
    }

    // проверка точки на выброс
    public boolean checkPoint (GPS gpsPoint, double speedNow) {
        boolean wasExcess = false;
        // если был выброс
        if ((!(speedNow > mathExpectation - 3 * sigma)) || (!(speedNow < mathExpectation + 3 * sigma)))
            wasExcess = true;

        calcSigma(speedNow);
        count++;
        speedGPS.lastGPS = gpsPoint;
        return wasExcess;
    }

    @Override
    public void trackToGpsPoint(String name) {
        parse(name);
        for (GPS point : gpspoints)
            receiveGpsPoint(point);
    }

    @Override
    // обработка точки - какие-то действия - прорисовка карты и т.п.
    public void result(double speedNow) {
        speedGPS.result(speedNow);
    }

    public void calcSigma (double speedNow) {
        // почему-то в одном из треков в двух точках скорость становилась infinity
        // поэтому ввел эту проверку
        if (Double.isInfinite(speedNow)) return;

        int countNext = count+1;

        // мат ожидание
        // рекур формула  (N-1/N)M + (1/N * x)
        mathExpectation = (count * mathExpectation / countNext) + (speedNow/ countNext);
//        System.out.printf("Матожидание " + "%.3f" + " м/с\n", mathExpectation);

        // дисперсия
        dispersion = (count * dispersion / countNext) + (mathExpectation - speedNow)*(mathExpectation-speedNow)/ countNext;
//        System.out.printf("Дисперсия " + "%.3f" + " м/с\n", dispersion);

        // средеквадр отклонение
        sigma = Math.sqrt(dispersion);
//        System.out.printf("Отклонение " + "%.3f" + " м/с\n" + "3 сигма " + "%.3f" + " м/с\n", sigma, 3 * sigma);

        count = countNext;
    }
}