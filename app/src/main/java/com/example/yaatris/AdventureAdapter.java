package com.example.yaatris;

import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdventureAdapter extends RecyclerView.Adapter<AdventureHolder> {

    Context c;
    ArrayList<AdvnetureModel> models;
    private AdventureHolder.OnCardListener onCardListener;

    public AdventureAdapter(Context c, ArrayList<AdvnetureModel> models, AdventureHolder.OnCardListener onCardListener) {
        this.c = c;
        this.models = models;
        this.onCardListener = onCardListener;
    }

    @NonNull
    @Override
    public AdventureHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.advnt_row, null);
        return new AdventureHolder(view, onCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdventureHolder holder, int i) {
        holder.sponsor.setText(models.get(i).getSponsor());
        holder.adventName.setText(models.get(i).getAdventureName());
        holder.dateFrom.setText(models.get(i).getFrom());
        holder.dateTo.setText(models.get(i).getTo());
        holder.price.setText(models.get(i).getPrice());
        holder.img.setImageResource(models.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
