package com.example.veronica.rainorshine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by Veronica on 2016-10-30.
 */

public class ImageAdapter extends ArrayAdapter<CameraInput> {
    Context context;
    int layoutResourceId;
    private ArrayList<CameraInput> data = new ArrayList<CameraInput>();

    public ImageAdapter(Context context, int layoutResourceId, ArrayList<CameraInput> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ImageHolder();
            holder.caption = (TextView)row.findViewById(R.id.caption);
            holder.weather = (TextView)row.findViewById(R.id.weather);
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }

        CameraInput input = data.get(position);
        holder.caption.setText(input.caption);
        holder.weather.setText(input.weatherTemp + "°C, " + input.weatherCondition);

        byte[] outImage=input.image;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);
        return row;
    }

    // overriding this method actually lets us easily access the actual ID of the CameraInput object
    @Override
    public long getItemId(int position) {
        return getItem(position).getID();
    }

    static class ImageHolder
    {
        ImageView imgIcon;
        TextView caption;
        TextView weather;
    }
}
