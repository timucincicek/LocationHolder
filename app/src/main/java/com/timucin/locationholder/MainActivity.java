package com.timucin.locationholder;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Location> list;
    static LocationListAdapter adapter = null;
    static ArrayList<String> names = new ArrayList<String>(); //original
    static ArrayList<LatLng> locations = new ArrayList<LatLng>(); //original
    String nameFromDatabase;
    LatLng locationFromDatabase;
    byte[] image;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_location, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_place) {
            Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView= (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        try {
            MapsActivity.database = this.openOrCreateDatabase("Places",MODE_PRIVATE,null);
            Cursor cursor = MapsActivity.database.rawQuery("SELECT * FROM places",null);
            int nameIx = cursor.getColumnIndex("name");
            int latitudeIx = cursor.getColumnIndex("latitude");
            int longitudeIx = cursor.getColumnIndex("longitude");
            while (cursor.moveToNext()) {
                    nameFromDatabase = cursor.getString(nameIx);
                    String latitudeFromDatabase = cursor.getString(latitudeIx);
                    String longitudeFromDatabase = cursor.getString(longitudeIx);
                    image = cursor.getBlob(3);
                    // names.add(nameFromDatabase);
                    Double l1 = Double.parseDouble(latitudeFromDatabase);
                    Double l2 = Double.parseDouble(longitudeFromDatabase);
                    //   System.out.println("coordinates:"+l1+","+l2);
                    locationFromDatabase = new LatLng(l1, l2);
                    names.add(nameFromDatabase);
                    locations.add(locationFromDatabase);
                    list.add(new Location(nameFromDatabase, image));
                }
                System.out.println(names);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new LocationListAdapter(this, R.layout.location_items, list);
        gridView.setAdapter(adapter);
        System.out.println(names);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] items = {"View Location", "Update","Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0)
                        {
                          Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                          intent.putExtra("position",position);
                          intent.putExtra("info","old");
                          startActivity(intent);
                        }
                    }
                });
                dialog.show();
            }
        });
        }

        }

