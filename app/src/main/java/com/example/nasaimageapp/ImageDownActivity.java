package com.example.nasaimageapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.PrecomputedTextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
        startActivity(intent);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call for date from editext
                String date = dateEditText.getText().toString();
                String imageUrl = getImageUrlForDate(date);
                new DownloadImageTask().execute(imageUrl);

                Toast.makeText(ImageDownActivity.this, "Image added", Toast.LENGTH_SHORT).show();

                //update elements
                mNameTextView.setText("Name");
                mExplanationTextView.setText("Explanation");
                mDateTextView.setText("Date");
                FetchAPODData fetchAPODData = new FetchAPODData(mImageView, mNameTextView,
                        mExplanationTextView, mDateTextView, dateEditText);
                fetchAPODData.execute(imageUrl);
                // get the image info
                Bitmap imageBitmap =  Bitmap.createBitmap(mImageView.getWidth(),mImageView.getHeight(),Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(imageBitmap);
                mImageView.draw(canvas);
                String imageName = "image_" + date + ".png";
                String imageFilePath = saveImageToFile(imageBitmap, imageName);

                // create the ImageInfo object and add it to the list
                ImageInfo imageInfo = new ImageInfo(imageName ,imageUrl, mExplanationTextView.getText().toString(), date);
                imageInfoList.add(imageInfo);

                // start the new activity with the list of ImageInfo objects
                Intent intent = new Intent(ImageDownActivity.this, SListViewActivity.class);
                intent.putExtra("imageInfoList", new Gson().toJson(imageInfoList));
                startActivity(intent);

                dateEditText.setText("");


            }

            class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

                @Override
                protected Bitmap doInBackground(String... urls) {
                    // get image
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

            private String saveImageToFile(Bitmap imageBitmap, String imageName) {
                // save the image to file
                File imagePath = new File(getExternalFilesDir(null), "images");
                if (!imagePath.exists()) {
                    imagePath.mkdir();
                }
                File imageFile = new File(imagePath, imageName);
                try {
                    FileOutputStream fos = new FileOutputStream(imageFile);
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return imageFile.getAbsolutePath();
            }

        });






    }

    private String getImageUrlForDate(String date) {
        return "https://api.nasa.gov/planetary/apod?api_key=QLiIb7c2IgcMOHiGbSUR4QuubFwcggLHsFkC2dlf&date="+date;
    }

}
