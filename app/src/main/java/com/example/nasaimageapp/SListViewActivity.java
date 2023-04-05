package com.example.nasaimageapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SListViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slist_view);

        //Adapter for the listview
        List<ImageInfo> imageInfoList = new Gson().fromJson(getIntent().getStringExtra("imageInfoList"), new TypeToken<List<ImageInfo>>(){}.getType());
        ListView listView = findViewById(R.id.listView);
       ImageListAdapter adapter = new ImageListAdapter(this, imageInfoList);
        listView.setAdapter(adapter);

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


    }
    public class ImageListAdapter extends BaseAdapter {
        private Context context;
        private List<ImageInfo> imageInfoList;

        public ImageListAdapter(Context context, List<ImageInfo> imageInfoList) {
            this.context = context;
            this.imageInfoList = imageInfoList;
        }

        @Override
        public int getCount() {
            return imageInfoList.size();
        }

        @Override
        public ImageInfo getItem(int position) {
            return imageInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
            }
            TextView dateTextView = convertView.findViewById(R.id.item);
            ImageInfo imageInfo = getItem(position);
            dateTextView.setText(imageInfo.getDate());
            return convertView;
        }
    }
}