package com.example.yaatris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Confirmed extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed);

        TextView p = (TextView)findViewById(R.id.textView22);


        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j = (String) b.get("ID");
            p.setText(j);



    }}
}