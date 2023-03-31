package com.example.nasaimageapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.PrecomputedTextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownActivity extends BaseActivity {
    private ImageView mImageView;
    private ImageButton mButton;
    private TextView mNameTextView;
    private TextView mExplanationTextView;
    private TextView mDateTextView;
    private EditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_down);

        //For toolbar:
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

         mImageView = findViewById(R.id.im);
         mNameTextView = findViewById(R.id.iName);
        mExplanationTextView = findViewById(R.id.explanation);
        mDateTextView = findViewById(R.id.iDate);
        dateEditText = findViewById(R.id.dte);

        mButton = findViewById(R.id.plus);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateEditText.getText().toString();
                String imageUrl = getImageUrlForDate(date);
                new DownloadImageTask().execute(imageUrl);

                //update fragment
                mNameTextView.setText("Name");
                mExplanationTextView.setText("Explanation");
                mDateTextView.setText("Date");
            }
            class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

                @Override
                protected Bitmap doInBackground(String... urls) {
                    String imageUrl = urls[0];
                    Bitmap bitmap = null;
                    try {
                        InputStream in = new URL(imageUrl).openStream();
                        bitmap = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return bitmap;
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    mImageView.setImageBitmap(result);
                }
            }

        });


        String apiUrl = "https://api.nasa.gov/planetary/apod?api_key=QLiIb7c2IgcMOHiGbSUR4QuubFwcggLHsFkC2dlf";
        FetchAPODData fetchAPODData = new FetchAPODData(mImageView, mNameTextView,
                mExplanationTextView, mDateTextView, dateEditText);
        fetchAPODData.execute(apiUrl);



    }
    private String getImageUrlForDate(String date) {
        return "https://api.nasa.gov/planetary/apod?api_key=QLiIb7c2IgcMOHiGbSUR4QuubFwcggLHsFkC2dlf";
    }

}
