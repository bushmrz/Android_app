package com.example.rucafeandroid;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.rucafeandroid.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rucafe.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ORDER_NUMBER = "order_number";
    private static final String COLUMN_ORDER_COST = "order_cost";
    // Добавьте другие столбцы по необходимости

    public OrderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," // Автоматически инкрементируемый ID
                + COLUMN_ORDER_NUMBER + " INTEGER,"
                + COLUMN_ORDER_COST + " REAL" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public void addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_NUMBER, order.getOrderNumber());
        values.put(COLUMN_ORDER_COST, order.getTotalCost(order.calculateTax())); // Используйте методы класса Order для получения стоимости

        db.insert(TABLE_ORDERS, null, values);
        db.close();
    }

    public void addOrderList(List<Order> orders) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Order order : orders) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ORDER_NUMBER, order.getOrderNumber());
            values.put(COLUMN_ORDER_COST, order.getTotalCost(order.calculateTax()));

            db.insert(TABLE_ORDERS, null, values);
        }

        db.close();
    }

    public void updateOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_NUMBER, order.getOrderNumber());
        values.put(COLUMN_ORDER_COST, order.getTotalCost(order.calculateTax()));
        db.update(TABLE_ORDERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(order.getId())});
        db.close();
    }

    public void deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, COLUMN_ID + " = ?", new String[]{String.valueOf(orderId)});
        db.close();
    }
}