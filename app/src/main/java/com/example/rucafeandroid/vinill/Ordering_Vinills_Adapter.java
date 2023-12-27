package com.example.rucafeandroid.vinill;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rucafeandroid.R;

import java.util.ArrayList;

public class Ordering_Vinills_Adapter extends RecyclerView.Adapter<Ordering_Vinills_Adapter.VinillsHolder> {
    private ArrayList<String> items;
    private Context context;

    public static final String Style_Price = "$1.59";
    public static final String Vin_Price = "$1.59";

    
    public Ordering_Vinills_Adapter(Context context, ArrayList<String> items){
        this.context=context;
        this.items=items;
    }
    
    @NonNull
    @Override
    public VinillsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.vinill_row_view, parent, false);
        return new VinillsHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VinillsHolder holder, int position) {
        holder.vinill_style.setText(items.get(position));
        if(items.get(position).contains("ROCK")) holder.vinill_price.setText(Style_Price);
        if(items.get(position).contains("POP")) holder.vinill_price.setText(Style_Price);
        if(items.get(position).contains("Classic")) holder.vinill_price.setText(Style_Price);
        if(items.get(position).contains("Jazz")) holder.vinill_price.setText(Style_Price);

        if(items.get(position).contains("EP")) holder.vinill_price.setText(Vin_Price);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class VinillsHolder extends RecyclerView.ViewHolder {
        private TextView vinill_style, vinill_price;
        private ConstraintLayout parentLayout;


        public VinillsHolder(@NonNull View itemView){
            super(itemView);
            vinill_style= itemView.findViewById(R.id.vinill_style);
            vinill_price= itemView.findViewById(R.id.vinill_price);

            parentLayout= itemView.findViewById(R.id.rowLayout);
            parentLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String styleString= (String)vinill_style.getText();
                    Intent intent= new Intent(itemView.getContext(), Selected_Vinills_Activity.class);
                    if(styleString.contains("EP")) intent.putExtra("TYPE","EP");

                    if(styleString.contains("ROCK")) intent.putExtra("style","ROCK");
                    if(styleString.contains("POP")) intent.putExtra("style","POP");
                    if(styleString.contains("Classic")) intent.putExtra("style","Classic");
                    if(styleString.contains("Jazz")) intent.putExtra("style","Jazz");

                    intent.putExtra("name", styleString);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
