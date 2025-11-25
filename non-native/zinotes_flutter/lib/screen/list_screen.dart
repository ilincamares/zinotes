import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../model/hanzi.dart';
import '../notifier/list_notifier.dart';
import 'add_screen.dart';
import 'detail_screen.dart';

class ListScreen extends StatelessWidget {
  const ListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: BoxDecoration(image: DecorationImage(image: AssetImage('assets/images/background.jpeg'))),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            AddButton(),
            Flexible(
              child: HanziList(),
            ),
          ],
        ),
      )
    );
  }

}

class AddButton extends StatelessWidget {
  const AddButton({super.key});

  @override
  Widget build(BuildContext context) {
    // return GestureDetector(
    //   onTap: () {
    //     Navigator.push(
    //       context,
    //       MaterialPageRoute(builder: (context) => AddScreen())
    //     );
    //   },
    //   child: Image(
    //       image: AssetImage('assets/images/chinese coin.png'),
    //       width: 100,
    //       height: 100,
    //   ),
    // );
    return IconButton(
      icon: Image(
        image: AssetImage('assets/images/chinese coin.png'),
        width: 100,
        height: 100,
      ),
      onPressed: () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => AddScreen())
        );
      }
    );
  }
}

class HanziList extends StatelessWidget {
  const HanziList({super.key});

  @override
  Widget build(BuildContext context) {
    final notifier = context.watch<ListNotifier>();
    final hanziList = notifier.hanziList;

    return ListView.builder(
      shrinkWrap: true,
      itemCount: hanziList.length,
      itemBuilder: (context, index) {
        final hanzi = hanziList[index];
        return HanziCard(hanzi);
      },
    );
  }
}

class HanziCard extends StatelessWidget {
  final Hanzi hanzi;

  const HanziCard(this.hanzi, {super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 150,
      decoration: BoxDecoration(image: DecorationImage(
        image: AssetImage('assets/images/horizontal scroll.png'),
        fit: BoxFit.cover,
      )),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text('${hanzi.pinyin}${hanzi.tones.join(',')}',
          style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
          Text((hanzi.definitions != null && hanzi.definitions!.isNotEmpty)
              ? hanzi.definitions!.first
              : "",
          style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
          IconButton(
            icon: Image(
                image: AssetImage('assets/images/slanted-eye.png'),
                width: 40,
                height: 40,
            ),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => DetailScreen(hanziId: hanzi.id)),
              );
            }
          )
        ]
      ),
    );
  }
  
}