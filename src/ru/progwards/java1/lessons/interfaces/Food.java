package ru.progwards.java1.lessons.interfaces;

public class Food implements CompareWeight {
    private  int weight;

    Food (int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return  weight;
    }

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeigt) {
        Food smth = (Food)smthHasWeigt;
        if (Double.compare(this.getWeight(),smth.getWeight()) > 0)
            return CompareResult.GREATER;
        if (Double.compare(this.getWeight(),smth.getWeight()) < 0)
            return CompareResult.LESS;
        return CompareResult.EQUAL;
    }


}
