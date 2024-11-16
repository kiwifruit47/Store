package org.citb408;

import org.citb408.exceptions.InsufficientAmountOfMoneyException;
import org.citb408.util.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class Register {
    private final int id;
    private Store store;
    private Cashier cashier;
    private static int counter = 1;

    public Register() {
        id = counter++;
    }

    public Register(Cashier cashier, Store store) {
        this.cashier = cashier;
        this.store = store;
        id = counter++;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public int getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public BigDecimal calculatePriceForAmount(Product product, int amount) {
        return store.getInventory().calculateSellingPrice(product).multiply(BigDecimal.valueOf(amount));
    }

    public BigDecimal calculateTotalPriceForProductsInClientCart (Client client) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : client.getProductsInCart().entrySet()) {
            Product product = entry.getKey();
            Integer amount = entry.getValue();
            sum = sum.add(calculatePriceForAmount(product, amount));
        }
        return sum;
    }

    public void makeSale(Client client, BigDecimal money) throws InsufficientAmountOfMoneyException, IllegalStateException {
        if (client.getProductsInCart().isEmpty()) {
            throw new IllegalStateException("Client cart is empty.");
        }
        BigDecimal totalPrice = calculateTotalPriceForProductsInClientCart(client);
        if (money.compareTo(totalPrice) < 0) {
            throw new InsufficientAmountOfMoneyException("Insufficient amount of money! Sale cannot happen");
        } else {
            System.out.println("Payment successful!");
            store.getInventory().removeProductsFromInventory(client.getProductsInCart());
        }
    }



    public void issueReceipt(Client client, BigDecimal money) {
        try {
            makeSale(client, money);
            Receipt receipt = new Receipt(this.cashier, LocalDateTime.now(), client.getProductsInCart(), calculateTotalPriceForProductsInClientCart(client));
            System.out.println(receipt);
            try {
                Serializer.serialize("receipts/" + receipt.getReceiptId() + ".ser", receipt);
            } catch (IOException e) {
                System.out.println("Failed to serialize receipt." + e.getMessage());
            }
        } catch (InsufficientAmountOfMoneyException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

}
