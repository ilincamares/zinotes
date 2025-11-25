import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../model/hanzi.dart';
import '../notifier/list_notifier.dart';

class EditScreen extends StatelessWidget {
  final String hanziId;

  const EditScreen({super.key, required this.hanziId});

  @override
  Widget build(BuildContext context) {
    final notifier = context.watch<ListNotifier>();
    Hanzi? hanzi = notifier.getHanziById(hanziId);
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: Container(
          decoration: BoxDecoration(image: DecorationImage(image: AssetImage('assets/images/background.jpeg'))),
          child: HanziEditView(hanzi)
      ),
    );
  }
}

class HanziEditView extends StatefulWidget{
  final Hanzi? hanzi;

  const HanziEditView(this.hanzi, {super.key});

  @override
  State<HanziEditView> createState() => _HanziEditViewState();
}

class _HanziEditViewState extends State<HanziEditView> {
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    if (widget.hanzi == null) {
      return Text('Hanzi not found');
    }

    String _pinyin = widget.hanzi!.pinyin;
    String _tones = widget.hanzi!.tones.join(',');
    String? _radicalNumber = widget.hanzi!.radicalNumber?.toString();
    String? _strokeCount = widget.hanzi!.strokeCount?.toString();
    String? _hskLevel = widget.hanzi!.hskLevel?.toString();
    String? _definitions = widget.hanzi!.definitions?.join(',');

    final notifier = context.watch<ListNotifier>();


    return Container(
        decoration: BoxDecoration(image: DecorationImage(
          image: AssetImage('assets/images/vertical scroll.png'),
          fit: BoxFit.fitHeight,
        )),
        child: Padding(
          padding: const EdgeInsets.fromLTRB(80,20,80,20),
          child: Form(
            key: _formKey,
            autovalidateMode: AutovalidateMode.onUserInteraction,
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
                    initialValue: _pinyin,
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
                    initialValue: _tones,
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
                    initialValue: _radicalNumber,
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
                    initialValue: _strokeCount,
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
                    initialValue: _hskLevel,
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
                    initialValue: _definitions,
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
                            icon: Icon(Icons.check),
                            onPressed: () {
                              if (_formKey.currentState!.validate()) {
                                _formKey.currentState!.save();
                                notifier.updateHanzi(
                                    Hanzi(
                                        id: widget.hanzi!.id,
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
          ),
        )
    );
  }
}