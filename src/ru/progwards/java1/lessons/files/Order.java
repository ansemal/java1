package ru.progwards.java1.lessons.files;

import java.time.LocalDateTime;
import java.util.List;

public class Order implements Comparable <Order> {
    public String shopId;  // - идентификатор магазина
    public String orderId;  // - идентификатор заказа
    public String customerId;  // - идентификатор покупателя
    public LocalDateTime datetime;  // - дата-время заказа (из атрибутов файла - дата последнего изменения)
    public List<OrderItem> items;  // - список позиций в заказе, отсортированный по наименованию товара
    public double sum;  // - сумма стоимости всех позиций в заказе

/*    public Order(String shopId, String orderId, String customerId, LocalDateTime datetime, List<OrderItem> items, double sum) {
        this.shopId = shopId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.datetime = datetime;
        this.items = items;
        this.sum = sum;
    }*/

    @Override
    public int compareTo(Order o) {
        return this.datetime.compareTo(o.datetime);
    }
}
