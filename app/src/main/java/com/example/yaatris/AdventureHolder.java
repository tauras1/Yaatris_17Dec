package com.example.yaatris;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdventureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView img;
    TextView sponsor, adventName, dateFrom, dateTo, price, rating;
    RatingBar ratingbar;

    OnCardListener onCardListener;

    public AdventureHolder(@NonNull View itemView, OnCardListener onCardListener) {
        super(itemView);
        this.onCardListener = onCardListener;
        this.img = itemView.findViewById(R.id.media_image);
        this.sponsor = itemView.findViewById(R.id.textView14);
        this.adventName = itemView.findViewById(R.id.textView15);
        this.dateFrom = itemView.findViewById(R.id.textView16);
        this.dateTo = itemView.findViewById(R.id.textView12);
        this.price = itemView.findViewById(R.id.textView17);
        this.rating = itemView.findViewById(R.id.textView18);
        this.ratingbar = itemView.findViewById(R.id.ratingBar);
        itemView.setOnClickListener(this);
    }

    public TextView getSponsor(){
        return this.sponsor;
    }

    @Override
    public void onClick(View v) {
        onCardListener.onNoteClick(getAdapterPosition());
    }

    public interface OnCardListener{
        void onNoteClick(int position);

    }


}
