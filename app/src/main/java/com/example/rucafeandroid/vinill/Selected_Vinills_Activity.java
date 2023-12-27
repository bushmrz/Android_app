package com.example.rucafeandroid.vinill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rucafeandroid.MainActivity;
import com.example.rucafeandroid.MenuItem;
import com.example.rucafeandroid.R;
import com.example.rucafeandroid.Your_Order_Activity;

import java.text.DecimalFormat;

public class Selected_Vinills_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView vinill_Price;
    private String style;
    private String name;

    private int quantity;
    public static final int DOZEN = 12;

 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_vinill);

        Button addButton = findViewById(R.id.addVinillToOrderButton);
        TextView vinill_displayer = findViewById(R.id.vinillstyle);
        Spinner quantitySelector = findViewById(R.id.quantitySpinner);
        ImageView img = findViewById(R.id.vinill_img);
        vinill_Price=findViewById(R.id.priceDisplayerVinill);
        String[] quantities = quantitySetter();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, quantities);
        quantitySelector.setAdapter(adapter);
        quantitySelector.setOnItemSelectedListener(this);

        Intent intent= getIntent();
        style=intent.getStringExtra("style");
        name = intent.getStringExtra("name");
        String display= name +" "+ " vinill";
        vinill_displayer.setText(display);

        img.setImageResource(R.drawable.vinill_one);
    }
    
    private String[] quantitySetter(){
        String[] arr= new String[DOZEN];
        for(int i=0; i<DOZEN; i++){
            arr[i]=""+(i+1);
        }
        return arr;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        DecimalFormat dFormatter = new DecimalFormat("$" + "##,##0.00");
        
        quantity= Integer.parseInt(adapterView.getSelectedItem().toString());
        if(style.equals("ROCK")){
            MenuItem vinill=new YeastVinill(style,quantity);
            String cost= dFormatter.format(vinill.itemPrice());
            vinill_Price.setText(cost);
        }
        if(style.equals("POP")){
            MenuItem vinill=new YeastVinill(style,quantity);
            String cost= dFormatter.format(vinill.itemPrice());
            vinill_Price.setText(cost);
        }
        if(style.equals("Classic")){
            MenuItem vinill=new YeastVinill(style,quantity);
            String cost= dFormatter.format(vinill.itemPrice());
            vinill_Price.setText(cost);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}


    public void addvinill(View view){
        if(style.equals("ROCK")){
            MenuItem vinill= new YeastVinill(style,quantity);
            boolean isAdd= MainActivity.order.add(vinill);
            String vinillString= vinill.toString() +getResources().getString(R.string.addedOrder);
            if(isAdd){
                Your_Order_Activity.ordersString.add(vinill.toString());
                Toast.makeText(this,vinillString,Toast.LENGTH_SHORT).show();
            }
        }
        if(style.equals("POP")){
            MenuItem vinill= new YeastVinill(style,quantity);
            boolean isAdd= MainActivity.order.add(vinill);
            String vinillString= vinill.toString() +getResources().getString(R.string.addedOrder);
            if(isAdd){
                Your_Order_Activity.ordersString.add(vinill.toString());
                Toast.makeText(this,vinillString,Toast.LENGTH_SHORT).show();
            }
        }
        if(style.equals("Classic")){
            MenuItem vinill= new YeastVinill(style,quantity);
            boolean isAdd= MainActivity.order.add(vinill);
            String vinillString= vinill.toString() +getResources().getString(R.string.addedOrder);
            if(isAdd){
                Your_Order_Activity.ordersString.add(vinill.toString());
                Toast.makeText(this,vinillString,Toast.LENGTH_SHORT).show();
            }
        }
    }
}