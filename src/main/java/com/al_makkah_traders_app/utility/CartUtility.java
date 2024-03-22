package com.al_makkah_traders_app.utility;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.Cart;
import javafx.collections.ObservableList;

public class CartUtility {

    public static boolean addCartItem(
            ObservableList<Cart> cartItems,
            String productCode,
            String productName,
            String brandName,
            String quantity,
            String price,
            boolean isSaleBill) {
        // check if the item is already in the cartItems on the base of productCode
        Cart cartItem = getCartItem(cartItems, productCode);
        if (cartItem != null) {
            // if the item is already in the cartItems, show an error message
            MessageDialogs.showWarningMessage("The item you are trying to add is already\nin the cart.");
            return false;
        } else {
            // check that if quantity and price are not empty and are valid
            if (quantity.isEmpty() || price.isEmpty() || !quantity.matches("\\d+(\\.\\d+)?")
                    || !price.matches("\\d+(\\.\\d+)?") || Double.parseDouble(quantity) <= 0
                    || Double.parseDouble(price) <= 0) {
                MessageDialogs.showWarningMessage("Please enter valid quantity and price.");
                return false;
            }
            if (isSaleBill) {
                // check if the quantity is greater than the available quantity
                if (Double.parseDouble(quantity) > DatabaseOperations.getAvailableQuantity(productCode)) {
                    MessageDialogs.showWarningMessage("Additional Quantity will be added to over invoice.");
                }
            }
            // add the item to the cartItems
            cartItems.add(new Cart(productCode, productName, brandName, Double.parseDouble(quantity),
                    Double.parseDouble(price), Double.parseDouble(price) * Double.parseDouble(quantity)));
        }
        return true;
    }

    private static Cart getCartItem(ObservableList<Cart> cartItems, String productCode) {
        for (Cart cartItem : cartItems) {
            if (cartItem.getProductCode().equals(productCode)) {
                return cartItem;
            }
        }
        return null;
    }

    public static boolean deleteCartItem(ObservableList<Cart> cartItems, Cart selectedCartItem) {
        boolean isConfirmed = MessageDialogs.showConfirmationDialog("Are you sure?");
        if (isConfirmed) {
            if (selectedCartItem != null) {
                // Remove the selected cart item from the cartItems list
                cartItems.remove(selectedCartItem);
                // Show a success message
                MessageDialogs.showMessageDialog("Item deleted successfully.");
            } else {
                MessageDialogs.showErrorMessage("Please select an item from the cart to delete.");
            }
        }
        return isConfirmed;
    }

    public static double calculateTotalBill(ObservableList<Cart> cartItems) {
        double totalBill = 0.0;
        for (Cart cartItem : cartItems) {
            totalBill += cartItem.getTotalPrice();
        }
        return totalBill;
    }

    public static boolean updateCartItem(
            Cart selectedCartItem,
            String productCode,
            String productName,
            String brandName,
            String quantityText,
            String priceText,
            boolean isSaleBill) {

        // Check if a cart item is selected
        if (selectedCartItem != null) {
            // Check if quantity and price are not null and are numeric(including decimal)
            if (quantityText.isEmpty() || priceText.isEmpty() || !quantityText.matches("\\d+(\\.\\d+)?")
                    || !priceText.matches("\\d+(\\.\\d+)?") || Double.parseDouble(quantityText) <= 0
                    || Double.parseDouble(priceText) <= 0) {
                MessageDialogs.showWarningMessage("Please enter valid quantity and price.");
                return false;
            }
            if (isSaleBill) {
                // check if the quantity is greater than the available quantity
                if (Double.parseDouble(quantityText) > DatabaseOperations.getAvailableQuantity(productCode)) {
                    MessageDialogs.showConfirmationDialog("Additional Qty will be added to over invoice.");
                }
            }
            // Update the selected cart item
            selectedCartItem.setProductCode(productCode);
            selectedCartItem.setProductName(productName);
            selectedCartItem.setBrandName(brandName);
            selectedCartItem.setQuantity(Double.parseDouble(quantityText));
            selectedCartItem.setPricePerUnit(Double.parseDouble(priceText));
            selectedCartItem.setTotalPrice(Double.parseDouble(priceText) * Double.parseDouble(quantityText));
            return true;
        } else {
            MessageDialogs.showErrorMessage("Please select an item from the cart to update.");
            return false;
        }
    }
}