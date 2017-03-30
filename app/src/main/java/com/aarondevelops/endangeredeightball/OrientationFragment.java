package com.aarondevelops.endangeredeightball;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

//TODO: When the MainActivity is destroyed, the sensorListener decremented to zero. Why not null pointer?
public class OrientationFragment extends Fragment implements SensorEventListener
{
    public static final String ORIENTATION_FRAGMENT_TAG = "OrientationFragment";

    interface SensorHandlerEvents
    {
        void onFacingDownward();
        void onFacingUpward();
    }

    private final float mSensitivity = 0.70f;
    private float mZGravityAverage;
    private boolean faceDownState = false;

    private SensorHandlerEvents sensorListener;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    public OrientationFragment()
    {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)
                getActivity().getApplicationContext().getSystemService(Context.SENSOR_SERVICE);

        // TODO: why are we using accelerometer and not GEOMAGNETIC_ROTATION_VECTOR to getRotationMatrix
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void registerListener(SensorHandlerEvents callbackListener)
    {
        sensorListener = callbackListener;
        Log.d(ORIENTATION_FRAGMENT_TAG, "Registering listener " + callbackListener.hashCode() + " now is " + sensorListener.hashCode());
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
        {
            return;
        }

        updateZGravity(event.values[2]);
        updateZState();
    }

    private void updateZGravity(float rawZData)
    {
        mZGravityAverage = lowPass(rawZData);
    }

    private void updateZState()
    {
        // if not currently in face down state, but now face down
        if( ! faceDownState && mZGravityAverage < -9.0f)
        {
            alertFaceDown();
            faceDownState = true;
        }
        // if currenty in face down state, but now no longer facing down
        // -5.0 to ensure phone is fully "up"
        else if(faceDownState && mZGravityAverage > -5.0f)
        {
            alertFaceUp();
            faceDownState = false;
        }
    }

    private float lowPass(float rawZData)
    {
        return mZGravityAverage * mSensitivity + rawZData * (1 - mSensitivity);
    }

    private void alertFaceDown()
    {
        Log.d(ORIENTATION_FRAGMENT_TAG, "Registered as down. listening: " + sensorListener.hashCode());
        sensorListener.onFacingDownward();
    }

    private void alertFaceUp()
    {
        sensorListener.onFacingUpward();
    }

    @Override
    // not needed in context of current application
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
