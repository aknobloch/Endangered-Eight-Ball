package com.aarondevelops.endangeredeightball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationListener implements SensorEventListener
{
    public static final String ORIENTATION_FRAGMENT_TAG = "OrientationListener";

    interface OrientationEventListener
    {
        void onFacingDownward();
        void onFacingUpward();
    }

    private final float mSensitivity = 0.70f;
    private float mZGravityAverage;
    private boolean faceDownState = false;

    private OrientationEventListener sensorEventListener;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    // TODO: combine these into one? I.E. param must extend Context and implement sensorEventListener
    public OrientationListener(Context appContext, OrientationEventListener sensorEventListener)
    {
        mSensorManager = (SensorManager)
                appContext.getSystemService(Context.SENSOR_SERVICE);

        // TODO: why are we using accelerometer and not GEOMAGNETIC_ROTATION_VECTOR to getRotationMatrix
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        this.sensorEventListener = sensorEventListener;
    }

    public void pauseListener()
    {
        mSensorManager.unregisterListener(this);
    }

    public void resumeListener()
    {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
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
        sensorEventListener.onFacingDownward();
    }

    private void alertFaceUp()
    {
        sensorEventListener.onFacingUpward();
    }

    @Override
    // not needed in context of current application
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
