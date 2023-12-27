package com.example.rucafeandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.rucafeandroid.random.Ordering_random_Activity;
import com.example.rucafeandroid.vinill.Ordering_Vinills_Activity;

public class MainActivity extends AppCompatActivity {

    public static Order order;
    public static StoreOrders ordersList;
    private OrderDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new OrderDatabaseHelper(this);

        order=new Order();

        dbHelper.addOrder(order);

        ordersList= new StoreOrders();

        dbHelper.addOrderList(ordersList.getOrderList());

        ImageButton ordering_stock_button= findViewById(R.id.orderStokButton);
        ordering_stock_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openVinillsPage();
            }
        });

        ImageButton order_coffee_button= findViewById(R.id.orderingRandomButton);
        order_coffee_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openRandomPage();
            }
        });

        Button yourOrderButton=findViewById(R.id.yourOrderButton);
        yourOrderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) { openOrderPage(); }
        });

        Button storeOrdersButton= findViewById(R.id.storeOrdersButton);
        storeOrdersButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openStoreOrdersPage();
            }
        });
    }

    public void openVinillsPage(){
        Intent intent= new Intent(this, Ordering_Vinills_Activity.class);
        startActivity(intent);
    }

    public void openRandomPage(){
        Intent intent= new Intent(this, Ordering_random_Activity.class);
        startActivity(intent);
    }

    public void openOrderPage(){
        Intent intent= new Intent(this, Your_Order_Activity.class);
        startActivity(intent);
    }

    public void openStoreOrdersPage(){
        Intent intent= new Intent(this, Store_Orders_Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Сохранение текущего состояния заказа в базу данных
        if (order != null) {
            dbHelper.addOrder(order);
            dbHelper.updateOrder(order);
        }
        if (ordersList.getOrderList() != null) {
            dbHelper.addOrderList(ordersList.getOrderList());
        }
    }

}