import org.citb408.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {
    private Store store;
    private Inventory mockInventory;

    @BeforeEach
    public void setUp() {
        HashMap<Category, BigDecimal> markup1 = new HashMap<>();
        markup1.put(Category.FOOD, new BigDecimal("0.10"));

        store = new Store(markup1, 5, 50);

        mockInventory = Mockito.mock(Inventory.class);
    }

    @Test
    public void receiveNewProductsDeliveryTest() {
        Product product = Mockito.mock(Product.class);

        store.receiveNewProductsDelivery(product, 10);
        assertTrue(store.getInventory().getAvailableProducts().containsKey(product));
        assertEquals(store.getInventory().getAvailableProducts().get(product), 10);
    }

    @Test
    public void receiveNewProductsDeliveryTest_ThrowsIllegalArgumentException() {
        Product product = Mockito.mock(Product.class);

        assertThrows(IllegalArgumentException.class, () ->
                 store.receiveNewProductsDelivery(null, 10));
        assertThrows(IllegalArgumentException.class, () ->
                store.receiveNewProductsDelivery(product, 0));
    }

    @Test
    public void hireNewCashierTest_Success() {
        Cashier cashier = store.hireNewCashier("TestCashier", BigDecimal.valueOf(1600), YearMonth.now());
        assertTrue(store.getCashiers().contains(cashier));
    }

    @Test
    public void hireNewCashierTest_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> store.hireNewCashier(null, null, null));
    }

    @Test
    public void calculateMonthlySalaryExpensesTest() {
        store.hireNewCashier("TestCashier1", BigDecimal.valueOf(1600), YearMonth.now());
        store.hireNewCashier("TestCashier2", BigDecimal.valueOf(1600), YearMonth.now());

        assertEquals(BigDecimal.valueOf(3200), store.calculateMonthlySalaryExpenses(YearMonth.now()));
    }

    @Test
    public void calculateMonthlyDeliveryExpensesTest() {
        Product product1 = new Product("product1", BigDecimal.valueOf(10), Category.FOOD, LocalDate.of(2026,1,1));
        store.receiveNewProductsDelivery(product1, 5);
        store.receiveNewProductsDelivery(product1, 5);
        assertEquals(BigDecimal.valueOf(100), store.calculateMonthlyDeliveryExpenses(YearMonth.now()));
    }

    @Test
    public void calculateMonthlySalesTest() {
        Register registerMock1 = Mockito.mock(Register.class);
        Register registerMock2 = Mockito.mock(Register.class);

        HashMap<YearMonth, BigDecimal> sales = new HashMap<>();
        sales.put(YearMonth.now(), BigDecimal.valueOf(1000));

        Mockito.when(registerMock1.getMonthlySalesTotal()).thenReturn(sales);
        Mockito.when(registerMock2.getMonthlySalesTotal()).thenReturn(sales);

        store.getRegisters().add(registerMock1);
        store.getRegisters().add(registerMock2);

        assertEquals(BigDecimal.valueOf(2000), store.calculateMonthlySales(YearMonth.now()));
    }

    @Test
    public void calculateMonthlyProfitTest() {
        Register registerMock = Mockito.mock(Register.class);
        HashMap<YearMonth, BigDecimal> sales = new HashMap<>();
        sales.put(YearMonth.now(), BigDecimal.valueOf(10_000));
        Mockito.when(registerMock.getMonthlySalesTotal()).thenReturn(sales);
        store.getRegisters().add(registerMock);

        Cashier cashierMock = Mockito.mock(Cashier.class);
        Mockito.when(cashierMock.getSalary()).thenReturn(BigDecimal.valueOf(1000));
        Mockito.when(cashierMock.getStartingMonth()).thenReturn(YearMonth.now());
        store.getCashiers().add(cashierMock);

        Product productMock = Mockito.mock(Product.class);
        Mockito.when(productMock.getName()).thenReturn("product1");
        Mockito.when(productMock.getPriceOnDelivery()).thenReturn(BigDecimal.valueOf(1));

        Delivery deliveryMock = Mockito.mock(Delivery.class);
        Mockito.when(deliveryMock.getDate()).thenReturn(LocalDate.now());
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(productMock, 100);
        Mockito.when(deliveryMock.getProducts()).thenReturn(products);
        store.getDeliveries().put(1, deliveryMock);

        assertEquals(BigDecimal.valueOf(8900), store.calculateMonthlyProfit(YearMonth.now()));
    }

}
