import org.citb408.*;
import org.citb408.exceptions.InsufficientAmountOfMoneyException;
import org.citb408.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashMap;

public class RegisterTest {
    private Product productMock;
    private Register registerMock;
    private Store storeMock;
    private Cashier cashierMock;
    private Inventory inventoryMock;
    private Client clientMock;

    @BeforeEach
    public void setup() {
        productMock = Mockito.mock(Product.class);
        storeMock = Mockito.mock(Store.class);
        inventoryMock = Mockito.mock(Inventory.class);
        cashierMock = Mockito.mock(Cashier.class);
        registerMock = new Register(cashierMock, storeMock);
    }

    //tests for calculatePriceForAmount()
    @Test
    void calculatePriceForAmount_Success() throws ProductNotFoundException {
        Mockito.when(storeMock.getInventory()).thenReturn(inventoryMock);
        Mockito.when(inventoryMock.calculateSellingPrice(productMock)).thenReturn(new BigDecimal("1.00"));
        assertEquals(registerMock.calculatePriceForAmount(productMock, 10), new BigDecimal("10.00"));
    }

    @Test
    void calculatePriceForAmount_Failure() throws ProductNotFoundException {
        Mockito.when(storeMock.getInventory()).thenReturn(inventoryMock);
        Mockito.when(inventoryMock.calculateSellingPrice(productMock))
                .thenThrow(new ProductNotFoundException("Product not found"));
        BigDecimal result = registerMock.calculatePriceForAmount(productMock, 10);
        assertEquals(BigDecimal.ZERO, result);
    }

    //tests for calculateTotalPriceForProductsInClientCart()
    @Test
    void calculateTotalPriceForProductsInClientCart_Success() throws ProductNotFoundException {
        Mockito.when(storeMock.getInventory()).thenReturn(inventoryMock);
        clientMock = Mockito.mock(Client.class);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(productMock, 3);
        Mockito.when(clientMock.getProductsInCart()).thenReturn(products);
        Mockito.when(inventoryMock.calculateSellingPrice(productMock)).thenReturn(new BigDecimal("1.00"));

        assertEquals(registerMock.calculateTotalPriceForProductsInClientCart(clientMock), new BigDecimal("3.00"));
    }

    //tests for makeSale()
    @Test
    void makeSale_Fail() throws InsufficientAmountOfMoneyException, IllegalStateException {
        clientMock = Mockito.mock(Client.class);
        HashMap<Product, Integer> products = new HashMap<>();
        Mockito.when(clientMock.getProductsInCart()).thenReturn(products);
        assertThrows(IllegalStateException.class, () -> registerMock.makeSale(clientMock, new BigDecimal("5.00")));
    }

    @Test
    void makeSale_Fail2() throws InsufficientAmountOfMoneyException, IllegalStateException, ProductNotFoundException {
        clientMock = Mockito.mock(Client.class);
        Mockito.when(storeMock.getInventory()).thenReturn(inventoryMock);
        Mockito.when(inventoryMock.calculateSellingPrice(productMock)).thenReturn(new BigDecimal("5.00"));
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(productMock, 3);
        Mockito.when(clientMock.getProductsInCart()).thenReturn(products);

        assertThrows(InsufficientAmountOfMoneyException.class, () -> registerMock.makeSale(clientMock, new BigDecimal("5.00")));
    }
}
