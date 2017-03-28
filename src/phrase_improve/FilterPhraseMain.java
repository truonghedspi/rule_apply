package phrase_improve;

import writer.FileWriter;

public class FilterPhraseMain {
	public static void main(String[] args) {
		String fileName = "/home/truong/gr/phrase_improve/sub_16_ja_en/working/train/model/phrase-table";
		String out = "/home/truong/gr/phrase_improve/sub_16_ja_en/working/train/model/phrase-table-filtered";
		int mostK = 1000000	;
		FilterPhraseProcess p = new FilterPhraseProcess(fileName, mostK);
		PhraseTable res = p.getKMostPhrase();
	} 
}
