class Hanzi{
  final String id;
  final String pinyin;
  final List<int> tones;
  final int? radicalNumber;
  final int? strokeCount;
  final int? hskLevel;
  final List<String>? definitions;

  Hanzi({required this.id, required this.pinyin, required this.tones, this.radicalNumber, this.strokeCount, this.hskLevel, this.definitions});
}