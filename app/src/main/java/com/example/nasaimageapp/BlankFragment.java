package com.example.nasaimageapp;

import android.content.Intent;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

public class BlankFragment extends Fragment {
    private List<ImageInfo> imageInfoList;
    private int customRowLayout;


    public static BlankFragment newInstance(List<ImageInfo> imageInfoList) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString("imageInfoList", new Gson().toJson(imageInfoList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String imageInfoListJson = getArguments().getString("imageInfoList");
            imageInfoList = new Gson().fromJson(imageInfoListJson, new TypeToken<List<ImageInfo>>(){}.getType());
        }
        if (getActivity() == null) {
            return;
        }
        // initialize the customRowLayout variable here
        customRowLayout = ((SListViewActivity) getActivity()).customRowLayout;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        // get the list view and set the adapter
        ListView listView = view.findViewById(R.id.listView);

        // check if the imageInfoList is null or empty
        if (imageInfoList == null || imageInfoList.isEmpty()) {
            // show an error message in the list view
            TextView textView = new TextView(getActivity());
            textView.setText(R.string.noimage);
            listView.setEmptyView(textView);
            return view;
        }

        ImageInfoAdapter adapter = new ImageInfoAdapter(getActivity(), customRowLayout, imageInfoList);
        listView.setAdapter(adapter);

        // set the onItemClickListener for the list view
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            // start the ImageViewActivity with the image name as an extra
            ImageInfo imageInfo = (ImageInfo) parent.getItemAtPosition(position);
            Intent intent = new Intent(getActivity(), ImageView.class);
            intent.putExtra("imageName", imageInfo.getName());
            startActivity(intent);
        });

        return view;
    }
}