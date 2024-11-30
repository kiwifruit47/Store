package org.citb408;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private List<Cashier> cashiers;
    private List<Register> registers;
    private HashMap<Product, Integer> soldProducts;
    private HashMap<Product, Integer> deliveredProducts;
    private Inventory inventory;
    private List<Receipt> issuedReceipts;
    private List<Delivery> deliveries ;

    public Store(HashMap<Category, BigDecimal> markup, int numberOfDaysForPriceDecrease, int priceDecreasePercentage) {
        this.cashiers = new ArrayList<>();
        this.registers = new ArrayList<>();
        this.soldProducts = new HashMap<>();
        this.deliveredProducts = new HashMap<>();
        this.inventory = new Inventory(markup, numberOfDaysForPriceDecrease, priceDecreasePercentage);
        this.issuedReceipts = new ArrayList<>();
        this.deliveries = new ArrayList<>();
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

    public List<Register> getRegisters() {
        return registers;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    //todo: da si opravq pomiqta, koqto sum sgotvila s dostavkite - deliverNewProducts() trqbva da suzdava nova instanciq na Delivery, a metoda za razhodite da vzima spisuk s Delivery-ta
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

    //outer loop iterates through list of deliveries and inner one maps through key-value pairs in the products hashmap in Delivery
    public BigDecimal calculateMonthlyDeliveryExpenses(YearMonth yearMonth) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Delivery delivery : deliveries) {
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
