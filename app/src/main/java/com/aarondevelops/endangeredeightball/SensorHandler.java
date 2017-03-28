package com.aarondevelops.endangeredeightball;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Aaron on 3/28/2017.
 */

public class SensorHandler extends Fragment implements SensorEventListener
{
    interface SensorHandlerEvents
    {
        public void onFacingDownward();
        public void onFacingUpward();
    }

    public static final String SENSOR_HANDLER_TAG = "Sensor Handler Tag";

    private ArrayList<SensorHandlerEvents> sensorListeners;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    public SensorHandler()
    {
        super();
        sensorListeners = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)
                getActivity().getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void registerListener(SensorHandlerEvents callbackListener)
    {
        sensorListeners.add(callbackListener);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
