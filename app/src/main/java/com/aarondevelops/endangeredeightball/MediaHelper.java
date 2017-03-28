package com.aarondevelops.endangeredeightball;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MediaHelper extends Fragment
{
    public static final String MEDIA_HELPER_TAG = "Media Helper FragTag";

    private Context appContext;
    private Integer mediaID;

    private MediaPlayer mediaPlayer;

    public MediaHelper()
    {
        super();
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        appContext = getActivity().getApplicationContext();
        setRetainInstance(true);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        pauseMedia();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        playMedia();
    }

    public void playMedia()
    {
        if(mediaID == null)
        {
            Log.e(this.getClass().getName(), "MediaHelper not initialized.");
            return;
        }

        if(mediaPlayer == null)
        {
            new MediaHelperLoader().execute();
            return;
        }

        if(mediaPlayer.isPlaying())
        {
            return;
        }

        mediaPlayer.start();
        Log.i(this.getClass().getName(), "Playing track.");
    }

    public void pauseMedia()
    {
        if(mediaPlayer == null)
        {
            Log.d(this.getClass().getName(), "MediaHelper not playing anything.");
            return;
        }

        mediaPlayer.pause();
        Log.i(this.getClass().getName(), "Pausing track.");
    }

    public void setMediaID(int ID)
    {
        this.mediaID = ID;
    }

    class MediaHelperLoader extends AsyncTask<Object, Object, Void>
    {
        @Override
        protected Void doInBackground(Object... params)
        {
            mediaPlayer = MediaPlayer.create(appContext, mediaID);
            mediaPlayer.setLooping(true);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            playMedia();
        }
    }
}
