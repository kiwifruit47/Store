package org.citb408;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Register {
    private final int id;
    private Cashier cashier;
    private static int counter = 1;

    public Register() {
        id = counter++;
    }

    public Register(Cashier cashier) {
        this.cashier = cashier;
        id = counter++;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public BigDecimal calculatePriceForAmount(Product product, int amount) {

    }

    public BigDecimal tagProductsFromCustomerCart (HashMap<Product, Integer> productsInCart) {
        for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()) {
            Product product = entry.getKey();
            Integer amount = entry.getValue();

        }
    }
}
