package ru.progwards.java2.lessons.basetypes;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class BiDirList <T> implements Iterable<T>{
    class ListItem <T> {

        private T item;
        private ListItem<T> prev;
        private ListItem<T> next;

        ListItem (T item) {
            this.item = item;
        }

        void setPrev (ListItem<T> item) {
            prev = item;
        }

        void setNext (ListItem<T> item) {
            next = item;
        }
    }

    private ListItem<T> head;
    private ListItem<T> tail;
    private int size=0;

    public void addLast(T item) {
        ListItem<T> itNew = new ListItem<>(item);
        if (head == null) {
            head = itNew;
        } else {
            itNew.setPrev(tail);
            tail.setNext(itNew);
        }
        tail = itNew;
        size++;
    }

    public void addFirst(T item) {
        ListItem<T> itNew = new ListItem<>(item);
        if (head == null) {
            tail = itNew;
        } else {
            head.setPrev(itNew);
            itNew.setNext(head);
        }
        head = itNew;
        size++;
    }

    public void remove(T item) {
        for (Iterator<T> iter = this.getIterator(); iter.hasNext();) {
            if (iter.next() == item) {
                iter.remove();
                size--;
                return;
            }
        }
        System.out.println("Такого значения нет");
    }

    public T at(int i) {
        if (i<0) return null;
        int count = 0;
        for (Iterator<T> iter = this.getIterator(); iter.hasNext();) {
            if (count == i)
                return iter.next();
            else
                iter.next();
            count++;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public static<T> BiDirList<T> from(T[] array) {
        BiDirList <T> newList = new BiDirList<>();
        for (T t : array) newList.addLast(t);
        return newList;
    }


    public static<T> BiDirList<T> of(T...array) {
        BiDirList <T> newList = new BiDirList<>();
        for (T t : array) newList.addLast(t);
        return newList;
    }

    public void toArray(T[] array) {
        if (size <= array.length) {
            int index = 0;
            for (Iterator<T> iter = this.getIterator(); iter.hasNext(); ) {
                array[index] = iter.next();
                index++;
            }
            if (index < array.length)
                for (int i = index; i < array.length; i++)
                    array[index] = null;
        } else
            System.out.println("Количество элементов больше размера массива");
    }

    public Iterator<T> getIterator() {

        return new Iterator<>() {

            private ListItem<T> current;
            private ListItem<T> currentTemp = head;

            @Override
            public boolean hasNext() {
                current = currentTemp;
                return current != null;
            }

            @Override
            public T next() {
                currentTemp = current.next;
                return current.item;
            }

            @Override
            public void remove() {
                if (current == head) {
                    current.next.setPrev(null);
                    head = current.next;
                } else if (current == tail) {
                    current.prev.setNext(null);
                    tail = current.prev;
                } else {
                    current.prev.setNext(current.next);
                    current.next.setPrev(current.prev);
                }
                //throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Iterator<T> iterator() {
        return getIterator();
    }

    public static void main(String[] args) {
        BiDirList <Integer> biDirList = new BiDirList<>();
        biDirList.addLast(5);
        biDirList.addLast(6);
        biDirList.addFirst(4);
        biDirList.addFirst(3);
        biDirList.at(3);
        biDirList.remove(3);
        for (Iterator<Integer> iter = biDirList.getIterator(); iter.hasNext();) {
            System.out.println(iter.next());
        }
        biDirList.remove(2);
        biDirList.remove(6);
        Integer [] intmas = new Integer [] {1,2,3,4,5,6};
        BiDirList<Integer> list1 = BiDirList.from(intmas);
        BiDirList<String> list2 = BiDirList.of("Вася", "Петя", "Коля");
        Integer [] intmas1 = new Integer[6];
        list1.toArray(intmas1);
        System.out.println(Arrays.toString(intmas1));
//        Iterator<Integer> itr = biDirList.iterator();
        for (Integer item : biDirList) {
            System.out.println(item);
        }
    }
}
