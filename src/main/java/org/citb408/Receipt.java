package org.citb408;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

public class Receipt implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String receiptId;
    private Cashier cashier;
    private LocalDateTime dateAndTimeOfIssuing;
    private HashMap<Product, Integer> products;
    private BigDecimal totalPrice;

    public Receipt(Cashier cashier, LocalDateTime dateAndTimeOfIssuing, HashMap<Product, Integer> products, BigDecimal totalPrice) {
        this.cashier = cashier;
        this.dateAndTimeOfIssuing = dateAndTimeOfIssuing;
        this.products = products;
        this.totalPrice = totalPrice;
        this.receiptId = "receipt" + "_" + dateAndTimeOfIssuing.toString();
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
        return "Receipt:" + "\n" +
                "cashierID: " + cashier.getId() + "\n" +
                "dateAndTimeOfIssuing: " + dateAndTimeOfIssuing.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)) + "\n" +
                "products" + readProducts() + "\n" +
                "totalPrice: " + totalPrice;
    }
}
