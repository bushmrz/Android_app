/**
 * YeastDonut class creates a cake donut object that extends Menuiten.
 * YeastDonut Object holds the style, quantity, and price of a Yeast Donut.
 *
 * @author Garvit Gupta, Udayan Rai
 */
package com.example.rucafeandroid.vinill;

import com.example.rucafeandroid.MenuItem;

public class YeastVinill extends MenuItem {
    private String style;
    public static final double TYPE_PRICE = 1.59;



    public YeastVinill(String style, int quantity) {
        super(TYPE_PRICE, quantity);
        this.style = style;
    }
    
    @Override
    public double itemPrice() {
        return super.itemPrice();
    }

    
    @Override
    public String toString() {
        return style + " Vinill " + super.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof YeastVinill) {
            YeastVinill item = (YeastVinill) obj;
            return (super.equals(item) && this.style.equals(item.style));
        }
        return false;
    }
}
