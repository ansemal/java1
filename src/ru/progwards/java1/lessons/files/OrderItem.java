package ru.progwards.java1.lessons.files;

public class OrderItem implements Comparable <OrderItem>{
    public String goodsName;  // - наименование товара
    public int count;  // - количество
    public double price;  // - цена за единицу

   public OrderItem() {
    }

    @Override
    public String toString() {
        return "\n goodsName='" + goodsName + '\'' +
                ", count=" + count +
                ", price=" + price;
    }

    @Override
    public int compareTo(OrderItem o) {
        return this.goodsName.compareTo(o.goodsName);
    }
}
