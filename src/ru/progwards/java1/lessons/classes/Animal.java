package ru.progwards.java1.lessons.classes;

public class Animal {
    double weight;
    enum AnimalKind {
        ANIMAL,
        COW,
        HAMSTER,
        DUCK
    }
    enum FoodKind {
        UNKNOWN,
        HAY,
        CORN
    }

    public Animal(double weight) {
        this.weight = weight;
    }

    public AnimalKind getKind() {
        return AnimalKind.ANIMAL;
    }

    public FoodKind getFoodKind() {
        return FoodKind.UNKNOWN;
    }

    public double getWeight() {
        return weight;
    }

    public double getFoodCoeff() {
        return 0.02;
    }

    public double calculateFoodWeight() {
        return getWeight()*getFoodCoeff();
    }

    public String toString() {
        return "I am " + getKind() + ", eat " + getFoodKind();
    }

    public String toStringFull() {
        return "I am " + getKind() + ", eat " + getFoodKind() + " " + calculateFoodWeight();
    }

    public static void main(String[] args) {
        Animal animal1 = new Animal(1000.50);
        Animal animal2 = new Animal(524.13);
        Animal animal3 = new Animal(25.25);
        System.out.println(animal1.toString() + "    " + animal1.toStringFull());
        System.out.println(animal2.toString() + "    " + animal2.toStringFull());
        System.out.println(animal3.toString() + "    " + animal3.toStringFull());
    }
}
