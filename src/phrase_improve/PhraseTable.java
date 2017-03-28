package phrase_improve;

import java.util.ArrayList;
import java.util.List;

import reader.*;

public class PhraseTable implements FileReader.Listener{
	private List<Phrase> table = new ArrayList<Phrase>();
	private double maxQ = Double.MIN_VALUE, minQ = Double.MAX_VALUE;
	
	public PhraseTable() {
		
	}
	
	public void addPhrase(Phrase newPhrase) {
		int index = getPhraseIndex(newPhrase);
		if (index == -1) {
			table.add(newPhrase);
			
		}
	}
	
	public void addMergedPhrase(Phrase newPhrase) {
		int index = getPhraseIndex(newPhrase);
		if (index == -1) {
			table.add(newPhrase);
			System.out.println("ad rule:"+newPhrase.toString());
			return;
		}
		
		Phrase existedP = table.get(index);
		existedP.setInPhraseWeight(existedP.getInPhraseWeight()+newPhrase.getInPhraseWeight());
		existedP.setInLexicalWeight(existedP.getInLexicalWeight()+newPhrase.getInLexicalWeight());
		existedP.setDirectPhraseWeight(existedP.getDirectPhraseWeight()+newPhrase.getDirectPhraseWeight());
		existedP.setDirectLexicalWeight(existedP.getDirectLexicalWeight()+newPhrase.getDirectLexicalWeight());
	}
	
	private int getPhraseIndex(Phrase newPhrase) {
		
		if (table.size() == 0) {
			return -1;
		}
		
		Phrase p;
		for (int i = 0 ; i < table.size(); ++i) {
			p = table.get(i);
			if (p.getSourcePhrase().equals(newPhrase.getSourcePhrase())
					&& p.getTargetPhrase().equals(newPhrase.getTargetPhrase())) {
				return i;
			}
		}
		
		return -1;
	}
	
	public boolean contains(Phrase newPhrase) {
		
		if (getPhraseIndex(newPhrase) == -1) {
			return true;
		} 
		
		return false;
	}
	
	public List<Phrase> getTable() {return table;}
	
	public PhraseTable(String inFile) {
		FileReader reader = new FileReader(this);
		reader.read(inFile);
	}

	public void execute(String line) {
		table.add(new Phrase(line));
//		double 
//		Phrase p = new Phrase(line);
//		double quality = p.getQuality();
//		if (quality > maxQ) {
//			maxQ = quality;
//		} 
//		if (quality < minQ) {
//			minQ = quality;
//		}
	}

	public void completedReadFile() {
		// TODO Auto-generated method stub
		System.out.println("maxQ:"+maxQ);
		System.out.println("minQ"+minQ);
		System.out.println((maxQ+minQ)/2);
	}
	
	public void printTable() {
		for (Phrase phrase: table) {
			System.out.println(phrase.toString());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Phrase p: table) {
			builder.append(p.toString());
			builder.append("\n");
		}
		
		return builder.toString();
	}
}
