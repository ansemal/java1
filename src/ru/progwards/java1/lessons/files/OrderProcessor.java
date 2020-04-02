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
    }

    public int loadOrders(LocalDate start, LocalDate finish, String shopId) throws IOException {
        errorsCount = 0;
        PathMatcher pmShopID;
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/???-??????-????.csv");

        // определение фильтра по shopID
        if (shopId == null)
            pmShopID = pathMatcher;
        else
            pmShopID = FileSystems.getDefault().getPathMatcher("glob:**/" + shopId + "-??????-????.csv");

        Files.walkFileTree(Paths.get(startPath), new SimpleFileVisitor<>() {
            int parsC;
            double parsP;
            double sum;

            @Override
            public FileVisitResult visitFile (Path path, BasicFileAttributes attrs) {
                if (pathMatcher.matches(path)) {
                    if (pmShopID.matches(path)) {
                        LocalDate dateFile = LocalDate.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());
                        // попадание в диапазон дат
                        if ((start == null || start.isBefore(dateFile) || start.equals(dateFile)) && (finish == null || finish.isAfter(dateFile) || finish.equals(dateFile))) {
                            try {
                                // чтение заказов из файла и наполнение списка OrderItems
                                List<String> orderItemStrings = Files.readAllLines(path);
                                for (String orderItemString: orderItemStrings) {
                                    csv = orderItemString.split(", ");
                                    try {
                                        parsC = Integer.parseInt(csv[1]);
                                        parsP = Double.parseDouble(csv[2]);
                                        orderItems.add(new OrderItem(csv[0], parsC, parsP));
                                        sum += parsP;
                                    } catch (Exception e) {
                                        System.out.println("В одной из строк файла " + path.getFileName() + "проблема с числами");
                                    }
                                }
                                // наполнение списка orders
                                String shopIdTemp = path.getFileName().toString().substring(0,3);
                                String orderIdTTemp = path.getFileName().toString().substring(4,10);
                                String customerIdTemp = path.getFileName().toString().substring(11,15);
                                LocalDateTime dateTime = LocalDateTime.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());
                                Collections.sort(orderItems);
                                orders.push(new Order(shopIdTemp, orderIdTTemp, customerIdTemp, dateTime, orderItems, sum));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                } else
                    errorsCount++;  // подсчет файлов с ошибкой в формате
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
