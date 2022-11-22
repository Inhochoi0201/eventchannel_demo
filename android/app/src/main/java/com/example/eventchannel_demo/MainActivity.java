package com.example.eventchannel_demo;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    public static final String STREAMX = "eventSample/x";
    public static final String STREAMY = "eventSample/y";
    public static final String STREAMZ = "eventSample/z";
    public static final String CHANNEL = "methodSample/value";
    private RotateX rotateX = new RotateX();
    private RotateY rotateY = new RotateY();
    private RotateZ rotateZ = new RotateZ();
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL).setMethodCallHandler(
                (call, result) -> {
                    if (call.method.equals("init")){
                        rotateX.init(this.getContext());
                        rotateY.init(this.getContext());
                        rotateZ.init(this.getContext());
                        result.success(true);
                        return;
                    }
                    result.notImplemented();
                    return;
                }
        );

        new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), STREAMX).
                setStreamHandler(rotateX);
        new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), STREAMY).
                setStreamHandler(rotateY);
        new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), STREAMZ).
                setStreamHandler(rotateZ);
    }
}
