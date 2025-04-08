import org.citb408.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class StoreTest {
    private Store storeMock;
    private Product productMock;
    private Delivery delivery;
    private Map<Integer, Delivery> deliveriesMock;

    @BeforeEach
    public void setUp() {
        HashMap<Category, BigDecimal> markup = new HashMap<>();
        markup.put(Category.FOOD, new BigDecimal("0.10"));
        markup.put(Category.MISCELLANEOUS, new BigDecimal("0.20"));
        storeMock = new Store(markup, 5, 50);
    }

    @Test
    void calculateMonthlySalaryExpensesTest() {
        storeMock.hireNewCashier("Sasho", new BigDecimal("1800.00"), YearMonth.now());
        assertEquals(new BigDecimal("1800.00"), storeMock.calculateMonthlySalaryExpenses(YearMonth.now()));
    }

    @Test
    void calculateMonthlyDeliveryExpensesTest() {
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(productMock, 3);
        delivery = new Delivery(products, LocalDate.now());
        deliveriesMock = new HashMap<>();
        deliveriesMock.put(1, delivery);
        Mockito.when(productMock.getPriceOnDelivery()).thenReturn(new BigDecimal("1.00"));
    }
}
