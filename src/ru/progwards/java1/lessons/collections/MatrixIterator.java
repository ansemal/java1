package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class MatrixIterator<T> implements Iterator<T> {

    private T[][] array;
    private int i=0, j = 0;

    MatrixIterator(T[][] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        if (i >= array[j].length && j >= array.length-1) {
            i = 0;
            j = 0;
            return false;
        } else
            return true;
    }

    @Override
    public T next() {
        if (i < array[j].length) {
            i++;
            if (i == 1 && j != 0)
                i++;
            return array [j][i-1];
        }
        i = 0;
        j++;
        return array[j][i];
    }
}