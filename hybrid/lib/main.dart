import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _content = "None";
  @override
  Widget build(BuildContext context) {
    return Container(
        child: Center(
      child: Text("$_content", textDirection: TextDirection.ltr),
    ));
  }
}
