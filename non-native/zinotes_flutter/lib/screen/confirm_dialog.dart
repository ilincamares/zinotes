import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../notifier/list_notifier.dart';

class ConfirmDialog extends StatelessWidget {
  final String hanziId;

  const ConfirmDialog({super.key, required this.hanziId});

  @override
  Widget build(BuildContext context) {
    final notifier = context.watch<ListNotifier>();
    final hanzi = notifier.getHanziById(hanziId);

    return Dialog(
      backgroundColor: Colors.transparent,

      child: Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/horizontal_scroll_red.png'),
            fit: BoxFit.fill,
          ),
        ),

        child: Padding(
          padding: const EdgeInsets.all(20),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text(
                  'Are you sure you want to delete this hanzi?',
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 15),
                textAlign: TextAlign.center,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  TextButton(
                    style: TextButton.styleFrom(
                      backgroundColor: Colors.grey,
                    ),
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    child: const Text(
                        'No',
                      style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
                    ),
                  ),
                  TextButton(
                    style: TextButton.styleFrom(
                      backgroundColor: Colors.red,
                    ),
                    onPressed: () {
                      if (hanzi != null) {
                        notifier.removeHanzi(hanzi);
                        Navigator.pop(context);
                        Navigator.pop(context);
                      }
                    },
                    child: const Text(
                    'Yes',
                    style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
                  ),
                  ),
                ],
              )
            ],
          ),
        ),
      ),
    );
  }

}