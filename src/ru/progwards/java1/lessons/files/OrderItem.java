package ru.progwards.java1.lessons.files;

public class OrderItem implements Comparable <OrderItem>{
    public String googsName;  // - наименование товара
    public int count;  // - количество
    public double price;  // - цена за единицу

  //  public OrderItem(String googsName, int count, double price) {
   public OrderItem() {
        this.googsName = googsName;
        this.count = count;
        this.price = price;

    }

    @Override
    public int compareTo(OrderItem o) {
        return this.googsName.compareTo(o.googsName);
    }
}
