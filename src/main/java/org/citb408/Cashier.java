package org.citb408;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.YearMonth;

public class Cashier implements Serializable {
    private final int id;
    private String name;
    private BigDecimal salary;
    private YearMonth startingMonth;
    private static int counter = 1;

    public Cashier(String name, BigDecimal salary, YearMonth startingMonth) {
        id = counter++;
        this.name = name;
        this.salary = salary;
        this.startingMonth = startingMonth;
    }

    private Cashier() {
        id = counter++;
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

    public YearMonth getStartingMonth() {
        return startingMonth;
    }
}
