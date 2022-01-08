import 'package:flutter/material.dart';

class HelloCard extends StatelessWidget {
  const HelloCard({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Holidays"),
      ),
      body: Column(
        children: [
          Stack(
          alignment: Alignment.topCenter,
          children: <Widget>[
            //Text("Hello")
            _getCard(),
            _getAvatar()
          ],
        ),
          _getSecondCard()]
      ),
    );
  }
}


Container _getCard() {
  return Container(
    width:350,
    height: 200,
    margin: EdgeInsets.all(50),
    decoration: BoxDecoration(
      color: Colors.blueAccent,
      borderRadius: BorderRadius.circular(26),

    ),
    child: Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Text("Hello, Yuliya!",
          style: TextStyle(fontSize: 24,
              color:Colors.greenAccent),),
        Text("Happy New Year!!!!"),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.two_k_rounded),
            Icon(Icons.ac_unit),
            Icon(Icons.two_k_rounded),
            Icon(Icons.two_k_rounded),
          ],
        )
      ],
    ),
  );
}

Container _getAvatar(){
  return Container(
    width:100,
    height: 100,
    decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.all(Radius.circular(50.0)),
        border: Border.all(color:Colors.orange,width: 1.2),
        image: DecorationImage(image: NetworkImage("https://pbs.twimg.com/media/B8hpA5tCcAE0GK_.jpg"),
            fit:BoxFit.cover)
    ),
  );

}

Container _getSecondCard() {
  return Container(
    width:350,
    height: 200,
    margin: EdgeInsets.all(50),
    decoration: BoxDecoration(
      color: Colors.redAccent,
      borderRadius: BorderRadius.circular(26),
      border: Border.all(
        color: Colors.yellowAccent,
        width: 2.0,
      ),
        boxShadow: const [
          BoxShadow(
              color: Colors.orangeAccent,
              blurRadius: 2.0,
              offset: Offset(2.0,2.0)
          )
        ],
      gradient: LinearGradient(
          colors: [
            Colors.red,
            Colors.redAccent
          ]
      ),

    ),
    child: Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Text("Merry Christmas!!!",
          style: TextStyle(fontSize: 24,
              color:Colors.amberAccent),),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.ac_unit, color: Colors.white),
            Icon(Icons.auto_awesome_outlined, color: Color.fromRGBO(255, 223, 0, 0.8)),
            Icon(Icons.ac_unit, color: Colors.white),
            Icon(Icons.auto_awesome_outlined, color: Color.fromRGBO(255, 223, 0, 0.8)),
          ],
        )
      ],
    ),
  );
}

