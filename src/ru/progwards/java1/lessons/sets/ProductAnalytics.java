package ru.progwards.java1.lessons.sets;

import java.util.*;

public class ProductAnalytics {
    private List<Shop> shops;
    private List<Product> products;

    public ProductAnalytics(List<Product> products, List<Shop> shops) {
        this.products = products;
        this.shops = shops;
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
        HashSet <Product> existTemp = new HashSet<>();
        HashSet <Product> existDel = new HashSet<>();
        for (Shop temp : shops) {
            existTemp.addAll(exist);
            exist.addAll(temp.getProducts());
            existTemp.retainAll(temp.getProducts());
            exist.removeAll(existDel);
            exist.removeAll(existTemp);
            existDel.addAll(existTemp);
            existTemp.clear();
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
        magnProd.add(hleb);
        magnProd.add(krupa);
        lentaProd.add(hleb);
        lentaProd.add(voda);
        products.add(hleb);
        products.add(krupa);
        products.add(voda);
        Shop magnit = new Shop(magnProd);
        Shop lenta = new Shop(lentaProd);
        List <Shop> shops = new LinkedList<>();
        shops.add(magnit);
        shops.add(lenta);
        ProductAnalytics productAnalytics = new ProductAnalytics(products, shops);
        System.out.println(productAnalytics.existOnlyInOne());
    }
}
