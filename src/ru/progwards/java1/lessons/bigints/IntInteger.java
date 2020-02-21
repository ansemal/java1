package ru.progwards.java1.lessons.bigints;

public class IntInteger extends AbsInteger {

    int val;


    public IntInteger (int val) {
       super(val);
       this.val = val;
   }

    @Override
    public String toString () {
        return String.valueOf(val);
    }

}
