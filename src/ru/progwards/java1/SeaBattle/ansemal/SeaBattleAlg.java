package ru.progwards.java1.SeaBattle.ansemal;

import ru.progwards.java1.SeaBattle.SeaBattle;
import ru.progwards.java1.SeaBattle.SeaBattle.FireResult;

import java.util.Arrays;

public class SeaBattleAlg {
    // Тестовое поле создаётся конструктором
    //     SeaBattle seaBattle = new SeaBattle(true);
    //
    // Обычное поле создаётся конструктором по умолчанию:
    //     SeaBattle seaBattle = new SeaBattle();
    //     SeaBattle seaBattle = new SeaBattle(false);
    //
    // Посомтреть результаты стрельбы можно в любой момент,
    // выведя объект класса SeaBattle на консоль. Например так:
    //     System.out.println(seaBattle);
    //
    //
    // Вид тестового поля:
    //
    //           0 1 2 3 4 5 6 7 8 9    координата x
    //         0|.|.|.|.|.|.|.|X|.|.|
    //         1|.|.|.|.|.|X|.|.|.|.|
    //         2|X|X|.|.|.|.|.|.|.|.|
    //         3|.|.|.|.|.|.|.|X|X|X|
    //         4|.|.|.|.|X|.|.|.|.|.|
    //         5|.|.|.|.|X|.|.|Х|.|.|
    //         6|.|.|.|.|.|.|.|Х|.|X|
    //         7|X|.|X|.|.|.|.|Х|.|X|
    //         8|X|.|.|.|.|.|.|X|.|.|
    //         9|X|.|.|.|X|.|.|.|.|.|

    private static final int MINUS = 0b01;
    private static final int PLUS = 0b10;

    char [][]field;
    SeaBattle seaBattle;
    int hits;
    int direction;
    int countPalub;
    int palub4 = 0;
    int palub3 = 0;
    int palub2 = 0;
    int step = 4;

    void init (SeaBattle seaBattle) {
        this.seaBattle = seaBattle;
        hits = 0;

        field = new char[seaBattle.getSizeX()][seaBattle.getSizeY()];
        for (int i =0; i<seaBattle.getSizeX(); i++){
            Arrays.fill(field[i], ' ');
        }
    }

    void printField () {
        for (int y=0; y<seaBattle.getSizeY(); y++) {
            String str = "|";
            for (int x=0; x<seaBattle.getSizeX(); x++) {
                str += field[x][y] + "|";
            }
            System.out.println(str);
        }
        System.out.println("");
    }

    boolean killVertikal(int x, int y) {
 //       countPalub = 1;
        int i = 1;
        direction = PLUS | MINUS;
        boolean killed = false;
        do {
            if ((direction & PLUS) != 0)
                killed = checkHit(fire(x, y+i), PLUS);
            if ((direction & MINUS) != 0)
                killed = checkHit(fire(x, y-i), MINUS);
            i++;
        } while (direction != 0);
//        countPalub = i;
        return killed;
    }

    boolean killHorizontal (int x, int y) {
        int i = 1;
        direction = PLUS | MINUS;
        boolean killed = false;
        do {
            if ((direction & PLUS) != 0)
                killed = checkHit(fire(x + i, y), PLUS);
            if ((direction & MINUS) != 0)
                killed = checkHit(fire(x - i, y), MINUS);
            i++;
        } while (direction != 0);
//        countPalub += (i-1);
        return killed;
    }

    boolean checkHit (FireResult fireResult, int fireDirection) {
        switch (fireResult) {
            case DESTROYED:
                direction = 0;
                return true;
            case HIT:
                return  false;
            case MISS:
                direction &= ~fireDirection;
                return false;
        }
        return false;
    }

    void killShip(int x, int y) {
        boolean killed = killVertikal(x,y);
        if (!killed)
            killHorizontal(x,y);
        if (countPalub > 1)
            whoKilled();

    }

    void whoKilled () {
 //       System.out.println(countPalub);
        if (countPalub == 4)
            palub4++;
        else if (countPalub == 3)
            palub3++;
        else
            palub2++;
//        countPalub = countPalub == 3 ? palub4++ : countPalub == 2 ? palub3++ : palub2++;
        countPalub = 0;
        step = changeStep();

    }

    void markFire (int x, int y, FireResult fireResult) {
        field[x][y] = fireResult == FireResult.MISS ? '*' : 'X';
        if (fireResult == FireResult.DESTROYED)
            markKilled();
    }

    void markDot (int x, int y) {
        if (x<0 || y<0 || x>=seaBattle.getSizeX() || y>=seaBattle.getSizeY() || field[x][y] != ' ')
            return;
        field[x][y] = 'o';
    }

    void markHit(int x, int y) {
        markDot(x-1, y-1);
        markDot(x-1, y);
        markDot(x-1, y+1);
        markDot(x+1, y-1);
        markDot(x+1, y);
        markDot(x+1, y+1);
        markDot(x, y-1);
        markDot(x, y+1);
    }

