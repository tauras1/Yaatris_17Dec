package com.example.yaatris;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Volunteers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Volunteers extends Fragment {
    private CardView card;

    public Volunteers() {
        // Required empty public constructor
    }


    public static Volunteers newInstance() {
        Volunteers fragment = new Volunteers();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_adventures, container, false);
//        card = (CardView) v.findViewById(R.id.card_view);
//        card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Calling EditText is empty or no method.
//                openAdventure();
//            }
//        });
        return v;
    }

    private  void openAdventure(){
        Intent in = new Intent(getActivity(), VolunteerDetails.class);
        startActivity(in);
    }

}