package com.example.nasaimageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends BaseActivity {
    // create variables for text

    TextView name;
    Button button;
    EditText text;

    @Override
    protected void onPause() {
        super.onPause();
        EditText svName = findViewById(R.id.name);
        String currentValue = svName.getText().toString();
        SharedPreferences sPre = getSharedPreferences("app_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPre.edit();
        editor.putString("currentValue", currentValue);
        editor.apply();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//set variables for input
        button = (Button) findViewById(R.id.enter);
        name = (TextView) findViewById(R.id.welname);
        text = (EditText) findViewById(R.id.name);


        final int REQUEST_CODE = 1;

        SharedPreferences sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE);
        String userName = sharedPreferences.getString("currentValue", "");
        if (!userName.isEmpty()) {
            text.setText(userName);
        }

        //set intro
        LinearLayout lin = (LinearLayout) findViewById(R.id.smart);
        lin.removeAllViews();

        //listener to load name for greetings
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                String tx = text.getText().toString();
                name.setText(tx);
                TextView txtName = new TextView(MainActivity.this);
                txtName.setId(28);
                txtName.setText("So you're interested in watching the most amazing images from the universe?\n" +
                        "well you are in the right place!!  " +
                        "Enjoy");

                lin.addView(txtName);

                Toast.makeText(MainActivity.this, getString(R.string.message), Toast.LENGTH_LONG).show();
            }
        });

    }
}