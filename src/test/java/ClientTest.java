import org.citb408.*;
import org.citb408.exceptions.ExpirationDateReachedException;
import org.citb408.exceptions.InsufficientAmountOfProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientTest {
    private Client client;
    private Store storeMock;
    private Inventory inventoryMock;
    private Product productMock;

    @BeforeEach
    public void setUp() {
        productMock = Mockito.mock(Product.class);
        storeMock = Mockito.mock(Store.class);
        inventoryMock = Mockito.mock(Inventory.class);
        client = new Client();
        Mockito.when(storeMock.getInventory()).thenReturn(inventoryMock);
    }

    @Test
    void addProductToCart_Success() throws InsufficientAmountOfProductException, ExpirationDateReachedException {
        when(inventoryMock.isProductAvailable(productMock, 2)).thenReturn(true);
        when(productMock.isProductExpired()).thenReturn(false);

        client.addProductToCart(storeMock, productMock, 2);

        assertTrue(client.getProductsInCart().containsKey(productMock));
        assertEquals(2, client.getProductsInCart().get(productMock));
    }

    @Test
    void addProductsToCart_ProductExpired() throws InsufficientAmountOfProductException, ExpirationDateReachedException {
        when(inventoryMock.isProductAvailable(productMock, 2)).thenReturn(true);
        when(productMock.isProductExpired()).thenReturn(true);

        client.addProductToCart(storeMock, productMock, 2);

        assertFalse(client.getProductsInCart().containsKey(productMock));

    }

    @Test
    void addProductsToCart_ProductUnavailable() throws InsufficientAmountOfProductException, ExpirationDateReachedException {
        when(inventoryMock.isProductAvailable(productMock, 2)).thenReturn(false);
        when(productMock.isProductExpired()).thenReturn(false);

        client.addProductToCart(storeMock, productMock, 2);

        assertFalse(client.getProductsInCart().containsKey(productMock));

    }
}
