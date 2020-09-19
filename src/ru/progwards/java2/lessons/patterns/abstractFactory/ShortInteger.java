package ru.progwards.java2.lessons.patterns.abstractFactory;

public class ShortInteger extends AbsInteger {
      short val;


    public ShortInteger (short val) {
       super(val);
       this.val = val;
   }

    @Override
    public String toString () {
        return String.valueOf(val);
    }

}
