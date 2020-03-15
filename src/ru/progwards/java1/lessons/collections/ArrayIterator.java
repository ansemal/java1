package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class ArrayIterator <T> implements Iterator<T> {

    private T[] array;
    private int i=0;

    ArrayIterator(T[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        if (i >= array.length) {
            i = 0;
            return false;
        } else
            return true;
    }

    @Override
    public T next() {
        i++;
        return array[i-1];
    }
}
