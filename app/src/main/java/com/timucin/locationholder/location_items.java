package com.timucin.locationholder;

import android.content.Intent;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.timucin.locationholder.MapsActivity.database;
import static com.timucin.locationholder.MapsActivity.l1;
import static com.timucin.locationholder.MapsActivity.l2;

public class location_items extends AppCompatActivity {
    // static Bitmap bitmap;
    private EditText editText;
    private Button saveBtn,getImage;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_items);
        Intent intent = getIntent();
        String street=intent.getExtras().getString("nameData");
        editText = (EditText)findViewById(R.id.editText3);
        editText.setText(street);
        saveBtn= (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saving();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        getImage= (Button)findViewById(R.id.getimage);
        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView = (ImageView) findViewById(R.id.imgPlace);
                imageView.setImageResource(R.drawable.eiffel);
            }
        });
    }
    public void saving()
    {
        try {
            byte[] x=imageViewToByte(imageView);
            editText =(EditText)findViewById(R.id.editText3);
            String name = editText.getText().toString();
            String coord1 = l1.toString();
            String coord2 = l2.toString();
           // System.out.println("Coordinates are:"+coord1+coord2);
            database = this.openOrCreateDatabase("Places",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS places (name VARCHAR, latitude VARCHAR, longitude VARCHAR,image BLOB)");
            String toCompile = "INSERT INTO places (name, latitude, longitude,image) VALUES (?, ?, ?,?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(toCompile);
            sqLiteStatement.bindString(1,name);
            sqLiteStatement.bindString(2,coord1);
            sqLiteStatement.bindString(3,coord2);
            sqLiteStatement.bindBlob(4,x);
            sqLiteStatement.execute();
        } catch (Exception e) {
        }
    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
