import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _content = "None";

  @override
  void initState() {
    _getBatteryLevel();
  }

  static const MethodChannel methodChannel = MethodChannel('seniorzhai.io');

  Future<void> _getBatteryLevel() async {
    String batteryLevel;
    try {
      final int result = await methodChannel.invokeMethod('getBatteryLevel');
      batteryLevel = 'Battery level: $result%.';
    } on PlatformException {
      batteryLevel = 'Failed to get battery level.';
    }
    setState(() {
      _content = batteryLevel;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
        child: Center(
      child: Text("$_content", textDirection: TextDirection.ltr),
    ));
  }
}
