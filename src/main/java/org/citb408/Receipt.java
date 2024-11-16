package org.citb408;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;
    private String receiptId;
    private Cashier cashier;
    private LocalDateTime dateAndTimeOfIssuing;
    private HashMap<Product, Integer> products;
    private BigDecimal totalPrice;
    private static int counter;

    public Receipt(Cashier cashier, LocalDateTime dateAndTimeOfIssuing, HashMap<Product, Integer> products, BigDecimal totalPrice) {
        this.cashier = cashier;
        this.dateAndTimeOfIssuing = dateAndTimeOfIssuing;
        this.products = products;
        this.totalPrice = totalPrice;
        this.receiptId = "receipt" + counter++ + "_" + dateAndTimeOfIssuing.toString();
    }
    private Receipt() {}

    public String getReceiptId() {
        return receiptId;
    }

    private StringBuilder readProducts() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            s.append("\n").append("Product: ").append(entry.getKey().getName()).append(" ");
            s.append("Amount: ").append(entry.getValue());
        }
        return s;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "cashierID=" + cashier.getId() +
                ", dateAndTimeOfIssuing=" + dateAndTimeOfIssuing +
                ", products=" + readProducts() +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
