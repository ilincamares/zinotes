import '../model/hanzi.dart';

class HanziRepository {
  final List<Hanzi> _hanziList = [
    Hanzi(
        id: "1", pinyin: "zi", tones: [4, 0], radicalNumber: 39, strokeCount: 6,
        hskLevel: 1, definitions: ["character", "letter", "word"]
    ),
    Hanzi(
        id: "2", pinyin:"cha", tones: [2], radicalNumber: 140, strokeCount: 9,
        hskLevel: 1, definitions: ["tea"]
    )
  ];

  List<Hanzi> getHanziList() {
    return _hanziList;
  }

  void addHanzi(Hanzi hanzi) {
    _hanziList.add(hanzi);
  }

  void removeHanzi(Hanzi hanzi) {
    int index = _hanziList.indexOf(hanzi);
    if (index != -1) {
      _hanziList.remove(hanzi);
    }
  }

  void updateHanzi(Hanzi hanzi) {
    int index = _hanziList.indexWhere((item) => item.id == hanzi.id);
    if (index != -1) {
      _hanziList[index] = hanzi;
    }
    else {
      _hanziList.add(hanzi);
    }
  }

  Hanzi? getHanziById(String id) {
    for (Hanzi hanzi in _hanziList) {
      if (hanzi.id == id) {
        return hanzi;
      }
    }
    return null;
  }

}