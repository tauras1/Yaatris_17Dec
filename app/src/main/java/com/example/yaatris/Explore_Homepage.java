package com.example.yaatris;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class Explore_Homepage extends Fragment {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.riverrafting, R.drawable.safari, R.drawable.snow};
    private static final String[] adventures = new String[] { "River Rafting", "Trekking", "Desert Safari", "Snow skiing", "Sky Diving", "Caving", "Exploring"};
    public Explore_Homepage() {
    }

    public static Explore_Homepage newInstance() {
        Explore_Homepage fragment = new Explore_Homepage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_explore__homepage, container, false);
        Button btnSearch = (Button) v.findViewById(R.id.searchButton);
        btnSearch.setVisibility(View.VISIBLE);
        carouselView = (CarouselView) v.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                if(position ==0)
                {


                    Button btnSearch = (Button) v.findViewById(R.id.searchButton);
                    //btnSearch.setVisibility(View.GONE);

                    Intent inn = new Intent(getActivity(), RegisterUser.class);
                    startActivity(inn);


                    Toast.makeText(getActivity(), "Adventures ", Toast.LENGTH_SHORT).show();
                }
                else if (position ==1){
                    Intent in21 = new Intent(getActivity(), places.class);
                    startActivity(in21);
                    Toast.makeText(getActivity(), "Places ", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent in21 = new Intent(getActivity(), events.class);
                    startActivity(in21);
                    Toast.makeText(getActivity(), "Events ", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getActivity(), "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
            }
        });


        //Button btnSearch = (Button) v.findViewById(R.id.searchButton);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MapSearch.class);
                startActivity(in);
            }
        });

        return v;
    }
}