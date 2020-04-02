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
    List<OrderItem> orderItems = new LinkedList<>();
    LinkedList<Order> orders = new LinkedList<>();
    String [] csv;

    public OrderProcessor(String startPath) {
        this.startPath = startPath;
        System.out.println("startPath " + startPath);
    }

    public int loadOrders(LocalDate start, LocalDate finish, String shopId) throws IOException {
        System.out.println("start " + start.toString());
        System.out.println("finish " + finish.toString());
        System.out.println("shopID " + shopId );
        errorsCount = 0;
        PathMatcher pmShopID;
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/???-??????-????.csv");

        // определение фильтра по shopID
        if (shopId == null)
            pmShopID = pathMatcher;
        else
            pmShopID = FileSystems.getDefault().getPathMatcher("glob:**/" + shopId + "-??????-????.csv");

        Files.walkFileTree(Paths.get(startPath), new SimpleFileVisitor<>() {
            double sum;

            @Override
            public FileVisitResult visitFile (Path path, BasicFileAttributes attrs) {
                if (pathMatcher.matches(path)) {
                    if (pmShopID.matches(path)) {
                        System.out.println("зашёл в pmShopID");
                        LocalDate dateFile = LocalDate.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());
                        // попадание в диапазон дат
                        if ((start == null || start.isBefore(dateFile) || start.equals(dateFile)) && (finish == null || finish.isAfter(dateFile) || finish.equals(dateFile))) {
                            try {
                                // чтение заказов из файла и наполнение списка OrderItems
                                List<String> orderItemStrings = Files.readAllLines(path);
                                for (String orderItemString: orderItemStrings) {
                                    csv = orderItemString.split(", ");
                                    System.out.println("csv " + Arrays.toString(csv));
                                    try {
                                        OrderItem orderItem = new OrderItem();
                                        orderItem.price = Double.parseDouble(csv[2]);
                                        System.out.println("orderItem.price " + orderItem.price);
                                        orderItem.count = Integer.parseInt(csv[1]);
                                        System.out.println("orderItem.count " + orderItem.count);
                                        orderItem.googsName = csv[0];
                                        System.out.println("orderItem.googsName " + orderItem.googsName);
                                        orderItems.add(orderItem);
                                        sum += orderItem.price;
                                        System.out.println("sum " + sum);
                                    } catch (Exception e) {
                                        System.out.println("В одной из строк файла " + path.getFileName() + "проблема с числами");
                                    }
                                }
                                // наполнение списка orders
                                Order order = new Order();
                                order.shopId = path.getFileName().toString().substring(0,3);
                                System.out.println("order.shopId " + order.shopId);
                                order.orderId = path.getFileName().toString().substring(4,10);
                                System.out.println("order.orderId " + order.orderId);
                                order.customerId = path.getFileName().toString().substring(11,15);
                                System.out.println("order.customerId " + order.customerId);
                                order.datetime = LocalDateTime.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());
                                System.out.println("order.datetime " + order.datetime.toString());
                                order.sum = sum;
                                System.out.println("sum " + sum);
                                Collections.sort(orderItems);
                                order.items = orderItems;
                                orders.push(order);
                                System.out.println("положено в orders");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                } else
                    errorsCount++;  // подсчет файлов с ошибкой в формате
                System.out.println("errorsCount " + errorsCount);
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitFileFailed (Path file, IOException e) {
                return FileVisitResult.CONTINUE;
            }
        });
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
                Double was = byGoods.putIfAbsent(orderItem.googsName, orderItem.price);
                if (was != null) {
                    byGoods.put(orderItem.googsName, orderItem.price + was);
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
        try {
            orderProcessor.loadOrders(LocalDate.parse("2020-04-01"), LocalDate.parse("2020-04-03"), null);
            System.out.println(orderProcessor.orders.toString());
        } catch (Exception e) {
            System.out.println("Проблемка" + e.getMessage());
        }
    }

}
