package org.citb408;

import java.math.BigDecimal;

public class Cashier {
    private final int id;
    private String name;
    private BigDecimal salary;
    private static int counter = 1;
    public Cashier(String name, BigDecimal salary) {
        id = counter++;
        this.name = name;
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}
