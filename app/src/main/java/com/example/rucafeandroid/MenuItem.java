/**
 * The MenuItem class is the superclass of all menu items such as donuts and coffee.
 * Stores the price of the item and the quantity.
 *
 * @author Udayan Rai, Garvit Gupta
 */

package com.example.rucafeandroid;

import java.text.DecimalFormat;

public class MenuItem {
    private double priceOfItem;
    private int quantity;

    public MenuItem(double priceOfItem, int quantity) {
        this.priceOfItem = priceOfItem;
        this.quantity = quantity;
    }

    public double getPriceOfItem() {
        return priceOfItem;
    }

    public void setPriceOfItem(double price) {
        this.priceOfItem = price;
    }

    public void addPrice(double price) {
        this.priceOfItem = this.priceOfItem + price;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double itemPrice() {
        return quantity * priceOfItem;
    }

    @Override
    public String toString() {
        DecimalFormat dFormatter = new DecimalFormat("$" + "##,##0.00");
        String output = "(" + quantity + ")" + "\t Стоимость: " + dFormatter.format(itemPrice());
        return output;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MenuItem) {
            MenuItem item = (MenuItem) obj;
            return (item.quantity == this.quantity && priceOfItem == item.priceOfItem);
        }
        return false;
    }
}
