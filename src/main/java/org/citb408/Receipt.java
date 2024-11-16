package org.citb408;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

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

    @Override
    public String toString() {
        return "Receipt{" +
                "cashier=" + cashier +
                ", dateAndTimeOfIssuing=" + dateAndTimeOfIssuing +
                ", products=" + products +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
