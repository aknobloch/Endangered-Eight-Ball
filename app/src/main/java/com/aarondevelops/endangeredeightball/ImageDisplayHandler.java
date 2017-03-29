package com.aarondevelops.endangeredeightball;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Random;

//  TODO: Is this well organized? View and controller, as well as handling gridview and mainview logic
public class ImageDisplayHandler extends BaseAdapter
{
    private Context appContext;

    private int[] picReferences = {R.drawable.eagle, R.drawable.elephant, R.drawable.gorilla,
            R.drawable.panda, R.drawable.panther, R.drawable.polar};
    private ImageView mainDisplay;

    public ImageDisplayHandler(Context appContext, ImageView mainDisplay)
    {
        this.appContext = appContext;
        this.mainDisplay = mainDisplay;
    }

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
        picture.setAdjustViewBounds(true);

        return picture;
    }

    public void randomizeMainDisplay()
    {
        Random ranGen = new Random();
        int randomSelection = ranGen.nextInt(picReferences.length);

        setMainDisplay(randomSelection);
    }

    public void setMainDisplay(int pictureReference)
    {
        this.mainDisplay.setImageResource(pictureReference);
    }
}
