package com.example.nasaimageapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchAPODData extends AsyncTask<String, Void, JSONObject> {

    private ImageView mImageView;
    private TextView mNameTextView;
    private TextView mExplanationTextView;
    private TextView mDateTextView;
    private EditText mDateEditText;

    public FetchAPODData(ImageView imageView, TextView nameTextView,
                         TextView explanationTextView, TextView dateTextView, EditText dateEditText) {
        mImageView = imageView;
        mNameTextView = nameTextView;
        mExplanationTextView = explanationTextView;
        mDateTextView = dateTextView;
        mDateEditText =dateEditText;
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        String date = mDateEditText.getText().toString();
        String urlString = urls[0] + "&date=" + date;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                reader.close();
                inputStream.close();
                connection.disconnect();
                String jsonData = builder.toString();
                return new JSONObject(jsonData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            String imageUrl = result.getString("url");
            String name = result.getString("title");
            String explanation = result.getString("explanation");
            String date = result.getString("date");

            new DownloadImageTask(mImageView).execute(imageUrl);
            mNameTextView.setText(name);
            mExplanationTextView.setText(explanation);
            mDateTextView.setText(date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

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

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
