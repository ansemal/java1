package ru.progwards.java1.lessons.files;

public class OrderItem implements Comparable <OrderItem>{
    public String googsName;  // - наименование товара
    public int count;  // - количество
    public double price;  // - цена за единицу

   public OrderItem() {
    }

    @Override
    public int compareTo(OrderItem o) {
        return this.googsName.compareTo(o.googsName);
    }
}
