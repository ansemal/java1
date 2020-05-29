package ru.progwards.java2.lessons.generics;

import java.util.Arrays;

public class DynamicArray <T> {

    private T [] dinArr = (T[]) new Object[2];
    T[] tmpArr;

    public DynamicArray() {}

    public void add (T item) {
        // если последний элемент массива не пустой - увеличиваем массив в 2 раза и добавляем элемент
        if (dinArr[dinArr.length - 1] != null) {
            tmpArr = Arrays.copyOf(dinArr, dinArr.length*2);
            tmpArr[dinArr.length] = item;
            dinArr = Arrays.copyOf(tmpArr, tmpArr.length);
        } else {
            // ищем последний не null элемент и вставляем после него
            for (int i=dinArr.length-1; i>=0; i--) {
                if (dinArr[i] != null) {
                    dinArr[i + 1] = item;
                    break;
                }
                if (i == 0)
                    dinArr[i] = item;
            }
        }
    }

    public void insert (int pos, T item) {
        // если массив полный - увеличиваем массив в 2 раза и вставляем элемент
        if (dinArr[dinArr.length - 1] != null) {
            tmpArr = Arrays.copyOf(dinArr, dinArr.length * 2);
            System.arraycopy(tmpArr, pos, tmpArr, pos+1, tmpArr.length-1-pos);
            tmpArr[pos] = item;
            dinArr = Arrays.copyOf(tmpArr, tmpArr.length);
        } else {
            System.arraycopy(dinArr, pos, dinArr, pos+1, dinArr.length-1-pos);
            dinArr[pos] = item;
        }
    }

    public void remove(int pos) {
        System.arraycopy(dinArr, pos+1, dinArr, pos, dinArr.length-1-pos);
        dinArr[dinArr.length-1] = null;
        // если массив наполовину пустой - уменьшаем размер в 2 раза
        if (dinArr[dinArr.length/2] == null)
            dinArr = Arrays.copyOf(dinArr, dinArr.length/2);
    }

    public T get (int pos) {
        return dinArr[pos];
    }

    public int size () {
        return dinArr.length;
    }

    @Override
    public String toString() {
        return "DynamicArray{" +
                "dinArr=" + Arrays.toString(dinArr) +
                '}';
    }


    public static void main(String[] args) {
        DynamicArray<Integer> b = new DynamicArray<>();
        b.add(4);
        b.add(8);
        b.add(-48);
        b.add(0);
        b.add(15);
        System.out.println(b);
        DynamicArray<String> a = new DynamicArray<>();
        a.add("Меркурий");
        a.add("Венера");
        a.add("Марс");
        a.add("Юпитер");
        System.out.println(a);
        b.insert(3, 22);
        System.out.println(b);
        a.insert(2,"Земля");
        System.out.println(a);
        a.remove(0);
        System.out.println(a);
        b.remove(5);
        System.out.println(b);
        System.out.println(b.get(2));
    }
}
