package org.citb408;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private List<Delivery> deliveries = null;

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

    //todo: maybe fix time complexity; serialize Delivery objects in files by month (in the name of file) and deserialize for expense report
    //outer loop iterates through list of deliveries and inner one maps through key-value pairs in the products hashmap in Delivery
    public BigDecimal calculateMonthlyDeliveryExpenses() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Delivery delivery : deliveries) {
            if (delivery.getDate().getMonth().equals(LocalDate.now().getMonth()) &&
                    delivery.getDate().getYear() == LocalDate.now().getYear()) {
                for (Map.Entry<Product, Integer> entry : delivery.getProducts().entrySet()) {
                    Product product = entry.getKey();
                    int quantity = entry.getValue();
                    BigDecimal productCost = product.getPriceOnDelivery().multiply(BigDecimal.valueOf(quantity));
                    sum = sum.add(productCost);
                }
            }
        }
        return sum;
    }


    public BigDecimal calculateMonthlySales() {

    }

    public BigDecimal calculateProfit() {

    }
}
