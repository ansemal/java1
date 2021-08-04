package ru.progwards.java2.lessons.synchro;

public class Philosopher implements Runnable {
    String name;
    Fork right;        // - вилка справа
    Fork left;         // - вилка слева
    long reflectTime;  // - время, которое философ размышляет в мс
    long eatTime;      // - время, которое философ ест в мс
    long reflectSum;   // - суммарное время, которое философ размышлял в мс
    long eatSum;       // - суммарное время, которое философ ел в мс

    Philosopher (String name, Fork right, Fork left, long reflectTime, long eatTime) {
        this.name = name;
        this.right = right;
        this.left = left;
        this.reflectTime = reflectTime;
        this.eatTime = eatTime;
    }

    //Размышление. Выводит "размышляет "+ name на консоль с периодичностью 0.5 сек
    void reflect() {
        long start = System.currentTimeMillis();
        long reflecttTimeNow = 0;
        while (reflecttTimeNow < reflectTime) {
                System.out.println("Размышляет " + name);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reflecttTimeNow=System.currentTimeMillis()-start;
            }
        reflectSum+=reflecttTimeNow;
    }

    //  Ест. Выводит "ест "+ name на консоль с периодичностью 0.5 сек
    void eat() {
        long start = System.currentTimeMillis();
        long eatTimeNow = 0;
        while (eatTimeNow < eatTime) {
            System.out.println("Ест " + name);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eatTimeNow=System.currentTimeMillis()-start;
        }
        eatSum+=eatTimeNow;
    }

    @Override
    public void run() {
        boolean canEat = false;   // может ли есть?
        while (Simposion.talkContinue) {     // пока разговор не окончен
            while (!canEat) {     // пока не может - пытается
                try {
                    // входит под семафор максимум одновременно возможных едоков
                    Simposion.semaphore.acquire();
                    // пробует взять правую вилку
                    canEat = right.isFree().compareAndSet(true, false);
                    if (canEat) {
                        // если получилось - пробует взять левую вилку
                        canEat = left.isFree().compareAndSet(true, false);
                        // если получилось взять обе вилки
                        if (canEat) {
                            eat();                   // ест
                            left.isFree().set(true); // освобождает левую
                        }
                        right.isFree().set(true);    // освобождает правую в любом случае
                    }
                    Simposion.semaphore.release();  // выход из-под семафора
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            reflect();
            canEat = false;
        }
    }
}
