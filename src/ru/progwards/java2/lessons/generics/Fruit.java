package ru.progwards.java2.lessons.generics;

public class Fruit {
    public float weight;

    public Fruit(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }
}

class Apple extends Fruit {

    public Apple () {
        super(1.0f);
    }
}

class Orange extends Fruit{

    public Orange() {
        super(1.5f);
    }
}
