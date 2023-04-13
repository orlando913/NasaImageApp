package com.example.nasaimageapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class SListViewActivity extends BaseActivity {

    int customRowLayout = R.layout.row_layout;
    private ListView listView;
    private ImageInfoAdapter adapter;
    private List<ImageInfo> imageInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slist_view);

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

        // Add the fragment to the activity

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameL, new BlankFragment());
        transaction.addToBackStack(null); // Add the transaction to the back stack
        transaction.commit();


        // get the list of ImageInfo objects from the Intent
        String imageInfoListJson = getIntent().getStringExtra("imageInfoList");
        List<ImageInfo> imageInfoList = new Gson().fromJson(imageInfoListJson, new TypeToken<List<ImageInfo>>(){}.getType());




    }
    List<String> getImageFileNames() {
        List<String> fileNames = new ArrayList<>();
        File imagePath = new File(getExternalFilesDir(null), "images");
        if (imagePath.exists() && imagePath.isDirectory()) {
            File[] files = imagePath.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".png"); // filter only PNG files
                }
            });
            if (files != null && files.length > 0) {
                for (File file : files) {
                    fileNames.add(file.getName());
                }
            }
        }
        return fileNames;
    }


    // define the custom adapter
    private class ImageInfoAdapter extends ArrayAdapter<ImageInfo> {

        private int layoutResource;

        public ImageInfoAdapter(Context context, int layoutResource, List<ImageInfo> imageInfoList) {
            super(context, 0, imageInfoList);
            this.layoutResource = layoutResource;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
            }

            // get the ImageInfo object at the specified position
            ImageInfo imageInfo = getItem(position);

            // set the text and image for the custom row layout
            TextView nameTextView = convertView.findViewById(R.id.fotoname);

            nameTextView.setText(imageInfo.getName());


            return convertView;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.item1:
                message = "Touch any element from the list to display the image.";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        String message = null;

        switch (item.getItemId()) {
            case R.id.Home: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.Slist: {
                Intent intent = new Intent(this, SListViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.IDown: {
                Intent intent = new Intent(this, ImageDownActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.view:
                Intent intent = new Intent(this, VIewerActivity.class);
                startActivity(intent);
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}