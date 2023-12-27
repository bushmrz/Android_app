package com.example.rucafeandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Your_Order_Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private TextView subtotalOutput;
    private TextView salesTaxOutput;
    private TextView totalCostOutput;
    public static ArrayList<String> ordersString= new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    public static final String EMPTY_COST = "$0.00";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order);

        Button placeOrderButton = findViewById(R.id.addtoStoreOrdersButton);
        subtotalOutput=findViewById(R.id.subtotalDisplay);
        salesTaxOutput=findViewById(R.id.salesTaxDisplay);
        totalCostOutput=findViewById(R.id.totalCostDisplay);
        ListView orderListView = findViewById(R.id.menuItemsListView);

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ordersString);
        orderListView.setAdapter(adapter);
        orderListView.setOnItemClickListener(this);

        updatePrices();
    }

    /** вызывается, когда активность становится видимой для пользователя ( яв-ся частью жц акт-ти)
        гарантирует, что каждый раз, когда ваша активность становится видимой,
        интерфейс будет отображать самые актуальные данные **/
    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }


    private void removeOrderItem(String itemRemove) {
        for(int i=0; i<MainActivity.order.getItemList().size();i++){
            if(MainActivity.order.getItemList().get(i).toString().equals(itemRemove)){
                boolean isRemove=MainActivity.order.remove(MainActivity.order.getItemList().get(i));
                if(isRemove){
                    updatePrices();
                    ordersString.remove(itemRemove);
                    adapter.notifyDataSetChanged();
                    String confirm= getResources().getString(R.string.removeObjectItemConfirmation);
                    Toast.makeText(this,confirm,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String itemRemove= ordersString.get(i);
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(R.string.cancelItemTitle));
        alert.setMessage(getResources().getString(R.string.cancelItemMessage)+itemRemove);
        alert.setPositiveButton(getResources().getString(R.string.alert_yes), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeOrderItem(itemRemove);
            }
        }).setNegativeButton(getResources().getString(R.string.alert_no), new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog= alert.create();
        dialog.show();
    }

    private void updatePrices() {
        DecimalFormat dFormatter = new DecimalFormat("$"+"##,##0.00");
        if (MainActivity.order.getItemList().size() == 0) {
            String emptyCost = EMPTY_COST;
            subtotalOutput.setText(emptyCost);
            salesTaxOutput.setText(emptyCost);
            totalCostOutput.setText(emptyCost);
            return;
        }

        double subtotal = 0;
        for (MenuItem item : MainActivity.order.getItemList()) {
            subtotal += item.itemPrice();
        }

        double salesTax = subtotal * Order.NJTAX;
        double total = subtotal + salesTax;

        subtotalOutput.setText(dFormatter.format(subtotal));
        salesTaxOutput.setText(dFormatter.format(salesTax));
        totalCostOutput.setText(dFormatter.format(total));
    }

    private void loadOrderData() {
        OrderDatabaseHelper dbHelper = new OrderDatabaseHelper(this);
        Order lastOrder = dbHelper.getLastOrder(); // Метод для получения последнего заказа

        if (lastOrder != null) {
            MainActivity.order = lastOrder;

            ordersString.clear();
            for (MenuItem item : lastOrder.getItemList()) {
                ordersString.add(item.toString()); // Преобразование каждого элемента заказа в строку для отображения
            }
        } else {
            MainActivity.order = new Order(); // Если в базе данных нет заказов, создаем новый пустой заказ
        }

        adapter.notifyDataSetChanged();
    }

    /** метод обрабатывает логику оформления заказа: проверяет наличие товаров в корзине,
        запрашивает у пользователя подтверждение оформления и в случае подтверждения
        обрабатывает заказ, обновляя интерфейс и данные приложения **/
    public void placeOrder(View view){

        if (MainActivity.order == null || MainActivity.order.getItemList().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.place_order_error_when_order_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        try{
            if(MainActivity.order.getItemList().size()==0) throw new IllegalArgumentException();
            AlertDialog.Builder alert= new AlertDialog.Builder(this);
            alert.setTitle(getResources().getString(R.string.placeOrderTitle));
            alert.setMessage(getResources().getString(R.string.placeOrderMessage));
            alert.setPositiveButton(getResources().getString(R.string.alert_yes), new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean isPlace= MainActivity.ordersList.add(MainActivity.order);
                    if(isPlace){

                        MainActivity.order=new Order();
                        updatePrices();
                        ordersString.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
            }).setNegativeButton(getResources().getString(R.string.alert_no), new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog= alert.create();
            dialog.show();
        }catch(IllegalArgumentException e){
            Toast.makeText(this, getResources().getString(R.string.place_order_error_when_order_empty), Toast.LENGTH_SHORT).show();
        }
    }
}