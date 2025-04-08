import org.citb408.Category;
import org.citb408.Inventory;
import org.citb408.Product;
import org.citb408.exceptions.InsufficientAmountOfProductException;
import org.citb408.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class InventoryTest {
    private Product productMock;
    private Inventory inventoryMock;

    @BeforeEach
    public void setUp() {
        productMock = Mockito.mock(Product.class);
        HashMap<Category, BigDecimal> markup = new HashMap<>();
        markup.put(Category.FOOD, new BigDecimal("0.1"));
        markup.put(Category.MISCELLANEOUS, new BigDecimal("0.2"));
        inventoryMock = new Inventory(markup, 5,50);
    }

    //tests for isProductAvailable()
    @Test
    void isProductAvailable_Available() throws InsufficientAmountOfProductException {
        inventoryMock.getAvailableProducts().put(productMock, 10);
        assertTrue(inventoryMock.isProductAvailableInInventory(productMock, 5));
    }

    @Test
    void isProductAvailable_NotEnoughQuantity() throws InsufficientAmountOfProductException {
        inventoryMock.getAvailableProducts().put(productMock, 10);
        assertThrows(InsufficientAmountOfProductException.class, () -> inventoryMock.isProductAvailableInInventory(productMock, 15));
    }

    @Test
    void isProductAvailable_ProductNotInInventory() throws InsufficientAmountOfProductException {
        assertThrows(InsufficientAmountOfProductException.class, () -> inventoryMock.isProductAvailableInInventory(productMock, 0));
    }

    //tests for calculateBasePrice
    @Test
    void calculateBasePrice_Success() throws ProductNotFoundException {
        inventoryMock.getAvailableProducts().put(productMock, 10);
        Mockito.when(productMock.getCategory()).thenReturn(Category.FOOD);
        Mockito.when(productMock.getPriceOnDelivery()).thenReturn(BigDecimal.valueOf(5));
        assertEquals(inventoryMock.calculateBasePrice(productMock), BigDecimal.valueOf(5+5*0.1)); //0.1 comes from inventoryMock instance
    }

    @Test
    void calculateBasePrice_NoProduct() {
        assertThrows(ProductNotFoundException.class, () -> inventoryMock.calculateBasePrice(null));
    }

    //tests for isProductApproachingExpiryDate()
    @Test
    void isProductApproachingExpiryDate_True() {
        Mockito.when(productMock.getExpiryDate()).thenReturn(LocalDate.now().minusDays(1));
        assertTrue(inventoryMock.isProductApproachingExpiryDate(productMock));
    }

    @Test
    void isProductApproachingExpiryDate_False() {
        Mockito.when(productMock.getExpiryDate()).thenReturn(LocalDate.now().minusDays(6));
        assertFalse(inventoryMock.isProductApproachingExpiryDate(productMock));
    }

    //tests for calculateSellingPrice
    @Test
    void calculateSellingPrice_WithDiscount() throws ProductNotFoundException {
        inventoryMock.getAvailableProducts().put(productMock, 10);
        Mockito.when(productMock.getCategory()).thenReturn(Category.FOOD);
        Mockito.when(productMock.getPriceOnDelivery()).thenReturn(BigDecimal.valueOf(10));
        Mockito.when(productMock.getExpiryDate()).thenReturn(LocalDate.now().minusDays(1));
        BigDecimal expectedPrice = BigDecimal.valueOf(11.00).subtract(BigDecimal.valueOf(11.00).multiply(BigDecimal.valueOf(0.50)));
        assertEquals(inventoryMock.calculateSellingPrice(productMock), expectedPrice);
    }

    @Test
    void calculateSellingPrice_WithoutDiscount() throws ProductNotFoundException {
        inventoryMock.getAvailableProducts().put(productMock, 10);
        Mockito.when(productMock.getCategory()).thenReturn(Category.FOOD);
        Mockito.when(productMock.getPriceOnDelivery()).thenReturn(BigDecimal.valueOf(10));
        Mockito.when(productMock.getExpiryDate()).thenReturn(LocalDate.now().minusDays(6));
        assertEquals(inventoryMock.calculateSellingPrice(productMock), BigDecimal.valueOf(11.00));
    }

    @Test
    void calculateSellingPrice_Fail() throws ProductNotFoundException {
        assertThrows(ProductNotFoundException.class, () -> inventoryMock.calculateSellingPrice(productMock));
    }

    //tests for removeProductsFromInventory()
    @Test
    void removeProductsFromInventory_Success() {
        inventoryMock.getAvailableProducts().put(productMock, 10);
        HashMap<Product, Integer> productsToRemove = new HashMap<>();
        productsToRemove.put(productMock, 5);
        inventoryMock.removeProductsFromInventory(productsToRemove);
        assertEquals(5, inventoryMock.getAvailableProducts().get(productMock));
    }
}
