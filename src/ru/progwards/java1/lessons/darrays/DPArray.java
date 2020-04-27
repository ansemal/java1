package ru.progwards.java1.lessons.darrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DPArray {
    ArrayList<Integer[]> array;
    int sizePage;
    int capacity;
    Random random;

    public DPArray(int blockSize) {
        array = new ArrayList<>();
        array.add(new Integer[blockSize]);
        capacity = blockSize;
        random = new Random();
    }

    public void add(int item) {
        Integer [] page = array.get(array.size()-1);
        if (sizePage >= random.nextInt(capacity/2)+capacity/2) {
            for (int j = sizePage; j < page.length; j++)
                page[j] = null;
            page = new Integer[page.length];
            array.add(page);
            sizePage = 0;
        }
        page[sizePage++] = item;
    }

    int get(int index) {
        int findIndex = 0;
        int j;
        for (Integer[] integers : array) {
            j = 0;
            while (j < capacity && integers[j] != null) {
                if (findIndex == index)
                    return integers[j];
                findIndex++;
                j++;
            }
        }
        return 0;
    }

    public void remove(int index) {
        int findIndex = 0;
        int j;
        for (int i = 0; i < array.size(); i++) {
            j = 0;
            while (j < capacity && array.get(i)[j] != null) {
                if (findIndex == index) {
                    // если второй элемент равен null - можем удалить весь массив
                    if (array.get(i)[1] == null) {
                        array.remove(i);
                        break;
                    }
                    // удаляем - сдвигаем все элементы массива
                    for (int z = j; z < capacity; z++) {
                        if (z == capacity-1) {
                            array.get(i)[z] = null;
                            break;
                        }
                        array.get(i)[z] = array.get(i)[z+1];
                    }
                }
                findIndex++;
                j++;
            }
        }
    }

    public int size() {
        int count = 0;
        int j;
        for (Integer[] integers : array) {
            j = 0;
            while (j < capacity && integers[j] != null) {
                count++;
                j++;
            }
        }
        return count;
    }

    public void add(int index, int item) {
        int findIndex = 0;
        int j;
        for (int i = 0; i < array.size(); i++) {
            j = 0;
            while (j < capacity && array.get(i)[j] != null) {
                if (findIndex == index) {
                    //  если в массиве нет места - заводим новый массив в списке и копируем последний элемент
                    if (array.get(i)[capacity - 1] != null) {
                        boolean arEmpty = false;
                        int nextFull = i+1;
                        // проверка - есть ли в следующем массиве место
                        while (!arEmpty) {
                            // не закончился ли список ?
                            if (nextFull <= array.size()-1) {
                                if (array.get(nextFull)[capacity - 1] != null)
                                    nextFull++;
                                else arEmpty = true;
                            } else {
                                // если закончился - добавляем массив с null
                                Integer [] page = new Integer[capacity];
                                Arrays.fill(page, null);
                                array.add(page);
                                arEmpty = true;
                            }
                        }
                        // сдвигаем значения
                        for (int z = nextFull; z >= i; z--) {
                            int start = z == i ? j : 0;
                            System.arraycopy(array.get(z), start, array.get(z), start + 1, capacity - start - 1);
                            if (z > i)
                               array.get(z)[0] = array.get(z-1)[capacity-1];
                        }
                    } else
                       // сдвигаем массив  и вставляем значение по индексу
                       System.arraycopy(array.get(i), j, array.get(i), j + 1, capacity - j - 1);
                    array.get(i)[j] = item;
                }
                findIndex++;
                j++;
            }
        }
    }
}
