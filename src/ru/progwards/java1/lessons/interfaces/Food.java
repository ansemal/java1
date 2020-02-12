package ru.progwards.java1.lessons.interfaces;

public class Food implements CompareWeight {
    public static CompareWeight ArraySort;
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

    public static void sort(CompareWeight[] a) {
//        CompareWeight rev;
        for (int i=0; i-1<a.length; i++)
            for (int j=i+1; j<a.length; j++)
                if (a[i].compareWeight(a[j]) == CompareResult.GREATER) {
                    ArraySort = a[i];
                    a[i] = a[j];
                    a[j] = ArraySort;
                }
    }

    public static void main(String[] args) {
        Food AAA = new Food(25);
        Food AA1 = new Food(255);
        Food AA2 = new Food(55);
        Food AA3 = new Food(2515);
        Food AA4 = new Food(235);
        CompareWeight [] a = {AAA,AA1,AA2,AA3,AA4};
        sort(a);

    }
}
