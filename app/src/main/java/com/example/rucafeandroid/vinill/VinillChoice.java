package com.example.rucafeandroid.vinill;

import com.example.rucafeandroid.MenuItem;

public class VinillChoice extends MenuItem {
    private String flavor;
    public static final double VINILL_PRICE = 1.79;


    public VinillChoice(String flavor, int quantity) {
        super(VINILL_PRICE, quantity);
        this.flavor = flavor;
    }

    @Override
    public double itemPrice() {
        return super.itemPrice();
    }


    @Override
    public String toString() {
        return flavor + "  " + super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VinillChoice) {
            VinillChoice item = (VinillChoice) obj;
            return (super.equals(item) && this.flavor.equals(item.flavor));
        }
        return false;
    }
}
