package com.example.rucafeandroid;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rucafeandroid.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rucafe.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ORDER_NUMBER = "order_number";
    private static final String COLUMN_ORDER_COST = "order_cost";


    private static final String TABLE_ORDER_ITEMS = "order_items";
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_ORDER_ID = "order_id"; // Foreign key to TABLE_ORDERS
    private static final String COLUMN_ITEM_NAME = "name";
    private static final String COLUMN_ITEM_PRICE = "price";
    private static final String COLUMN_ITEM_QUANTITY = "quantity";

    public OrderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORDER_NUMBER + " INTEGER,"
                + COLUMN_ORDER_COST + " REAL" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);

        String CREATE_ORDER_ITEMS_TABLE = "CREATE TABLE " + TABLE_ORDER_ITEMS + "("
                + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORDER_ID + " INTEGER,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_PRICE + " REAL,"
                + COLUMN_ITEM_QUANTITY + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + COLUMN_ID + ")" + ")";
        db.execSQL(CREATE_ORDER_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public void addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

//        ContentValues values = new ContentValues();
//        values.put(COLUMN_ORDER_NUMBER, order.getOrderNumber());
//        values.put(COLUMN_ORDER_COST, order.getTotalCost(order.calculateTax()));
//        db.insert(TABLE_ORDERS, null, values);

        ContentValues orderValues = new ContentValues();
        orderValues.put(COLUMN_ORDER_NUMBER, order.getOrderNumber());
        orderValues.put(COLUMN_ORDER_COST, order.getTotalCost(order.calculateTax()));
        long orderId = db.insert(TABLE_ORDERS, null, orderValues);

        for (MenuItem item : order.getItemList()) {
            ContentValues itemValues = new ContentValues();
            itemValues.put(COLUMN_ORDER_ID, orderId);
            itemValues.put(COLUMN_ITEM_NAME, item.getName());
            itemValues.put(COLUMN_ITEM_PRICE, item.getPriceOfItem());
            itemValues.put(COLUMN_ITEM_QUANTITY, item.getQuantity());
            db.insert(TABLE_ORDER_ITEMS, null, itemValues);
        }
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

    private void loadOrderItems(Order order, int orderId, SQLiteDatabase db) {
        Cursor itemCursor = db.query(TABLE_ORDER_ITEMS, null, "order_id=?", new String[]{String.valueOf(orderId)}, null, null, null);
        if (itemCursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = itemCursor.getString(itemCursor.getColumnIndex("name"));
                @SuppressLint("Range") double price = itemCursor.getDouble(itemCursor.getColumnIndex("price"));
                @SuppressLint("Range") int quantity = itemCursor.getInt(itemCursor.getColumnIndex("quantity"));

                MenuItem item = new MenuItem(price, quantity);
                order.add(item);
            } while (itemCursor.moveToNext());
        }
        itemCursor.close();
    }

    public Order getLastOrder() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, null, null, null, null, null, COLUMN_ID + " DESC", "1");

        Order order = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") double orderCost = cursor.getDouble(cursor.getColumnIndex(COLUMN_ORDER_COST));
            @SuppressLint("Range") int orderNumber = cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER_NUMBER));

            order = new Order();
            order.setId(orderId);
            order.setOrderNumber(orderNumber);
            order.setOrderCost(orderCost);

            loadOrderItems(order, orderId, db);
        }
        cursor.close();
        db.close();
        return order;
    }

}