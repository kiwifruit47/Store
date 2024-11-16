package org.citb408;

import java.time.LocalDate;
import java.util.HashMap;

public class Delivery {
    private HashMap<Product, Integer> products;
    private LocalDate date;

    public Delivery(HashMap<Product, Integer> products, LocalDate date) {
        this.products = products;
        this.date = date;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public LocalDate getDate() {
        return date;
    }
}
