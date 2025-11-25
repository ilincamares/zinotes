import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../model/hanzi.dart';
import '../notifier/list_notifier.dart';

class AddScreen extends StatelessWidget {
  const AddScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        resizeToAvoidBottomInset: false,
        body: Container(
            decoration: BoxDecoration(image: DecorationImage(image: AssetImage('assets/images/background.jpeg'))),
            child: HanziAddView()
        )
    );
  }
}

class HanziAddView extends StatefulWidget{
  const HanziAddView({super.key});

  @override
  State<HanziAddView> createState() => _HanziAddViewState();
}

class _HanziAddViewState extends State<HanziAddView> {
  final _formKey = GlobalKey<FormState>();

  String _pinyin = '', _tones= '';
  String? _radicalNumber, _strokeCount, _hskLevel, _definitions;

  @override
  Widget build(BuildContext context) {
    final notifier = context.watch<ListNotifier>();

    return Form(
      key: _formKey,
      autovalidateMode: AutovalidateMode.onUserInteraction,
      child: Container(
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
                    textAlign: TextAlign.center,
                    style: Theme.of(context).textTheme.bodyLarge,
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter Pinyin';
                      }
                      return null;
                    },
                    onSaved: (value) {
                      _pinyin = value!;
                    },
                  ),
                  TextFormField(
                    decoration: InputDecoration(
                      floatingLabelAlignment: FloatingLabelAlignment.center,
                      border: OutlineInputBorder(),
                      labelText: 'Tones',
                      alignLabelWithHint: true,
                    ),
                    textAlign: TextAlign.center,
                    style: Theme.of(context).textTheme.bodyLarge,
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter tones';
                      }
                      final parts = value.split(',');

                      for (var part in parts) {
                        if (int.tryParse(part.trim()) == null) {
                          return 'Must be numbers separated by commas';
                        }
                      }
                      return null;
                    },
                    onSaved: (value) {
                      _tones = value!;
                    },
                  ),
                  TextFormField(
                    decoration: InputDecoration(
                      floatingLabelAlignment: FloatingLabelAlignment.center,
                      border: OutlineInputBorder(),
                      labelText: 'Radical Number',
                      alignLabelWithHint: true,
                    ),
                    textAlign: TextAlign.center,
                    style: Theme.of(context).textTheme.bodyLarge,
                    validator: (value) {
                      if (value != null && value.isNotEmpty) {
                        if (int.tryParse(value) == null) {
                          return 'Must be a number';
                        }
                      }
                      return null;
                    },
                    onSaved: (value) {
                      _radicalNumber = value;
                    },
                  ),
                  TextFormField(
                    decoration: InputDecoration(
                      floatingLabelAlignment: FloatingLabelAlignment.center,
                      border: OutlineInputBorder(),
                      labelText: 'Stroke Count',
                      alignLabelWithHint: true,
                    ),
                    textAlign: TextAlign.center,
                    style: Theme.of(context).textTheme.bodyLarge,
                    validator: (value) {
                      if (value != null && value.isNotEmpty) {
                        if (int.tryParse(value) == null) {
                          return 'Must be a number';
                        }
                      }
                      return null;
                    },
                    onSaved: (value) {
                      _strokeCount = value;
                    },
                  ),
                  TextFormField(
                    decoration: InputDecoration(
                      floatingLabelAlignment: FloatingLabelAlignment.center,
                      border: OutlineInputBorder(),
                      labelText: 'HSK Level',
                      alignLabelWithHint: true,
                    ),
                    textAlign: TextAlign.center,
                    style: Theme.of(context).textTheme.bodyLarge,
                    validator: (value) {
                      if (value != null && value.isNotEmpty) {
                        if (int.tryParse(value) == null) {
                          return 'Must be a number';
                        }
                      }
                      return null;
                    },
                    onSaved: (value) {
                      _hskLevel = value;
                    },
                  ),
                  TextFormField(
                    decoration: InputDecoration(
                      floatingLabelAlignment: FloatingLabelAlignment.center,
                      border: OutlineInputBorder(),
                      labelText: 'Definitions',
                      alignLabelWithHint: true,
                    ),
                    textAlign: TextAlign.center,
                    style: Theme.of(context).textTheme.bodyLarge,
                    onSaved: (value) {
                      _definitions = value;
                    },
                  ),
                  Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        IconButton(
                          icon: Icon(Icons.cancel),
                          onPressed: () {
                            _formKey.currentState!.reset();
                            Navigator.pop(context);
                          },
                          color: Colors.red,
                        ),
                        IconButton(
                            icon: Icon(Icons.add),
                            onPressed: () {
                              if (_formKey.currentState!.validate()) {
                                _formKey.currentState!.save();
                                notifier.addHanzi(
                                    Hanzi(
                                        id: DateTime
                                            .now()
                                            .millisecondsSinceEpoch
                                            .toString(),
                                        pinyin: _pinyin,
                                      tones: _tones.trim().isEmpty
                                          ? []
                                          : _tones.split(',').map((e) => int.parse(e.trim())).toList(),

                                      radicalNumber: (_radicalNumber != null && _radicalNumber!.isNotEmpty)
                                          ? int.parse(_radicalNumber!)
                                          : null,

                                      strokeCount: (_strokeCount != null && _strokeCount!.isNotEmpty)
                                          ? int.parse(_strokeCount!)
                                          : null,

                                      hskLevel: (_hskLevel != null && _hskLevel!.isNotEmpty)
                                          ? int.parse(_hskLevel!)
                                          : null,

                                      definitions: (_definitions != null && _definitions!.isNotEmpty)
                                          ? _definitions!.split(',')
                                          : null,
                                    )
                                );
                                Navigator.pop(context);
                              }
                            }
                        )
                      ]
                  )
                ]
            ),
          )
      ),
    );
  }
}