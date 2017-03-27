package com.aarondevelops.endangeredeightball;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Aaron on 3/27/2017.
 */

public class ImageAdapter extends BaseAdapter
{
    private Context appContext;

    private int[] picReferences = {R.drawable.eagle, R.drawable.elephant, R.drawable.gorilla,
            R.drawable.panda, R.drawable.panther, R.drawable.polar};

    public ImageAdapter(Context appContext) {this.appContext = appContext;}

    @Override
    public int getCount()
    {
        return picReferences.length;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView picture = new ImageView(appContext);
        picture.setImageResource(picReferences[position]);
        picture.setScaleType(ImageView.ScaleType.FIT_XY);
        picture.setLayoutParams(new GridView.LayoutParams(330, 300));
        return picture;
    }
}
