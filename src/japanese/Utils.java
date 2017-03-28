package japanese;

public class Utils {
	public static String convertKana(String input) {
		if (input == null || input.length() == 0)
			return "";

		StringBuilder out = new StringBuilder();
		char ch = input.charAt(0);

		if (JapaneseCharacter.isHiragana(ch)) { // convert to hiragana to
												// katakana
			for (int i = 0; i < input.length(); i++) {
				out.append(JapaneseCharacter.toKatakana(input.charAt(i)));
			}
		} else if (JapaneseCharacter.isKatakana(ch)) { // convert to katakana to
														// hiragana
			for (int i = 0; i < input.length(); i++) {
				out.append(JapaneseCharacter.toHiragana(input.charAt(i)));
			}
		} else { // do nothing if neither
			return input;
		}

		return out.toString();
	}
	
	public static boolean isHiraganaString(String input) {
		char ch = input.charAt(0);
		return JapaneseCharacter.isHiragana(ch);
	}
	
	public static boolean isKatakanaString(String input) {
		char ch = input.charAt(0);
		return JapaneseCharacter.isKatakana(ch);
	}
	
	public static boolean isRomajiString(String input) {
		char ch = input.charAt(0);
		return JapaneseCharacter.isRomaji(ch);
	}
}
