package ru.appline.framework.utils;

/**
 * Класс для хронения элементов продукта
 */
public class Product {
    String name;
    String searchName;
    int price;
    int warranty;
    int priceWithWarranty;

    public Product(String searchName) {
        this.searchName = searchName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public int getPriceWithWarranty() {
        return priceWithWarranty;
    }

    public void setPriceWithWarranty(int priceWithWarranty) {
        this.priceWithWarranty = priceWithWarranty;
    }
}