    void markKilled () {
        for (int y = 0; y < seaBattle.getSizeX(); y++) {
            for (int x = 0; x < seaBattle.getSizeY(); x++) {
                if (field[x][y] == 'X')
                    markHit(x,y);
            }
        }
    }

    void countHit () {
            hits++;
    }

    FireResult fire (int x, int y) {
        if (x<0 || y<0 || x>=seaBattle.getSizeX() || y>=seaBattle.getSizeY() || hits>=20 || field[x][y] != ' ')
            return FireResult.MISS;

        FireResult fireResult = seaBattle.fire(x,y);
        markFire(x,y,fireResult);
        if (fireResult != SeaBattle.FireResult.MISS)
            countHit();
        if (fireResult == FireResult.HIT) {
            countPalub++;
//            System.out.println("файр хит и коунт " + countPalub);

        }

        if (fireResult == FireResult.DESTROYED) {
            countPalub++;
//            System.out.println("файр дестрой и каунт " + countPalub);
            killShip(x, y);
        }
//        printField();
        return fireResult;
    }

    void stepFire (int offset) {
        for (int y=0; y<seaBattle.getSizeY(); y++) {
            for (int x = y + offset; x < seaBattle.getSizeX(); x += 4)
                fireAndKill(x, y);
            for (int x = y - offset+2; x >=0; x -= 4)
                fireAndKill(x, y);
        }
    }

    void stepFire2 (int paluba) {
        if (paluba !=1)
            for (int y = 1; y < seaBattle.getSizeY(); y+=4) {
                for (int x = 1; x < seaBattle.getSizeX(); x += 4) {
                         miniField(x,y,paluba);
                }
            }
        else
            for (int y = 0; y < seaBattle.getSizeY(); y++) {
                for (int x = 0; x < seaBattle.getSizeX(); x ++) {
                    fireAndKill(x,y);
                }
            }
    }

    void  stepFire3 (int offset) {
        for (int y=0; y<seaBattle.getSizeY(); y++) {
            for (int x = y + offset; x < seaBattle.getSizeX(); x += step) {
                fireAndKill(x, y); // != FireResult.MISS)
//                    step = changeStep();
            }
            for (int x = y - offset+4; x >=0; x -= step) {
//                fireAndKill(x, y);
                fireAndKill(x, y); // != FireResult.MISS)
//                    step = changeStep();
            }
        }

    }

    int changeStep () {
        if (palub4 == 1 && palub3 == 2 && palub2 == 3)
            return 1;
        else if (palub4 == 1 && palub3 == 2)
            return 2;
        else // if (palub4 == 1)
            return 3;
    }

    void miniField (int x, int y, int paluba) {
        switch (paluba) {
            case 4:
                fireAndKill(x + 2, y - 1);
                fireAndKill(x + 1, y);
                fireAndKill(x - 1, y + 1);
                fireAndKill(x, y + 2);
                break;
            case 3:
                fireAndKill(x,y-1);
                fireAndKill(x+2,y+1);
                break;
            case 2:
                fireAndKill(x-1, y);
                fireAndKill(x,y+1);
                fireAndKill(x+1,y+2);

        }
    }


    // выстрел с добиванием корабля. Используется из основного алгоритма для избегания рекурсии
    FireResult fireAndKill(int x, int y) {
        FireResult result = fire(x, y);
        if (result == FireResult.HIT) {
            killShip(x, y);
        }
        return result;
    }

    void simpleFire () {
        for (int y = 0; y < seaBattle.getSizeX(); y++) {
            for (int x = 0; x < seaBattle.getSizeY(); x++) {
                fire(x,y);
            }
        }
    }

    void diagonal () {
//        stepFire3(3);
//        stepFire3(1);
//        stepFire3(0);
//        stepFire3(2);

//        stepFire2(4);
//        stepFire2(3);
//        stepFire2(2);
//        stepFire2(1);
        stepFire(3);
        stepFire(1);
        stepFire(0);
        stepFire(2);
    }

    public void battleAlgorithm(SeaBattle seaBattle) {
        init(seaBattle);
        diagonal();
    }

    static void fullTest () {
        double res = 0;
        for (int i = 0; i<1000; i++) {
            SeaBattle seaBattle = new SeaBattle();
            new SeaBattleAlg().battleAlgorithm(seaBattle);
            res += seaBattle.getResult();
        }
        System.out.println(res/1000);
    }

    static void oneTest () {
        SeaBattle seaBattle = new SeaBattle(true);
        new SeaBattleAlg().battleAlgorithm(seaBattle);
        System.out.println(seaBattle.getResult());
    }

    public static void main(String[] args) {
    	System.out.println("Sea battle");
    	fullTest();
//        oneTest();
 //   	for (int i =0; i<10; i++) {
 //       System.out.println(Arrays.toString(field[i]));
 //       }
    }
}


