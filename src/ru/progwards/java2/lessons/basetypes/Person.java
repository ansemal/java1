package ru.progwards.java2.lessons.basetypes;

public class Person implements HashValue {
    public String name;
    public int age;

    Person (String name, int age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public String getKey() {
        return Integer.toString(age);
    }
}
