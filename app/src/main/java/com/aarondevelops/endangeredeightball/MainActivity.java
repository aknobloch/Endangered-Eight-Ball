package com.aarondevelops.endangeredeightball;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

//TODO: ScrollView sufficient?
//TODO: Auto imports?
public class MainActivity extends AppCompatActivity
{

    private MediaHelper mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        ImageDisplayHandler displayManager = new ImageDisplayHandler(this,
                                            (ImageView) findViewById(R.id.mainPic));
        gridView.setAdapter(displayManager);

        MessageHelper.initializeSpeaker(this);

        initializeMusic();
    }

    public void initializeMusic()
    {
        FragmentManager fragmentManager = getFragmentManager();

        if(fragmentManager.findFragmentByTag(MediaHelper.MEDIA_HELPER_TAG) != null)
        {
            // media helper already created
            return;
        }

        if(mediaPlayer == null)
        {
            mediaPlayer = new MediaHelper();
            mediaPlayer.setMediaID(R.raw.mists_of_time);
        }

        fragmentManager.beginTransaction()
                .add(mediaPlayer, MediaHelper.MEDIA_HELPER_TAG)
                .commit();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if(mediaPlayer != null)
        {
            mediaPlayer.pauseMedia();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(mediaPlayer != null)
        {
            mediaPlayer.playMedia();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about)
        {
            showAboutMessage();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAboutMessage()
    {
        new AlertDialog.Builder(this)

                .setMessage(getString(R.string.about_dialog))

                .setPositiveButton(getString(R.string.about_confirmation),
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                            }
                        })

                .show();
    }
}
