package org.citb408;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private List<Cashier> cashiers;
    private List<Register> registers;
    private HashMap<Product, Integer> soldProducts;
    private Inventory inventory;
    private List<Receipt> issuedReceipts;
    private Map<Integer, Delivery> deliveries;
//    counter is for mapping deliveries
    private int counter = 1;

    public Store(HashMap<Category, BigDecimal> markup, int numberOfDaysForPriceDecrease, int priceDecreasePercentage) {
        this.cashiers = new ArrayList<>();
        this.registers = new ArrayList<>();
        this.soldProducts = new HashMap<>();
        this.inventory = new Inventory(markup, numberOfDaysForPriceDecrease, priceDecreasePercentage);
        this.issuedReceipts = new ArrayList<>();
        this.deliveries = new HashMap<>();
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public HashMap<Product, Integer> getSoldProducts() {
        return soldProducts;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Receipt> getIssuedReceipts() {
        return issuedReceipts;
    }

    public List<Register> getRegisters() {
        return registers;
    }

    public Map<Integer, Delivery> getDeliveries() {
        return deliveries;
    }

    //adds delivered products to deliveredProducts(for reports) and availableProducts in Inventory(for functional purposes)
    public void deliverNewProducts(Product product, int amount) {
        if (product == null || amount <= 0) {
            throw new IllegalArgumentException("Product cannot be null and amount must be positive.");
        }
        HashMap<Product, Integer> newProducts = new HashMap<>();
        newProducts.put(product, amount);
        deliveries.put(counter++, new Delivery(newProducts, LocalDate.now()));
    }

    public void hireNewCashier(String name, BigDecimal salary, YearMonth hireYearMonth) {
        Cashier cashier = new Cashier(name, salary, hireYearMonth);
        cashiers.add(cashier);
    }

    public void addNewRegister() {
        Register register = new Register(this);
        registers.add(register);
    }

    public BigDecimal calculateMonthlySalaryExpenses(YearMonth yearMonth) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Cashier cashier : cashiers) {
            if (cashier.getStartingMonth().equals(yearMonth) || cashier.getStartingMonth().isAfter(yearMonth)) {
                sum = sum.add(cashier.getSalary());
            }
        }
        return sum;
    }

    //outer loop iterates through map of deliveries and inner one maps through key-value pairs in the products hashmap in Delivery
    public BigDecimal calculateMonthlyDeliveryExpenses(YearMonth yearMonth) {
        BigDecimal sum = BigDecimal.ZERO;
        for(Delivery delivery : deliveries.values()) {
            if (delivery.getDate().getMonth().equals(yearMonth.getMonth()) &&
                    delivery.getDate().getYear() == yearMonth.getYear()) {
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

    public BigDecimal calculateMonthlySales(YearMonth yearMonth) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Register register : registers) {
            sum = sum.add(register.getMonthlySalesTotal().get(yearMonth));
        }
        return sum;
    }

    public BigDecimal calculateMonthlyProfit(YearMonth yearMonth) {
        return calculateMonthlySales(yearMonth).subtract(calculateMonthlyDeliveryExpenses(yearMonth).add(calculateMonthlySalaryExpenses(yearMonth)));
    }
}
