package org.citb408;

import org.citb408.exceptions.ExpirationDateReachedException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product {
    private int id;
    private String name;
    private BigDecimal priceOnDelivery;
    private Category category;
    private LocalDate expiryDate;

    public Product(String name, BigDecimal priceOnDelivery, Category category, LocalDate expiryDate) {
        this.name = name;
        this.priceOnDelivery = priceOnDelivery;
        this.category = category;
        this.expiryDate = expiryDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriceOnDelivery(BigDecimal priceOnDelivery) {
        this.priceOnDelivery = priceOnDelivery;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPriceOnDelivery() {
        return priceOnDelivery;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isProductExpired() throws ExpirationDateReachedException {
        if (expiryDate.isBefore(LocalDate.now())) {
            throw new ExpirationDateReachedException("Product is expired! Can't be purchased!");
        }
        return false;
    }
}
