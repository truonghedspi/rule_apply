package phrase_improve;

public class Test {
	public static void main(String[] args) {
		PhraseTable table1 = new PhraseTable();
		PhraseTable table2 = new PhraseTable();
		
		Phrase p1 = new Phrase();
		Phrase p2 = new Phrase();
		p1.setAlignment("0-0 0-1 1-0 2-0 2-1 3-2");
		p2.setAlignment("0-0 0-1 1-0 1-2 2-1");
		
		table1.addPhrase(p1);
		table2.addPhrase(p2);
		
		MergePhraseTableProcessing process = new MergePhraseTableProcessing(table1, table2);
		proc
	}
}
