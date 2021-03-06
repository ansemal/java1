package ru.progwards.java1.lessons.interfaces;

import java.util.Objects;

public class Animal implements FoodCompare, CompareWeight {
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

    public double getFood1kgPrice() {
        switch (this.getFoodKind()) {
            case HAY:     return 20.0;
            case CORN:    return 50.0;
            case UNKNOWN: return 0;
            default:      return 0;
        }
    }

    public double getFoodPrice() {
        return calculateFoodWeight() * getFood1kgPrice();
    }

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeigt) {
        Animal smth = (Animal)smthHasWeigt;
        if (Double.compare(this.getWeight(),smth.getWeight()) > 0)
            return CompareResult.GREATER;
        if (Double.compare(this.getWeight(),smth.getWeight()) < 0)
            return CompareResult.LESS;
        return CompareResult.EQUAL;
    }

    @Override
    public int compareFoodPrice(Animal animal) {
        return Double.compare(this.getFoodPrice(), animal.getFoodPrice());
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) return true;
        if (anObject == null || getClass() != anObject.getClass()) return false;
        Animal animal = (Animal) anObject;
        return Double.compare(animal.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight);
    }

    public static void main(String[] args) {
        Cow cow1 = new Cow(555);
        Duck duck1 = new Duck(11);
        Hamster hamster1 = new Hamster (11);
        System.out.println(cow1.toStringFull());
        System.out.println(duck1.toStringFull());
        System.out.println(hamster1.toStringFull());
        System.out.println(duck1.equals(hamster1));
        System.out.println(cow1.getFoodPrice());
        System.out.println(duck1.compareFoodPrice(hamster1));
    }
}
