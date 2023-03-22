package com.example.nasaimageapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // create variables for text

    TextView name;
    Button button;
    EditText text;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//set variables for input
        button = (Button) findViewById(R.id.enter);
        name = (TextView) findViewById(R.id.welname);
        text = (EditText) findViewById(R.id.name);

        //listener to load name for greetings
        button.setOnClickListener(view -> {
            String tx = text.getText().toString();
            name.setText(tx);
            Toast.makeText(MainActivity.this , getString(R.string.message), Toast.LENGTH_LONG).show();
        });

    }
}