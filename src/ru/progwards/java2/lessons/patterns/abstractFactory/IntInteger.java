package ru.progwards.java2.lessons.patterns.abstractFactory;

public class IntInteger extends AbsInteger{

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
