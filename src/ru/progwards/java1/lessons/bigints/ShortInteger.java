package ru.progwards.java1.lessons.bigints;

public class ShortInteger extends AbsInteger{
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
