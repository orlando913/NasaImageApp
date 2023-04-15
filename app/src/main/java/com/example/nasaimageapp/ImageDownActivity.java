package com.example.nasaimageapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.PrecomputedTextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.List;

public class ImageDownActivity extends BaseActivity {

    int progressV = 0;
    ProgressBar pBar;

    // declare elements
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
//progressbaf
        pBar = (ProgressBar) findViewById(R.id.progress);
        pBar.setVisibility(View.GONE);

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

        // update elements
        mImageView = findViewById(R.id.im);
        mNameTextView = findViewById(R.id.iName);
        mExplanationTextView = findViewById(R.id.explanation);
        mDateTextView = findViewById(R.id.iDate);
        dateEditText = findViewById(R.id.dte);

        mButton = findViewById(R.id.plus);


        //list of image info object that pass to new activity

        List<ImageInfo> imageInfoList = new ArrayList<>();
        // populate the list with ImageInfo objects
        Intent intent = new Intent(this, SListViewActivity.class);
        intent.putExtra("imageInfoList", new Gson().toJson(imageInfoList));
        // startActivity(intent);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call for date from editext
                String date = dateEditText.getText().toString();
                String imageUrl = getImageUrlForDate(date);




                Toast.makeText(ImageDownActivity.this, "Image added", Toast.LENGTH_SHORT).show();

                // create a new ImageInfo object with the relevant information
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setName("Name");
                imageInfo.setDate(date);
                imageInfo.setExplanation("Explanation");
                imageInfo.setImagePath(imageUrl);

                // add the ImageInfo object to the list
                imageInfoList.add(imageInfo);

                // populate the list with ImageInfo objects
                Intent intent = new Intent(ImageDownActivity.this, SListViewActivity.class);
                intent.putExtra("imageInfoList", new Gson().toJson(imageInfoList));

                //update elements
                mNameTextView.setText("Name");
                mExplanationTextView.setText("Explanation");
                mDateTextView.setText("Date");
                FetchAPODData fetchAPODData = new FetchAPODData(mImageView, mNameTextView,
                        mExplanationTextView, mDateTextView, dateEditText);
                fetchAPODData.execute(imageUrl);

                setProgressValue(progressV);
                // get a reference to the InputMethodManager
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

// hide the keyboard
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                dateEditText.setText("");


            }

            private void setProgressValue(final int progressV) {
                if (progressV < 100) {
                    pBar.setProgress(progressV);
                    Thread thread = new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        setProgressValue(progressV + 10);
                    });
                    thread.start();
                }
            }







        });

    }
    int customRowLayout = R.layout.row_layout;




    private String getImageUrlForDate(String date) {
        return "https://api.nasa.gov/planetary/apod?api_key=QLiIb7c2IgcMOHiGbSUR4QuubFwcggLHsFkC2dlf&date="+date;
    }


}