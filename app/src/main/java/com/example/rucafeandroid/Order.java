package com.example.rucafeandroid;

import java.util.ArrayList;


public class Order implements Customizable {

    private ArrayList<MenuItem> itemList;
    private int numItems;
    private double orderCost;
    private int orderNumber;
    public static final double NJTAX= 0.06625;
    private int id;


    public Order() {
        this.itemList = new ArrayList<>();
        this.numItems = 0;
        this.orderCost = 0;
        this.orderNumber = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<MenuItem> getItemList() {
        return itemList;
    }

    public void setOrderNumber(int num) {
        this.orderNumber = num;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    private int find(MenuItem item) {
        for (MenuItem i : itemList) {
            if (i.equals(item)) return itemList.indexOf(i);
        }
        return -1;
    }

    public double getTotalCost(double tax) {
        return this.orderCost + tax;
    }

    public double calculateTax() {
        return NJTAX * this.orderCost;
    }

    @Override
    public boolean add(Object obj) {

        if (obj instanceof MenuItem) {
            MenuItem item = (MenuItem) obj;

            //maybe delete later
            if (itemList.contains(item)) {
                int index = itemList.indexOf(item);
                itemList.get(index).setQuantity(itemList.get(index).getQuantity() + item.getQuantity());
                orderCost += item.itemPrice();
                return false;
            }
            itemList.add(item);
            numItems++;
            orderCost += item.itemPrice();
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object obj) {
        if (numItems > 0) {
            if (obj instanceof MenuItem) {
                MenuItem item = (MenuItem) obj;
                int index = find(item);
                itemList.remove(index);
                numItems--;
                orderCost -= item.itemPrice();
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < itemList.size(); i++) {
            output += itemList.get(i).toString() + "\n";
        }
        return output;
    }

    public double getTotalCost() {
        double tax = calculateTax();
        return orderCost + tax;
    }

    public void setOrderCost(double orderCost) {
        this.orderCost = orderCost;
    }

}
