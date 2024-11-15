package org.citb408;

import org.citb408.exceptions.ExpirationDateReachedException;
import org.citb408.exceptions.InsufficientAmountOfProductException;

import java.time.LocalDate;
import java.util.HashMap;

public class Client {
    private HashMap<Product, Integer> productsInCart;

    public Client() {
    }

    public HashMap<Product, Integer> getProductsInCart() {
        return productsInCart;
    }

    public void addProductToCart(Store store, Product product, int amount) {
       try {
           if (store.getInventory().isProductAvailable(product, amount) ||
           !(product.isProductExpired())) {
               productsInCart.put(product, amount);
           }
       } catch (ExpirationDateReachedException | InsufficientAmountOfProductException e) {
           System.out.println(e.getMessage());
       }
    }
}
