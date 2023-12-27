package com.example.rucafeandroid;

import java.util.ArrayList;

public class StoreOrders implements Customizable {

    private ArrayList<Order> orderList;
    private int numOrders;
    private int orderNumCounter;


    public StoreOrders(){
        this.orderList = new ArrayList<>();
        numOrders = 0;

    }

    public int getNumOrders(){
        return numOrders;
    }

    public int getorderIndex(int orderNum){
        for(int i=0; i<orderList.size(); i++){
            if(orderList.get(i).getOrderNumber()==orderNum) return i;
        }
        return -1;
    }

    public ArrayList<Order> getOrderList(){
        return orderList;
    }

    @Override
    public boolean add(Object obj) {
        if (obj instanceof Order){
            Order item = (Order) obj;
            orderList.add(item);
            numOrders++;
            orderNumCounter++;
            item.setOrderNumber(orderNumCounter);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object obj) {
        if (numOrders > 0) {
            if (obj instanceof Order) {
                Order item = (Order) obj;
                orderList.remove(item);
                numOrders--;
                return true;
            }
        }
        return false;
    }
}
