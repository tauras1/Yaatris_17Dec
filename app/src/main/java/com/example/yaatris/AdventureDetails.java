package com.example.yaatris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdventureDetails extends AppCompatActivity {

    private Button button;
    AdvnetureModel m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_details);

        Adventures a = new Adventures();
        ArrayList<AdvnetureModel> newmodels;



        button = (Button) findViewById(R.id.Bookmyticket);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBookingPage();
            }
        });

        TextView name = (TextView)findViewById(R.id.adv_name);
        TextView name1 = (TextView)findViewById(R.id.loc_);
        TextView u = (TextView)findViewById(R.id.check_point);
        TextView v = (TextView)findViewById(R.id.open_at);
        TextView x = (TextView)findViewById(R.id.close_at);
        TextView y = (TextView)findViewById(R.id.price_place);
        TextView z = (TextView)findViewById(R.id.place_description);



        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            int j = (int) b.get("index");
            MainActivity amodel = new MainActivity();
            newmodels = amodel.getModels();
            Toast.makeText(this, String.valueOf(newmodels.size()), Toast.LENGTH_SHORT).show();
            this.m = newmodels.get(Integer.valueOf(j));
            name.setText(this.m.adventureName);

            name1.setText(this.m.location);
            u.setText(this.m.checkpoints);
            v.setText(this.m.from);
           x.setText(this.m.to);
            y.setText(this.m.price);
            z.setText(this.m.desc);

        }
    }

    public void openBookingPage() {
        Intent intent = new Intent(this, BookingPage.class);
        startActivity(intent);
    }







}
