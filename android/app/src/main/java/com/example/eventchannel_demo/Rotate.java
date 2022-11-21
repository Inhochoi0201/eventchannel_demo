package com.example.eventchannel_demo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import io.flutter.plugin.common.EventChannel;

class Rotate implements EventChannel.StreamHandler, SensorEventListener {
    private SensorManager sm;
    private Sensor s;
    private float x = 0;
    private EventChannel.EventSink rotateSink;

    public void init(Context context){
        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x = sensorEvent.values[0];
        if(rotateSink != null){
            rotateSink.success(x);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        rotateSink = events;
    }

    @Override
    public void onCancel(Object arguments) {
        rotateSink = null;
    }
}

