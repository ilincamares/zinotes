import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../model/hanzi.dart';
import '../notifier/list_notifier.dart';
import 'confirm_dialog.dart';
import 'edit_screen.dart';

class DetailScreen extends StatelessWidget {
  final String hanziId;

  const DetailScreen({super.key, required this.hanziId});

  @override
  Widget build(BuildContext context) {
      return Scaffold(
          body: Container(
            decoration: BoxDecoration(image: DecorationImage(image: AssetImage('assets/images/background.jpeg'))),
            child: HanziDetailView(hanziId)
          )
      );
  }
}

class HanziDetailView extends StatelessWidget{
  final String hanziId;

  const HanziDetailView(this.hanziId, {super.key});

  @override
  Widget build(BuildContext context) {
    final notifier = context.watch<ListNotifier>();
    Hanzi? hanzi = notifier.getHanziById(hanziId);

    if (hanzi == null) {
      return Text('Hanzi not found');
    }

    final ValueKey<Hanzi> key = ValueKey<Hanzi>(hanzi);

    return Container(
      key: key,
      decoration: BoxDecoration(image: DecorationImage(
        image: AssetImage('assets/images/vertical scroll.png'),
        fit: BoxFit.fitHeight,
      )),
      child: Padding(
        padding: const EdgeInsets.fromLTRB(80,20,80,20),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            TextFormField(
              decoration: InputDecoration(
                floatingLabelAlignment: FloatingLabelAlignment.center,
                border: OutlineInputBorder(),
                labelText: 'Pinyin',
                alignLabelWithHint: true,
              ),
              initialValue: hanzi.pinyin,
              readOnly: true,
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.bodyLarge,
            ),
            TextFormField(
              decoration: InputDecoration(
                floatingLabelAlignment: FloatingLabelAlignment.center,
                border: OutlineInputBorder(),
                labelText: 'Tones',
                alignLabelWithHint: true,
              ),
              initialValue: hanzi.tones.join(','),
              readOnly: true,
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.bodyLarge,
            ),
            TextFormField(
              decoration: InputDecoration(
                floatingLabelAlignment: FloatingLabelAlignment.center,
                border: OutlineInputBorder(),
                labelText: 'Radical Number',
                alignLabelWithHint: true,
              ),
              initialValue: hanzi.radicalNumber?.toString(),
              readOnly: true,
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.bodyLarge,
            ),
            TextFormField(
              decoration: InputDecoration(
                floatingLabelAlignment: FloatingLabelAlignment.center,
                border: OutlineInputBorder(),
                labelText: 'Stroke Count',
                alignLabelWithHint: true,
              ),
              initialValue: hanzi.strokeCount?.toString(),
              readOnly: true,
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.bodyLarge,
            ),
            TextFormField(
              decoration: InputDecoration(
                floatingLabelAlignment: FloatingLabelAlignment.center,
                border: OutlineInputBorder(),
                labelText: 'HSK Level',
                alignLabelWithHint: true,
              ),
              initialValue: hanzi.hskLevel?.toString(),
              readOnly: true,
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.bodyLarge,
            ),
            TextFormField(
              decoration: InputDecoration(
                floatingLabelAlignment: FloatingLabelAlignment.center,
                border: OutlineInputBorder(),
                labelText: 'Definitions',
                alignLabelWithHint: true,
              ),
              initialValue: hanzi.definitions?.join(','),
              readOnly: true,
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.bodyLarge,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                IconButton(
                  icon: Icon(Icons.delete),
                  onPressed: () {
                    showDialog(
                      context: context,
                      barrierDismissible: true,
                      builder: (BuildContext context) {
                        return ConfirmDialog(hanziId: hanzi.id);
                      },
                    );
                  },
                  color: Colors.red,
                ),
                IconButton(
                    icon: Icon(Icons.edit),
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => EditScreen(hanziId: hanzi.id)),
                      );
                    }
                )
              ]
            )
          ]
        ),
      )
    );
  }
}