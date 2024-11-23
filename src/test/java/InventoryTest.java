import org.citb408.Category;
import org.citb408.Inventory;
import org.citb408.Product;
import org.citb408.exceptions.InsufficientAmountOfProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashMap;

public class InventoryTest {
    private Product productMock;
    private Inventory inventoryMock;

    @BeforeEach
    public void setUp() {
        productMock = Mockito.mock(Product.class);
        HashMap<Category, BigDecimal> markup = new HashMap<>();
        markup.put(Category.FOOD, new BigDecimal("0.10"));
        markup.put(Category.MISCELLANEOUS, new BigDecimal("0.20"));
        inventoryMock = new Inventory(markup, 5,50);
    }

    //tests for isProductAvailable()
    @Test
    void isProductAvailable_Available() throws InsufficientAmountOfProductException {
        inventoryMock.getAvailableProducts().put(productMock, 10);
        assertTrue(inventoryMock.isProductAvailable(productMock, 5));
    }

    @Test
    void isProductAvailable_NotEnoughQuantity() throws InsufficientAmountOfProductException {
        inventoryMock.getAvailableProducts().put(productMock, 10);
        assertThrows(InsufficientAmountOfProductException.class, () -> inventoryMock.isProductAvailable(productMock, 15));
    }

    @Test
    void isProductAvailable_ProductNotInInventory() throws InsufficientAmountOfProductException {
        assertThrows(InsufficientAmountOfProductException.class, () -> inventoryMock.isProductAvailable(productMock, 0));
    }

    //tests for calculateBasePrice
    @Test
    void calculateBasePrice() {

    }
}
