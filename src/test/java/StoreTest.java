import org.citb408.Cashier;
import org.citb408.Category;
import org.citb408.Product;
import org.citb408.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;

public class StoreTest {
    private Store storeMock;
    private Product productMock;

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
}
