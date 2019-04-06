package com.timucin.locationholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LocationListAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<Location> locationsList;
    public LocationListAdapter(Context context, int layout, ArrayList<Location> locationsList) {
        this.context = context;
        this.layout = layout;
        this.locationsList =locationsList;
    }
    @Override
    public int getCount() {
        return locationsList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationsList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView txtName ;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtLocationName);
            holder.imageView = (ImageView) row.findViewById(R.id.imgLocation);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }
        Location location = locationsList.get(position);

        holder.txtName.setText(location.getName());
        byte[] locationImage = location.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(locationImage, 0, locationImage.length);
        holder.imageView.setImageBitmap(bitmap);
        return row;
    }

}
