package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class OrderProcessor{
    int errorsCount;
    String startPath;
    LinkedList<Order> orders = new LinkedList<>();
    String [] csv;

    public OrderProcessor(String startPath) {
        this.startPath = startPath;
    }

    public int loadOrders(LocalDate start, LocalDate finish, String shopId) {
        errorsCount = 0;
        PathMatcher pmShopID;
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/???-??????-????.csv");

        // определение фильтра по shopID
        if (shopId == null)
            pmShopID = pathMatcher;
        else
            pmShopID = FileSystems.getDefault().getPathMatcher("glob:**/" + shopId + "-??????-????.csv");
        try {
            Files.walkFileTree(Paths.get(startPath), new SimpleFileVisitor<>() {
                double sum;

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(path)) {
                        sum = 0;
                        if (pmShopID.matches(path)) {
                            LocalDate dateFile = LocalDate.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());
                            // попадание в диапазон дат
                            if ((start == null || start.isBefore(dateFile) || start.equals(dateFile)) && (finish == null || finish.isAfter(dateFile) || finish.equals(dateFile))) {
                                try {
                                    // чтение заказов из файла и наполнение списка OrderItems
                                    List<OrderItem> orderItems = new LinkedList<>();
                                    List<String> orderItemStrings = Files.readAllLines(path);
                                    for (String orderItemString : orderItemStrings) {
                                        csv = orderItemString.split(", ");
                                        if (csv.length == 3) {
                                            try {
                                                OrderItem orderItem = new OrderItem();
                                                orderItem.price = Double.parseDouble(csv[2]);
                                                orderItem.count = Integer.parseInt(csv[1]);
                                                orderItem.goodsName = csv[0];
                                                orderItems.add(orderItem);
                                                sum += orderItem.price;
                                            } catch (Exception e) {
                                                throw new IOException("Проблемка в полях в формате файла " + path.getFileName());
                                            }
                                        } else throw new IOException("Проблемка в формате файла " + path.getFileName());
                                    }
                                    // наполнение списка orders
                                    Order order = new Order();
                                    order.shopId = path.getFileName().toString().substring(0, 3);
                                    order.orderId = path.getFileName().toString().substring(4, 10);
                                    order.customerId = path.getFileName().toString().substring(11, 15);
                                    order.datetime = LocalDateTime.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());
                                    order.sum = sum;
                                    Collections.sort(orderItems);
                                    order.items = orderItems;
                                    orders.push(order);
                                } catch (Exception e) {
                                    errorsCount++; //  - подсчет файлов с ошибкой в формате
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return errorsCount;
    }

    // - выдать список заказов в порядке обработки
    public List<Order> process(String shopId) {
        if (shopId==null) {
            Collections.sort(orders);
            return orders;
        }
        LinkedList<Order> ordersTemp = new LinkedList<>();
        for (Order order : orders) {
            if (order.shopId.equals(shopId)) {
                ordersTemp.push(order);
            }
        }
        Collections.sort(ordersTemp);
        return ordersTemp;
    }

    // - выдать информацию по объему продаж по магазинам
    public Map<String, Double> statisticsByShop() {
        TreeMap<String, Double> byShop = new TreeMap<>();
        for (Order order: orders) {
            Double was = byShop.putIfAbsent(order.shopId, order.sum);
            if (was != null) {
                byShop.put(order.shopId, order.sum + was );
            }
        }
        return byShop;
    }

    // - выдать информацию по объему продаж по товарам
    public Map<String, Double> statisticsByGoods() {
        TreeMap<String, Double> byGoods = new TreeMap<>();
        for (Order order: orders) {
            for (OrderItem orderItem: order.items) {
                Double was = byGoods.putIfAbsent(orderItem.goodsName, orderItem.price);
                if (was != null) {
                    byGoods.put(orderItem.goodsName, orderItem.price + was);
                }
            }
        }
        return byGoods;
    }

    // - выдать информацию по объему продаж по дням
    public Map<LocalDate, Double> statisticsByDay() {
        TreeMap<LocalDate, Double> byDay = new TreeMap<>();
        for (Order order: orders) {
            Double was = byDay.putIfAbsent(LocalDate.from(order.datetime), order.sum);
            if (was != null) {
                byDay.put(LocalDate.from(order.datetime), order.sum + was);
            }
        }
        return byDay;
    }

    public static void main(String[] args) {
        OrderProcessor orderProcessor = new OrderProcessor("./каталог");
//        orderProcessor.loadOrders(LocalDate.parse("2020-04-01"), LocalDate.parse("2020-04-03"), null);
        System.out.println(orderProcessor.loadOrders(null, null, null));
        orderProcessor.process(null);
        System.out.println(orderProcessor.orders.toString());
    }

}
