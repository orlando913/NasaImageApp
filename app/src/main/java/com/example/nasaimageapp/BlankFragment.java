package com.example.nasaimageapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class BlankFragment extends Fragment {
    private ImageView mImageView;
    private TextView mNameTextView;
    private TextView mDateTextView;
    private TextView explanation;

    public static BlankFragment newInstance(String name, String height, String mass) {
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("height", height);
        args.putString("mass", mass);

        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);



        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name");
            String height = args.getString("height");
            String mass = args.getString("mass");


        }

        return view;

    }

}
