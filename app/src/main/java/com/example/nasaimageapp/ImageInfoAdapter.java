package com.example.nasaimageapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ImageInfoAdapter extends ArrayAdapter<ImageInfo> {
    private Context mContext;
    private int mResource;

    public ImageInfoAdapter(Context context, int resource, List<ImageInfo> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        ImageInfo imageInfo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }
        // Lookup view for data population
        TextView nameTextView = convertView.findViewById(R.id.fotoname);

        // Populate the data into the template view using the data object
        nameTextView.setText(imageInfo.getName());

        return convertView;
    }
}
