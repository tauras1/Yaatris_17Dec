package com.example.yaatris;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Adventures#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Adventures extends Fragment implements AdventureHolder.OnCardListener  {
    private CardView card;
    List<AdventureData> adventureDataList = new ArrayList<>();
    RecyclerView recyclerView;
    AdventureAdapter adventureAdapter;
    private ArrayList<AdvnetureModel> models = new ArrayList<>();
    private StorageReference mStorageRef;

    public Adventures() {
    }

    public static Adventures newInstance() {
        Adventures fragment = new Adventures();
        return fragment;
    }

    public  ArrayList<AdvnetureModel> getModels() {
        return this.models;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_adventures, container, false);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdventureCompanyImages");

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                adventureDataList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    AdventureData advnt = postSnapshot.getValue(AdventureData.class);
                    adventureDataList.add(advnt);
                    final AdvnetureModel m = new AdvnetureModel();
                    m.setAdventureName(advnt.adventureName);
                    m.setLocation(advnt.location);
                    m.setCheckpoints(advnt.checkpoints);
                    m.setDesc(advnt.desc);
                    m.setSponsor(advnt.cmail);
                    m.setFrom(advnt.from);
                    m.setTo(advnt.to);
                    m.setPrice(advnt.price);
                    m.setImage(R.drawable.udpr);
                    models.add(m);
                }
                abc();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return v;
    }

    private void abc(){
        adventureAdapter = new AdventureAdapter(getActivity(), models, this);
        recyclerView.setAdapter(adventureAdapter);
        MainActivity a = new MainActivity();
        a.setModels(this.models);
    }


    private  void openAdventure(){
        Intent in = new Intent(getActivity(), AdventureDetails.class);
        startActivity(in);
    }

    @Override
    public void onNoteClick(int position) {
        models.get(position);
        Intent i = new Intent(getActivity(), AdventureDetails.class);
        i.putExtra( "index", position);
        startActivity(i);
    }
}