package com.example.yaatris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button Settings2;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    public static String emailId ;
    static ArrayList<AdvnetureModel> models;

    public void setModels(ArrayList<AdvnetureModel> models) {
        MainActivity.models = models;
        System.out.println("######################################################################");
        System.out.println(models);
    }

    public ArrayList<AdvnetureModel> getModels() {
        System.out.println("######################################################################");
        System.out.println(MainActivity.models);
        return MainActivity.models;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.explore_Homepage:
                                selectedFragment = Explore_Homepage.newInstance();
                                break;
                            case R.id.adventures:
                                selectedFragment = Adventures.newInstance();
                                break;
                            case R.id.volunteers:
                                selectedFragment = Volunteers.newInstance();
                                break;
                            case R.id.account:
                                selectedFragment = Account.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, Explore_Homepage.newInstance());
        transaction.commit();

        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
//                    Intent intent = new Intent(this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
                }
            }


        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Settings2)
        {
            Intent inn = new Intent(this, settings.class);
            startActivity(inn);
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.U_reg)
        {
            Intent inn = new Intent(this, RegisterUser.class);
            startActivity(inn);
            Toast.makeText(this, "User Registration", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}