package phrase_improve;

public class Main {
	public static void main(String[] args) {
		String t1name = "/home/truong/gr/phrase_improve/sub_16_ja_en/working/train/model/phrase-table-filtered";
		String t2name = "/home/truong/gr/phrase_improve/sub_16_en_vi/working/train/model/phrase-table-1";
		String out = "/home/truong/gr/phrase_improve/table/new-table";
		
		PhraseTable t1 = new PhraseTable(t1name);
		PhraseTable t2 = new PhraseTable(t2name);
		
		MergePhraseTableProcessing process = new MergePhraseTableProcessing(t1name, t2name, out,MergePhraseTableProcessing.LEFT_RIGHT);
		process.merge();
		process.writeResultToFile(out);
		
	}
}
