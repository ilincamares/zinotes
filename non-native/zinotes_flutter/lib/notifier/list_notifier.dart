import 'package:flutter/material.dart';
import 'package:zinotes_flutter/data/datasource.dart';

import '../model/hanzi.dart';

class ListNotifier extends ChangeNotifier {
  final HanziRepository _repository = HanziRepository();
  List<Hanzi> get hanziList => _repository.getHanziList();

  void addHanzi(Hanzi hanzi) {
    _repository.addHanzi(hanzi);
    notifyListeners();
  }

  void removeHanzi(Hanzi hanzi) {
    _repository.removeHanzi(hanzi);
    notifyListeners();
  }

  void updateHanzi(Hanzi hanzi) {
    _repository.updateHanzi(hanzi);
    notifyListeners();
  }

  Hanzi? getHanziById(String id) {
    return _repository.getHanziById(id);
  }

}