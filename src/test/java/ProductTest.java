import org.citb408.*;
import org.citb408.exceptions.ExpirationDateReachedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    private Product product;

    @Test
    void isProductExpired_NotExpired() {
        product = new Product("bread", BigDecimal.valueOf(1.0), Category.FOOD, LocalDate.now().plusMonths(1));
        try {
            assertFalse(product.isProductExpired());
        } catch (ExpirationDateReachedException e) {
            System.out.println("Exception should not be thrown");
        }
    }

    @Test
    void isProductExpired_Expired() {
        product = new Product("bread", BigDecimal.valueOf(1.0), Category.FOOD, LocalDate.now().minusMonths(1));
        assertThrows(ExpirationDateReachedException.class, () -> {
            product.isProductExpired();
        });
    }
}
