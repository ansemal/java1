package ru.progwards.java1.lessons.queues;

import java.util.PriorityQueue;

public class OrderQueue{
    PriorityQueue <Order> after20 = new PriorityQueue<>();
    PriorityQueue <Order> between10and20 = new PriorityQueue<>();
    PriorityQueue <Order> before10 = new PriorityQueue<>();

    public void add(Order order) {
        if (order.priority ==1) {
            after20.offer(order);
        } else if (order.priority ==2) {
            between10and20.offer(order);
        } else before10.offer(order);
    }

    public Order get() {
        if (! (after20.peek() == null)) {
            return after20.poll();
        } else if (! (between10and20.peek() == null)) {
            return between10and20.poll();
        } else return before10.poll();
    }

    public static void main(String[] args) {
        OrderQueue orderQueue = new OrderQueue();
        orderQueue.add(new Order(15000));
        orderQueue.add(new Order(8000));
        orderQueue.add(new Order(6000));
        orderQueue.add(new Order(80000));
        orderQueue.add(new Order(11000));
        orderQueue.add(new Order(90000));
        System.out.println(orderQueue.get());
        System.out.println(orderQueue.get());
        System.out.println(orderQueue.get());
        System.out.println(orderQueue.get());
        System.out.println(orderQueue.get());
        orderQueue.add(new Order(11000));
        orderQueue.add(new Order(80000));
        System.out.println(orderQueue.get());
        System.out.println(orderQueue.get());

    }
}
