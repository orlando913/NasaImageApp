package com.example.nasaimageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

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

public class MainActivity extends AppCompatActivity {

    private class CatImages extends AsyncTask<String, Integer, String> {
        private Bitmap currentCatPicture;
        private static final String TAG = "MyActivity";

        @Override
        protected String doInBackground(String... urls) {
            int progress =0;
            while (true) {
                try {
                    URL url = new URL("https://cataas.com/cat?json=true");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    String catId = jsonObject.getString("_id");
                    File localFile = new File(getFilesDir(), catId + ".jpg");
                    publishProgress(progress);
                    Thread.sleep(2000);
                    progress += 20;
                    if (progress > 100) {
                        progress = 0;
                    }

                    if (localFile.exists()) {
                        // Use the existing local cat image
                        currentCatPicture = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    } else {
                        // Download the cat image and save it locally
                        String imageUrl = jsonObject.getString("url");
                        URL imageUrlObject = new URL("https://cataas.com"+imageUrl);
                        HttpURLConnection imageConnection = (HttpURLConnection) imageUrlObject.openConnection();
                        imageConnection.setDoInput(true);
                        imageConnection.connect();
                        InputStream imageInput = imageConnection.getInputStream();
                        currentCatPicture = BitmapFactory.decodeStream(imageInput);

                        FileOutputStream outputStream = new FileOutputStream(localFile);
                        currentCatPicture.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    }
                    publishProgress(0);
                } catch (IOException | InterruptedException e) {
                    Log.e(TAG, "Error downloading cat picture: " + e.getMessage());
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON: " + e.getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update the ImageView with the current cat picture
            // (Assuming you have an ImageView with the ID "catImageView" in your layout)
            ImageView catImageView = findViewById(R.id.Cat);
            catImageView.setImageBitmap(currentCatPicture);
            ProgressBar progressBar = findViewById(R.id.bar);
            progressBar.setProgress(values[0]);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CatImages gatos = new CatImages();
        gatos.execute();

    }
}