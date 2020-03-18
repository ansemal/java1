package ru.progwards.java1.lessons.sets;

import java.util.*;

public class ProductAnalytics {
    private List<Shop> shops;
    private List<Product> products;

    public ProductAnalytics(List<Product> products, List<Shop> shops) {
        this.products = products;
        this.shops = shops;
    }

    static class Product {      //- убрать статик
        private String code;

        public Product(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    static class Shop {                          // - убрать статик
        private List<Product> products;

        public Shop(List<Product> products) {
            this.products = products;
        }

        public List<Product> getProducts() {
            return products;
        }
    }

    public Set<Product> existInAll() {
        HashSet <Product> exist = new HashSet<>(products);
        for (Shop temp : shops) {
            exist.retainAll(temp.getProducts());
        }
   /*     String artikul;
        Iterator<Product> iterator = exist.iterator();
        while (iterator.hasNext()) {
            artikul = iterator.next().getCode();
            System.out.println(artikul);
        }*/
        return exist;
    }

    public Set<Product> existAtListInOne() {
        HashSet <Product> exist = new HashSet<>();
        for (Shop temp : shops) {
            exist.addAll(temp.getProducts());
        }
        return exist;
    }

    public Set<Product> notExistInShops() {
        HashSet <Product> exist = new HashSet<>(products);
        for (Shop temp : shops) {
            exist.removeAll(temp.getProducts());
        }
        return exist;
    }

    public Set<Product> existOnlyInOne() {
        HashSet <Product> exist = new HashSet<>();
        HashSet <Product> existTemp;
        for (Shop temp : shops) {
            existTemp = exist;
            exist.addAll(temp.getProducts());
            existTemp.retainAll(temp.getProducts());
            exist.removeAll(existTemp);
        }
        return exist;
    }

    public static void main(String[] args) {
        Product hleb = new Product("11");
        Product voda = new Product("22");
        Product krupa = new Product("33");
        List <Product> magnProd = new ArrayList<>();
        List <Product> lentaProd = new ArrayList<>();
        List <Product> products = new ArrayList<>();
        magnProd.add(krupa);
        magnProd.add(voda);
        lentaProd.add(voda);
        lentaProd.add(krupa);
        products.add(hleb);
        products.add(krupa);
        products.add(voda);
        Shop magnit = new Shop(magnProd);
        Shop lenta = new Shop(lentaProd);
        List <Shop> shops = new LinkedList<>();
        shops.add(magnit);
        shops.add(lenta);
        ProductAnalytics productAnalytics = new ProductAnalytics(products, shops);
        System.out.println(productAnalytics.notExistInShops());
        System.out.println(productAnalytics);
    }
}
