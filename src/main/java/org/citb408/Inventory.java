package org.citb408;

import org.citb408.exceptions.InsufficientAmountOfProductException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class Inventory {
    private HashMap<Product, Integer> availableProducts;
    private HashMap<Category, BigDecimal> markup;
    private int numberOfDaysForPriceDecrease;
    private int priceDecreasePercentage;

    public Inventory(HashMap<Category, BigDecimal> markup, int numberOfDaysForPriceDecrease, int priceDecreasePercentage) {
        this.markup = markup;
        this.availableProducts = new HashMap<>();
        this.numberOfDaysForPriceDecrease = numberOfDaysForPriceDecrease;
        this.priceDecreasePercentage = priceDecreasePercentage;
    }

    public HashMap<Product, Integer> getAvailableProducts() {
        return availableProducts;
    }

    public HashMap<Category, BigDecimal> getMarkup() {
        return markup;
    }

    public int getNumberOfDaysForPriceDecrease() {
        return numberOfDaysForPriceDecrease;
    }

    public int getPriceDecreasePercentage() {
        return priceDecreasePercentage;
    }

    public void setMarkup(HashMap<Category, BigDecimal> markup) {
        this.markup = markup;
    }

    public void setNumberOfDaysForPriceDecrease(int numberOfDaysForPriceDecrease) {
        this.numberOfDaysForPriceDecrease = numberOfDaysForPriceDecrease;
    }

    public void setPriceDecreasePercentage(int priceDecreasePercentage) {
        this.priceDecreasePercentage = priceDecreasePercentage;
    }


    public boolean isProductAvailable(Product product, int amount) throws InsufficientAmountOfProductException {
        if (availableProducts == null) {
            throw new InsufficientAmountOfProductException("No available products.");
        }
        if (!availableProducts.containsKey(product)) {
            throw new InsufficientAmountOfProductException(
                    "Insufficient amount of " + product.getName() + ": " + amount
            );
        }
        if (this.availableProducts.get(product) < amount) {
            throw new InsufficientAmountOfProductException(
                    "Insufficient amount of " + product.getName() + ": " + (amount - this.availableProducts.get(product))
            );
        }
        return true;
    }


    // delivery price + markup
    public BigDecimal calculateBasePrice(Product product) {
        BigDecimal markupPercentage = markup.get(product.getCategory());
        BigDecimal markupAmount = product.getPriceOnDelivery().multiply(markupPercentage);
        return product.getPriceOnDelivery().add(markupAmount);
    }


    public boolean isProductApproachingExpiryDate(Product product) {
        LocalDate dateOfPriceDecrease = LocalDate.now().minusDays(numberOfDaysForPriceDecrease);
        return product.getExpiryDate().isEqual(dateOfPriceDecrease) || product.getExpiryDate().isBefore(dateOfPriceDecrease);
    }

    // base price + discount if expiry date is close
    public BigDecimal calculateSellingPrice(Product product) {
        BigDecimal price = calculateBasePrice(product);
        if (isProductApproachingExpiryDate(product)) {
            price = price.add(price.multiply(BigDecimal.valueOf(priceDecreasePercentage*0.01)));
        }
        return price;
    }

    public void removeProductsFromInventory(HashMap<Product, Integer> productsToRemove) {
        for (HashMap.Entry<Product, Integer> entry : productsToRemove.entrySet()) {
            Product product = entry.getKey();
            int amountToRemove = entry.getValue();
            // Retrieve the current amount from availableProducts
            int currentAmount = availableProducts.get(product);
            // Update the amount in availableProducts
            availableProducts.put(product, currentAmount - amountToRemove);
        }
    }


}
