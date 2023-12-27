package com.example.rucafeandroid.vinill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.rucafeandroid.R;

import java.util.ArrayList;

public class Ordering_Vinills_Activity extends AppCompatActivity {

    private ArrayList<String> vinills = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_vinill);

        RecyclerView vinillsRV=findViewById(R.id.vinillFlavorsRV);
        setUpVinills();
        Ordering_Vinills_Adapter adapter = new Ordering_Vinills_Adapter(this, vinills);
        vinillsRV.setAdapter(adapter);
        vinillsRV.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setUpVinills(){
        String[] donutNames = getResources().getStringArray(R.array.Vinill_Stock);

        for (int i = 0; i< donutNames.length;i++){
            vinills.add(donutNames[i]);
        }

    }
}