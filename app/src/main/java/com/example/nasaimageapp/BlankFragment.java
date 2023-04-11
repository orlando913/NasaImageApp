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
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

public class BlankFragment extends Fragment {
    private List<ImageInfo> imageInfoList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        // Find the ListView inside the fragment
        ListView listView = rootView.findViewById(R.id.listView);

        // Set the adapter
        ImageInfoAdapter adapter = new ImageInfoAdapter(getActivity(), R.layout.row_layout, imageInfoList);
        listView.setAdapter(adapter);


        return rootView;


    }
}