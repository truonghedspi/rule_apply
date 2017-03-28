package main;

import phrase_improve.Phrase;
import phrase_improve.PhraseTable;
import writer.FileWriter;

public class ExtendTableMain {
	public static void main(String[] args) {
		String old = "/home/truong/gr/training/test/ja-vi/working/train/model/phrase-table";
		String extend = "/home/truong/gr/training/test/ja-vi/working/train/model/new-table";
		
		PhraseTable oldT = new PhraseTable(old);
		PhraseTable extT = new PhraseTable(extend);
		
		for (Phrase extP: extT.getTable()) {
			if (!oldT.contains(extP)) {
				oldT.addPhrase(extP);
			}
		}
		
		StringBuilder builder = new StringBuilder();
		for (Phrase phrase: extT.getTable()) {
			builder.append(phrase.toString());
			builder.append("\n");
		}
		
		FileWriter writer = new FileWriter();
		writer.write(builder.toString(), old);
		
	}
}
