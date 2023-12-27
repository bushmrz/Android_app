package com.example.rucafeandroid.random;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rucafeandroid.MainActivity;
import com.example.rucafeandroid.MenuItem;
import com.example.rucafeandroid.R;
import com.example.rucafeandroid.Your_Order_Activity;

import java.text.DecimalFormat;

public class Ordering_random_Activity extends AppCompatActivity{
    public static final int SMALL_SIZE = 0;
    public static final int TALL_SIZE = 1;
    public static final int VENTI_SIZE = 2;
    public static final int GRANDE_SIZE = 3;
    public static final int DOZEN = 12;
    private CheckBox popular;
    private CheckBox newcheck;
    private CheckBox rulang;
    private CheckBox rare;
    private CheckBox enlang;
    private Spinner sizeSpinner;
    private Spinner quantitySpinner;
    private TextView price;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_random);

        ImageView vinillaImage = findViewById(R.id.VinillaImage);
        popular = findViewById(R.id.PopularChecker);
        newcheck = findViewById(R.id.NewChecker);
        rulang = findViewById(R.id.RuLangChecker);
        rare = findViewById(R.id.RareChecker);
        enlang = findViewById(R.id.EnLangChecker);
        sizeSpinner = findViewById(R.id.SizeTrackSpinner);
        quantitySpinner = findViewById(R.id.QuantitySpinner);
        String[] quantities = quantitySetter();
        String[] sizes = getResources().getStringArray(R.array.Random_params);
        vinillaImage.setImageResource(R.drawable.random);
        price=findViewById(R.id.PriceDisplayer);

        ArrayAdapter<String> adapterForQuantities = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, quantities);
        ArrayAdapter<String> adapterforSizes = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sizes);

        quantitySpinner.setAdapter(adapterForQuantities);
        sizeSpinner.setAdapter(adapterforSizes);

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                displayPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private String[] quantitySetter(){
        String[] arr= new String[DOZEN];
        for(int i=0; i<DOZEN; i++){
            arr[i]=""+(i+1);
        }
        return arr;
    }

    public void updateNewPrice(View view){
        displayPrice();
    }


    public void updatePopularPrice(View view){
        displayPrice();
    }


    public void updateRarePrice(View view){
        displayPrice();
    }


    public void updateRulangPrice(View view){
        displayPrice();
    }


    public void updateEnlangPrice(View view){
        displayPrice();
    }


    public void addRandom(View view){
        int quantity = Integer.parseInt(quantitySpinner.getSelectedItem().toString());
        MenuItem randomvin=displayHelper(quantity);
        boolean isAdd= MainActivity.order.add(randomvin);
        String randomvinString= randomvin.toString()+getResources().getString(R.string.addedOrder);
        if(isAdd){
            Your_Order_Activity.ordersString.add(randomvin.toString());
            Toast.makeText(this,randomvinString,Toast.LENGTH_SHORT).show();
        }
    }

    private void displayPrice(){
        DecimalFormat dFormatter = new DecimalFormat("$"+ "##,##0.00");
        MenuItem randomvin = displayHelper(1);
        String cost=""+dFormatter.format(randomvin.itemPrice());
        price.setText(cost);
    }


    private MenuItem displayHelper(int quantity){
        boolean popular= this.popular.isChecked();
        boolean newcheck = this.newcheck.isChecked();
        boolean rare = this.rare.isChecked();
        boolean enlang = this.enlang.isChecked();
        boolean rulang = this.rulang.isChecked();

        int size=SMALL_SIZE;
        if(sizeSpinner.getSelectedItem().toString().equals("Поп")) size=TALL_SIZE;
        if(sizeSpinner.getSelectedItem().toString().equals("Джаз")) size=VENTI_SIZE;
        if(sizeSpinner.getSelectedItem().toString().equals("Классика")) size=GRANDE_SIZE;

        MenuItem randomvin= new RandomChoice(size,newcheck,rare,popular,enlang,rulang,quantity);
        return randomvin;
    }

}