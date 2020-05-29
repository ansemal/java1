package ru.progwards.java2.lessons.generics;

import java.util.ArrayList;

public class FruitBox<T extends Fruit> extends ArrayList <T> implements Comparable <FruitBox<T>>{
    private Fruit name;

    @Override
    public boolean add (T fruit) {
        // если коробка пустая - определяем, что в ней будет лежать по первому фрукту
        if (this.name == null)
            name = fruit;
        if (!name.getClass().equals(fruit.getClass())) {
            System.out.println("Невозможно положить в эту коробку - там другие фрукты");
            return false;
        }
        return super.add(fruit);
    }

    public float getWeight() {
        return this.size()*name.getWeight();
    }

    public void  moveTo (FruitBox<T> box){
        if (!name.getClass().equals(box.name.getClass())) {
            throw new UnsupportedOperationException("Невозможно пересыпать - фрукты в коробках разные");
        }
        box.addAll(this);
        this.clear();
        this.name = null;
    }

    @Override
    public int compareTo(FruitBox<T> o) {
        return Float.compare(this.getWeight(),o.getWeight());
    }

    public static void main(String[] args) {
        FruitBox<Fruit> box1 = new FruitBox<>();
        FruitBox<Fruit> box2 = new FruitBox<>();
        FruitBox<Fruit> box3 = new FruitBox<>();
        box1.add(new Apple());
        box1.add(new Apple());
        box2.add(new Apple());
        box2.add(new Apple());
        box2.add(new Apple());
        box2.add(new Orange());   // пробуем положить другой фрукт
        box3.add(new Orange());
        box3.add(new Orange());
        box3.add(new Orange());
        box3.add(new Orange());
        System.out.println("Вес 1-й коробки = " + box1.getWeight());
        System.out.println("Вес 3-й коробки = " + box3.getWeight());
        System.out.println(box1.compareTo(box3));
        box2.moveTo(box1);
        box2.add(new Orange());   // заполняем очищенную коробку другим видом фруктов
        try {
            box3.moveTo(box2);
            box2.moveTo(box1);     // пересыпаем разные фрукты
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
