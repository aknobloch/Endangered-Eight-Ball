package com.aarondevelops.endangeredeightball;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements OrientationFragment.SensorHandlerEvents
{
    private ImageDisplayHandler displayManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        displayManager = new ImageDisplayHandler(this,
                                            (ImageView) findViewById(R.id.mainPic));
        gridView.setAdapter(displayManager);

        initializeMusicFragment();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        MessageHelper.initializeSpeaker(this);
        initializeOrientationFragment();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MessageHelper.shutdownSpeaker();
    }

    @Override
    public void onFacingDownward()
    {
        displayManager.randomizeMainDisplay();
    }

    @Override
    public void onFacingUpward()
    {
        MessageHelper.makeSpeech(this, displayManager.getMainDisplayNickname());
    }

    private void initializeMusicFragment()
    {
        BackgroundMediaFragment backgroundMediaFragment = new BackgroundMediaFragment();
        backgroundMediaFragment.setMediaID(R.raw.mists_of_time);

        bindFragment(backgroundMediaFragment, BackgroundMediaFragment.MEDIA_HELPER_TAG);
    }

    private void initializeOrientationFragment()
    {
        OrientationFragment orientationFragment = new OrientationFragment();
        orientationFragment.registerListener(this);

        bindFragment(orientationFragment, OrientationFragment.ORIENTATION_FRAGMENT_TAG);
    }

    public void bindFragment(Fragment bindingFragment, String tag)
    {
        FragmentManager fragmentManager = getFragmentManager();

        if(fragmentManager.findFragmentByTag(tag) != null)
        {
            // fragment already created
            return;
        }

        fragmentManager.beginTransaction()
                .add(bindingFragment, tag)
                .commit();

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
