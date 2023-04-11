package com.example.nasaimageapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {
    // create variables for text

    TextView name;
    Button button;
    EditText text;

    @Override
    protected void onPause() {
        super.onPause();
        //save name from edit text
        EditText svName = findViewById(R.id.name);
        String currentValue = svName.getText().toString();
        SharedPreferences sPre = getSharedPreferences("app_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPre.edit();
        editor.putString("currentValue", currentValue);
        editor.apply();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tBar = findViewById(R.id.toolbar);
        setSupportActionBar(tBar);


        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set variables for input
        button = (Button) findViewById(R.id.enter);
        name = (TextView) findViewById(R.id.welname);
        text = (EditText) findViewById(R.id.name);

// call for name previously saved
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
        button.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.item1:
                message = "Touch any element from the list to display the image.";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        String message = null;

        switch (item.getItemId()) {
            case R.id.Home: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.Slist: {
                Intent intent = new Intent(this, SListViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.IDown: {
                Intent intent = new Intent(this, ImageDownActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.view:
                Intent intent = new Intent(this, VIewerActivity.class);
                startActivity(intent);
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}