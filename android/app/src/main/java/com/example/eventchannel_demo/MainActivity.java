package com.example.eventchannel_demo;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    public static final String STREAM = "eventSample/value";
    public static final String CHANNEL = "methodSample/value";
    private Rotate rotate = new Rotate();

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL).setMethodCallHandler(
                (call, result) -> {
                    if (call.method.equals("init")){
                        rotate.init(this.getContext());
                        result.success(true);
                        return;
                    }
                    result.notImplemented();
                    return;
                }
        );

        new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), STREAM).
                setStreamHandler(rotate);
    }
}
