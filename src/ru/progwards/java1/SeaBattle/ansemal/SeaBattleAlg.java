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

    static char [][]field;
    int hits = 0;
    int palub4 = 0;
    int palub3 = 0;
    int palub2 = 0;

    void init (SeaBattle seaBattle) {
        field = new char[seaBattle.getSizeX()][seaBattle.getSizeY()];
        for (int i =0; i<seaBattle.getSizeX(); i++){
            Arrays.fill(field[i], ' ');
        }
    }

    void fire (int x, int y) {
        field [y][x] = '*';
    }

    void destroy (int x, int y) {
        hits++;
        field [y][x] = 'X';
        for (int i = -1; i <= 1 ;i++) {
            for (int j = -1; j<= 1; j++) {
                if (x+i >= 0 && x+i < 10 && y+j >= 0 && y+j < 10) {
                    if (field [y+j][x+i] == ' ') {
                        field [y+j][x+i] = 'o';
                    }
                }
            }
        }
    }

    void hit (int x, int y) {
        hits++;
        field [y][x] = 'X';
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                if (x+i >= 0 && x+i < 10 && y+j >= 0 && y+j < 10 && field[y + j][x + i] == ' ') {
                    field[y + j][x + i] = 'o';
                }
            }
        }
    }

    void hitToDestr (SeaBattle seaBattle, int x, int y) {
        int palub = 2;
        hit (x, y);
        int i = x;
        i++;
        int j = y;
        while (j<y+4) {
            if (i >= 0 && i < 10 && j >= 0 && j < 10 && field[j][i] == ' ') {
                SeaBattle.FireResult fireResult = seaBattle.fire(i, j);
                if (fireResult == FireResult.MISS) {
                    fire(i, j);
                    if (i > x) i = x-1;
                    else if (i < x) {
                        j = y+1;
                        i = x;
                    }
                    else if (j > y) j = y-1;
                } else if (fireResult == FireResult.DESTROYED) {
                    destroy(i, j);
                    if (palub == 2) palub2++;
                    else if (palub == 3) palub3++;
                    else palub4++;
                    break;
                } else if (fireResult == FireResult.HIT) {
                    hit(i, j);
                    palub++;
                    if (i > x) {
                        i++;
                        if (i>9)
                            i = x-1;
                    }
                    else if (i < x) i--;
                    else if (j > y) {
                        j++;
                        if (j>9)
                            j = y-1;
                    }
                    else if (j < y) j--;
                }
            } else {
                if (i > x) i = x - 1;
                else if (i < x) {
                    j = y + 1;
                    i = x;
                } else if (j > y) j = y-1;
            }
        }
    }

    public void battleAlgorithm(SeaBattle seaBattle) {
        init(seaBattle);
        int i = 4;  // изменил с 4
        int j = 0;
        while (hits<20) {
            for (int x = 0; x<10; x++) {
                if (x==0) j = 0;
                if (x>0) {
                    j++;
                    if (j > i-1)
                        j = 0;
                }
                for (int y = j; y<10; y+=i){
                    if (field[y][x] == ' ') {
                        SeaBattle.FireResult fireResult = seaBattle.fire(x, y);
                        fire(x,y);
                        if (fireResult == FireResult.DESTROYED) {
                            destroy(x,y);
                            if (hits == 20)
                                return;
                        } else if (fireResult == FireResult.HIT) {
                            hitToDestr(seaBattle, x, y);
                            if (hits == 20)
                                return;
                        }
                    }
                }
                if (palub4 == 1 && palub3 == 2 && palub2 == 3)
                    i = 1;
                else if (palub4 == 1 && palub3 == 2)
                    i = 2;
                else if (palub4 == 1)
                    i = 3;
           }
        }
    }

    public static void main(String[] args) {
    	System.out.println("Sea battle");
    	double res = 0;
    	for (int i = 0; i<1; i++) {
            SeaBattle seaBattle = new SeaBattle();
            new SeaBattleAlg().battleAlgorithm(seaBattle);
            res += seaBattle.getResult();
        }
    	System.out.println(res/1000);
 //   	for (int i =0; i<10; i++) {
 //       System.out.println(Arrays.toString(field[i]));
 //       }
    }
}


