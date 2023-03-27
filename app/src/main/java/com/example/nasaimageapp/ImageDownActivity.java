package com.example.nasaimageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.PrecomputedTextCompat;
import com.google.gson.Gson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
    private TextView mNameTextView;
    private TextView mExplanationTextView;
    private TextView mDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_down);

        mImageView = findViewById(R.id.im);
        mNameTextView = findViewById(R.id.iName);
        mExplanationTextView = findViewById(R.id.explanation);
        mDateTextView = findViewById(R.id.iDate);

        String apiUrl = "https://api.nasa.gov/planetary/apod?api_key=YOUR_API_KEY";
        new FetchAPODData(mImageView, mNameTextView, mExplanationTextView, mDateTextView).execute(apiUrl);



    }

}
