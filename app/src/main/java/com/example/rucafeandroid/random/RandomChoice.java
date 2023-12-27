package com.example.rucafeandroid.random;

import com.example.rucafeandroid.Customizable;
import com.example.rucafeandroid.MenuItem;

public class RandomChoice extends MenuItem implements Customizable {
    private int style;
    private boolean popular;
    private boolean newest;
    private boolean rare;
    private boolean rulang;
    private boolean enlang;
    public static final double ROCK_PRICE = 5.69;
    public static final double style_PRICE_INCREASE = 0.40;
    public static final double ADD_ON_PRICE = 1.30;
    public static final double ROCK_style = 0;
    public static final double POP_style = 1;
    public static final double DJAZ_style = 2;
    public static final double CLASSIC_style = 3;

    private String output;


    public RandomChoice(int style, boolean popular, boolean newest, boolean rare, boolean rulang, boolean enlang, int quantity) {
        super(ROCK_PRICE, quantity);
        this.style = style;
        this.popular = popular;
        this.newest = newest;
        this.rare = rare;
        this.rulang = rulang;
        this.enlang = enlang;
    }

    @Override
    public boolean add(Object obj) {
        if (obj instanceof String) {
            if (obj.equals("popular")) {
                popular = true;
                return true;
            }
            if (obj.equals("newest")) {
                newest = true;
                return true;
            }
            if (obj.equals("rare")) {
                rare = true;
                return true;
            }
            if (obj.equals("rulang")) {
                rulang = true;
                return true;
            }
            if (obj.equals("enlang")) {
                enlang = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object obj) {
        if (obj instanceof String) {
            if (obj.equals("popular")) {
                popular = false;
                return true;
            }
            if (obj.equals("newest")) {
                newest = false;
                return true;
            }
            if (obj.equals("rare")) {
                rare = false;
                return true;
            }
            if (obj.equals("rulang")) {
                rulang = false;
                return true;
            }
            if (obj.equals("enlang")) {
                enlang = false;
                return true;
            }
        }
        return false;
    }


    @Override
    public double itemPrice() {
        this.setPriceOfItem(ROCK_PRICE + (style * style_PRICE_INCREASE));
        if (popular) this.addPrice(ADD_ON_PRICE);
        if (newest) this.addPrice(ADD_ON_PRICE*10);
        if (rare) this.addPrice(ADD_ON_PRICE);
        if (rulang) this.addPrice(ADD_ON_PRICE*2);
        if (enlang) this.addPrice(ADD_ON_PRICE*2);
        return this.getPriceOfItem() * getQuantity() * 0.5;
    }

    @Override
    public String toString() {
        String output = "";
        if (style == ROCK_style) output += "Рок ";
        if (style == POP_style) output += "Поп ";
        if (style == DJAZ_style) output += "Джаз ";
        if (style == CLASSIC_style) output += "Классика ";

        if (!popular && !newest && !rare && !rulang && !enlang) return output + super.toString();

        output += ": ";
        if (popular) output += "Новое ";
        if (newest) output += "Редкое ";
        if (rare) output += "Популярное ";
        if (rulang) output += "Eng ";
        if (enlang) output += "Rus ";

        return output + super.toString();
    }
}
