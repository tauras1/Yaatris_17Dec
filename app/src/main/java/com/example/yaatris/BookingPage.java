package com.example.yaatris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookingPage extends AppCompatActivity {

    private Button button;
    EditText e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);
        e = (EditText) findViewById(R.id.editTextTextEmailAddress2);

        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfirmed();
            }
        });
    }
    public void openConfirmed() {
        Intent intent = new Intent(this, Confirmed.class);
        intent.putExtra( "ID", e.getText().toString());
        startActivity(intent);
    }

}