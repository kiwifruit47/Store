package org.citb408;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private List<Cashier> cashiers = null;
    private HashMap<Product, Integer> soldProducts = null;
    private HashMap<Product, Integer> deliveredProducts = null;
    private Inventory inventory = null;
    private List<Receipt> issuedReceipts = null;

    public Store(Inventory inventory) {
        this.cashiers = new ArrayList<Cashier>();
        this.soldProducts = new HashMap<>();
        this.deliveredProducts = new HashMap<>();
        this.inventory = inventory;
        this.issuedReceipts = new ArrayList<>();
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public HashMap<Product, Integer> getSoldProducts() {
        return soldProducts;
    }

    public HashMap<Product, Integer> getDeliveredProducts() {
        return deliveredProducts;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Receipt> getIssuedReceipts() {
        return issuedReceipts;
    }

    //adds delivered products to deliveredProducts(for reports) and availableProducts in Inventory(for functional purposes)
    public void deliverNewProducts(Product product, int amount) {
        if(deliveredProducts.containsKey(product)) {
            deliveredProducts.replace(product, deliveredProducts.get(product) + amount);
            inventory.getAvailableProducts().replace(product, inventory.getAvailableProducts().get(product) + amount);
        } else {
            deliveredProducts.put(product, amount);
            inventory.getAvailableProducts().put(product, amount);
        }
    }

    public BigDecimal calculateMonthlySalaryExpenses() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Cashier cashier : cashiers) {
            sum = sum.add(cashier.getSalary());
        }
        return sum;
    }

    public BigDecimal calculateDeliveryExpenses() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : deliveredProducts.entrySet()) {
            Product product = entry.getKey();
            Integer amount = entry.getValue();
            sum = sum.add(product.getPriceOnDelivery().multiply(BigDecimal.valueOf(amount)));
        }
        return sum;
    }

    @
    public BigDecimal calculateSales() {

    }

    public BigDecimal calculateProfit() {

    }
}
