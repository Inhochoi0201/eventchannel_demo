import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: const EventChannelDemo()
    );
  }
}

class EventChannelDemo extends StatefulWidget {
  const EventChannelDemo({Key? key}) : super(key: key);

  @override
  State<EventChannelDemo> createState() => _EventChannelDemoState();
}

class _EventChannelDemoState extends State<EventChannelDemo> {
  static const EventChannel streamX = EventChannel('eventSample/x');
  static const MethodChannel channel = MethodChannel('methodSample/value');

  Stream<double> rotateXStream = const Stream.empty();

  Stream<double> exampleStreamX(){
    rotateXStream = streamX.receiveBroadcastStream().map<double>((event) => event);
    return rotateXStream;
  }
  static const EventChannel streamY = EventChannel('eventSample/y');
  Stream<double> rotateYStream = const Stream.empty();

  Stream<double> exampleStreamY(){
    rotateYStream = streamY.receiveBroadcastStream().map<double>((event) => event);
    return rotateYStream;
  }

  static const EventChannel streamZ = EventChannel('eventSample/z');
  Stream<double> rotateZStream = const Stream.empty();

  Stream<double> exampleStreamZ(){
    rotateZStream = streamZ.receiveBroadcastStream().map<double>((event) => event);
    return rotateZStream;
  }

  Future<bool> init() async{
    return await channel.invokeMethod('init');
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    init();
  }

  @override
  Widget build(BuildContext context) {
    SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp,DeviceOrientation.portraitDown]);
    return Scaffold(
        appBar: AppBar(
          title: const Text('EventChannel Demo'),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            StreamBuilder(
              stream: exampleStreamX(),
              builder: (context, snapshot) {
                return Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text('기울기 x : ${snapshot.data}')
                    ],
                  ),
                );
              }
            ),
            StreamBuilder(
                stream: exampleStreamY(),
                builder: (context, snapshot) {
                  return Center(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text('기울기 y : ${snapshot.data}')
                      ],
                    ),
                  );
                }
            ),
            StreamBuilder(
                stream: exampleStreamZ(),
                builder: (context, snapshot) {
                  return Center(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text('기울기 z : ${snapshot.data}')
                      ],
                    ),
                  );
                }
            ),
          ],
        ));
  }
}
