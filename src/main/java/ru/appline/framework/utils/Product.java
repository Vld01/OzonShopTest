package ru.appline.framework.utils;

import java.util.Objects;

/**
 * Класс для хронения элементов продукта
 */
public class Product implements Comparable<Product>{
    private String name;
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public int compareTo(Product o) {
        return this.getPrice() - o.getPrice();
    }
}
