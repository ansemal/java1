package ru.progwards.java2.lessons.basetypes;

import java.util.Arrays;
import java.util.Iterator;

public class DoubleHashTable <K extends HashValue, V> {
    static class DHItem<K, V>{

        private V value;
        private K key;
        private boolean delete;

        DHItem(K key, V value) {
            this.key = key;
            this. value = value;
        }

        @Override
        public String toString() {
            return "DHItem{" +
                    "value=" + value +
                    ", key=" + key +
                    ", delete=" + delete +
                    '}';
        }

    }

    DHItem<K, V>[] table;
    int size = 0;
    static final double A =(Math.sqrt(5)-1)/2;

    DoubleHashTable () {
        table = new DHItem [101];
    }

    public int getHash1 (int uniKey) {
        return Math.abs(uniKey % table.length);
    }

    public int getHash2 (int uniKey) {
        double d = A*uniKey;
        return (int) (table.length*(d-Math.floor(d)));
    }

    // проверка на конец таблицы
    public int isEndTable (int index) {
        if (index == table.length - 1)   // проверка на конец таблицы
            index = 0;
        else
            index++;
        return index;
    }

    // вычисление ближайшего простого числа
    public int nearSimpleNum (int realSize) {
        int newSize = realSize*2;
        boolean simple = false;
        while (!simple) {
            if (newSize % 2 == 0)
                newSize++;
            else {
                int temp = (int) Math.round(Math.sqrt(newSize));
                for (int i = 3; i <= temp; i += 2) {
                    if (newSize % i == 0) {
                        newSize++;
                        break;
                    }
                }
                simple = true;
            }
        }
        return newSize;
    }


    // перестроение таблицы
    public void restructTable (DHItem<K, V> newItem) {
        DHItem<K, V>[] tableTemp = Arrays.copyOf(table, table.length);
        table = new DHItem[nearSimpleNum(table.length)];
        size = 0;
        for (DHItem<K, V> oldIt : tableTemp) {
            if (oldIt != null && !oldIt.delete)
                add(oldIt.key, oldIt.value);
        }
        add(newItem.key, newItem.value);
    }

    public void add(K key, V value) {
        boolean addOK = false;
        int collizCount = 0;
        int uniKey = key.getHash();
        int index = getHash1(uniKey);
        DHItem<K, V> newItem = new DHItem<>(key, value);
        while (!addOK) {
            // пробирование по первому хэшу
            if (table[index] == null || table[index].delete) {
                table[index] = newItem;
                addOK = true;
            } else {
                collizCount++;
                // пробирование по второму хэшу
                int index2 = (index + getHash2(uniKey)) % table.length;
                if (table[index2] == null || table[index2].delete) {
                    table[index2] = newItem;
                    addOK = true;
                } else {
                    // продолжаем линейное пробирование
                    index = isEndTable(index);
                    collizCount++;
                    if (collizCount > table.length / 10) {
                        // количество коллизий больше 10 % - перестраиваем таблицу
                        restructTable(newItem);
                        return;
                    }
                }
            }
        }
        size++;
    }

    public V get(K key) {
        int uniKey = key.getHash();
        int index = getHash1(uniKey);
        while (table[index] != null) {
            if (table[index].key.equals(key))
                return table[index].value;
            int index2 = (index + getHash2(uniKey)) % table.length;
            if (table[index2].key.equals(key))
                return table[index2].value;
            index = isEndTable(index);
        }
        System.out.println("Данное значение не найдено");
        return null;
    }

    public void remove(K key) throws Exception {
        int uniKey = key.getHash();
        int index = getHash1(uniKey);
        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                table[index].delete = true;
                size--;
                return;
            }
            int index2 = (index + getHash2(uniKey)) % table.length;
            if (table[index2].key.equals(key)) {
                table[index2].delete = true;
                size--;
                return;
            }
            index = isEndTable(index);
        }
        throw new Exception("Значение по ключу не найдено");
    }

    public void change(K key1, K key2) {
        int uniKey = key1.getHash();
        int index = getHash1(uniKey);
        V value = get(key1);
        add(key2, value);
        table[index].delete = true;
    }

    public int size() {
        return size;
    }

    public Iterator <DHItem<K,V>> getIterator() {
        return new Iterator<>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                if (index > table.length - 1)
                    return false;
                while (table[index] == null || table[index].delete) {
                    if (index < table.length-1)
                        index++;
                    else return false;
                }
                    return true;
            }

            @Override
            public DHItem<K, V> next() {
                DHItem<K, V> current = table[index];
                index++;
                return current;
            }
        };
    }

    public static void main(String[] args) {
        DoubleHashTable <Person, String> dHT = new DoubleHashTable<>();
        Person p1 = new Person("Василий", 35);
        dHT.add(p1, "Преподаватель");
        System.out.println(Arrays.toString(dHT.table));
        Person p2 = new Person("Петр", 56);
        dHT.add(p2, "Космонавт");
        System.out.println(Arrays.toString(dHT.table));
        Person p3 = new Person("Иван",25);
        dHT.add(p3, "Повар");
        System.out.println(Arrays.toString(dHT.table));
        Person p4 = new Person( "Андрей",44);
        dHT.add(p4, "Спортсмен");
        System.out.println(Arrays.toString(dHT.table));
        Person p5 = new Person( "Евгений",30);
        dHT.add(p5, "Бухгалтер");
        System.out.println(Arrays.toString(dHT.table));
        Person p6 = new Person( "Николай",45);
        dHT.add(p6,"Директор");
        System.out.println(Arrays.toString(dHT.table));
        System.out.println(dHT.size());
        System.out.println(dHT.table.length);
        try {
            dHT.remove(p3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(Arrays.toString(dHT.table));
        for (Iterator<DHItem<Person, String>> iter = dHT.getIterator(); iter.hasNext();) {
            System.out.println(iter.next());
        }
        Person p7 = new Person("Олег", 55);
        dHT.change(p1, p7);
        System.out.println(Arrays.toString(dHT.table));
        dHT.get(p5);





    }
}


// 1.    В качестве размера таблицы выбирать простое число, первоначальное значение 101                                           - сделано
// 2.    При количестве коллизий более 10% при одной серии пробирований - перестраивать таблицу, увеличивая размер                - сделано
// 3.    Стратегия роста - удвоение размера, но с учетом правила - размер таблицы простое число. Т.е. искать ближайшее простое    - сделано
// 4.    Ключи должны реализовывать интерфейс  interface HashValue {int getHash();}                                               - сделано
// 5.    Т.о. мы унифицируем ключи любого типа, приведя их к целочисленному.
//       Для строковых - выбрать что-то, из функций, представленных в лекции, для целого числа можно вернуть просто сам число.    - сделано
// 6.    Далее, когда реализуем 2 хэш функции, реализуем их для числовых значение, и тут взять - деление и умножение              - сделано
//        Методы
//       2.1 public void add(K key, V value) - добавить пару ключ-значение                                                        - сделано
//       2.2 public V get(K key) - получить значение по ключу                                                                     - сделано
//       2.3 public void remove(K key) - удалить элемент по ключу                                                                 - сделано
//       2.4 public void change(K key1, Key key2) - изменить значение ключа у элемента с key1 на key2                             - сделано
//       2.5 public int size() - получить количество элементов                                                                    - сделано
//       2.6 public Iterator<DoubleHashTable<K,V>> getIterator()                                                                  - сделано
