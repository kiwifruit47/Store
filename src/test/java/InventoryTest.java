import org.citb408.Inventory;
import org.citb408.Product;
import org.citb408.Store;
import org.citb408.exceptions.InsufficientAmountOfProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

public class InventoryTest {
    private Store storeMock;
    private Product productMock;
    private Inventory inventoryMock;

    @BeforeEach
    public void setUp() {
        storeMock = Mockito.mock(Store.class);
        productMock = Mockito.mock(Product.class);
        inventoryMock = Mockito.mock(Inventory.class);

        when(storeMock.getInventory()).thenReturn(inventoryMock);
    }

    @Test
    void isProductAvailable_Available() {
        HashMap<Product, Integer> availableProducts = new HashMap<>();
        availableProducts.put(productMock, 10);
        when(inventoryMock.getAvailableProducts()).thenReturn(availableProducts);
        try {
            System.out.println(inventoryMock.getAvailableProducts());
            System.out.println(inventoryMock.isProductAvailable(productMock, 5));
            assertTrue(inventoryMock.isProductAvailable(productMock, 10));

        } catch (InsufficientAmountOfProductException e) {
            fail("Exception should not be thrown");
        }
    }
}
