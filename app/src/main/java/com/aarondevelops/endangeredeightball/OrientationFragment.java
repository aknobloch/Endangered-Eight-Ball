package com.aarondevelops.endangeredeightball;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import java.util.ArrayList;

public class OrientationFragment extends Fragment implements SensorEventListener
{
    interface SensorHandlerEvents
    {
        public void onFacingDownward();
        public void onFacingUpward();
    }

    private final float mSensitivity = 0.8f;
    private final long lastUpdateTime;

    public static final String SENSOR_HANDLER_TAG = "Sensor Handler";

    private ArrayList<SensorHandlerEvents> sensorListeners;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    public OrientationFragment()
    {
        super();
        sensorListeners = new ArrayList<>();
        lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)
                getActivity().getApplicationContext().getSystemService(Context.SENSOR_SERVICE);

        // TODO: why are we using accelerometer and not GRAVITY, ORIENTATION or GYROSCOPE
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void registerListener(SensorHandlerEvents callbackListener)
    {
        sensorListeners.add(callbackListener);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER ||
                timeSinceLastUpdate() > 500)
        {
            return;
        }


    }

    private long timeSinceLastUpdate()
    {
        return System.currentTimeMillis() - lastUpdateTime;
    }


    @Override
    // not needed in context of current application
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
