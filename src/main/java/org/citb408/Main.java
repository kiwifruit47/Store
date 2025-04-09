package org.citb408;

import org.citb408.util.Deserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Category, BigDecimal> markup1 = new HashMap<>();
        markup1.put(Category.FOOD, new BigDecimal("0.10"));
        markup1.put(Category.MISCELLANEOUS, new BigDecimal("0.20"));

        Product bread = new Product("Dobrudzha bread", new BigDecimal("0.9"), Category.FOOD, LocalDate.of(2024, 1, 12));
        Product chicken = new Product("Chicken", new BigDecimal("4.3"), Category.FOOD, LocalDate.of(2026, 12, 1));
        Product eggs = new Product("Eggs", new BigDecimal("3.8"), Category.FOOD, LocalDate.of(2026, 12, 17));
        Product toothPaste = new Product("SuperFresh", new BigDecimal("5.0"), Category.MISCELLANEOUS, LocalDate.of(2028, 10, 1));
        Product toothBrush = new Product("ToothBrush", new BigDecimal("1.3"), Category.FOOD, LocalDate.of(2040, 12, 1));

        Store store1 = new Store(markup1, 5, 50);
        Register register1 = store1.addNewRegister();
        store1.addNewRegister();
        store1.hireNewCashier("Sasho", new BigDecimal("1400"), YearMonth.now());
        store1.hireNewCashier("Pesho", new BigDecimal("1300"), YearMonth.now());
        store1.receiveNewProductsDelivery(bread, 30);
        store1.receiveNewProductsDelivery(chicken, 10);
        store1.receiveNewProductsDelivery(eggs, 15);
        store1.receiveNewProductsDelivery(toothBrush, 8);
        store1.receiveNewProductsDelivery(toothPaste, 12);

        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();

        client1.addProductToCart(store1, bread, 2);
        client1.addProductToCart(store1, eggs, 1);

        client2.addProductToCart(store1, chicken, 1);
        client2.addProductToCart(store1, bread, 1);

        client3.addProductToCart(store1, toothBrush, 1);
        client3.addProductToCart(store1, toothPaste, 1);

        store1.getRegisters().getFirst().setCashier(store1.getCashiers().getFirst());

        store1.getRegisters().getFirst().issueReceipt(client1, new BigDecimal("50"));

        try {
            Deserializer.deserialize("receipts/receipt_2025-04-09T13:10:58.345995.ser");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }


    }
}