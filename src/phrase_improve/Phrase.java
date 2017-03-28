package phrase_improve;

import reader.FileReader;

public class Phrase implements Comparable<Phrase>{
	private String sourcePhrase, targetPhrase;
	
	private double inPhraseWeight, inLexicalWeight, directPhraseWeight, directLexicalWeight;
	
	private String alignment;
	
	private String count1, count2, count3;
	
	public Phrase(String line) {
		String[] strArr = line.split("\\|\\|\\|");
		sourcePhrase = strArr[0].trim();
		targetPhrase = strArr[1].trim();
		
		String[] scores = strArr[2].split(" ");
		inPhraseWeight = Double.parseDouble(scores[1]);
		inLexicalWeight = Double.parseDouble(scores[2]);
		directPhraseWeight = Double.parseDouble(scores[3]);
		directLexicalWeight = Double.parseDouble(scores[4]);
		
		alignment = strArr[3].trim();
		
		String[] counts = strArr[4].trim().split(" ");
		count1 = counts[0];
		count2 = counts[1];
		count3 = counts[2];
	}
	
	public Phrase(String source,
			String target,
			double inPhrase,
			double inLexical,
			double directPhrase,
			double directLexical,
			String align,
			String count1, 
			String count2,
			String count3) {
		
		setSourcePhrase(source);
		setTargetPhrase(target);
		setInPhraseWeight(inPhrase);
		setInLexicalWeight(inLexical);
		setDirectPhraseWeight(directPhrase);
		setDirectLexicalWeight(directLexical);
		
		setCount1(count1+"");
		setCount2(count2+"");
		setCount3(count3+"");
	
		
	}
	
	public Phrase() {}
	
//	public Phrase merge(Phrase other) {
//		if (getTargetPhrase().equals(other.getSourcePhrase())) {
//			Phrase newPhrase = new Phrase();
//			mergePhrase(this,other,newPhrase);
//			mergeScore(this,other,newPhrase);
//			mergeAlignment(this,other,newPhrase);
//			mergeCount(this,other,newPhrase);
//		}
//	}
//	
//	private void mergePhrase(Phrase leftP, Phrase rightP, Phrase newP) {
//		newP.setSourcePhrase(leftP.getSourcePhrase());
//		newP.setTargetPhrase(rightP.getTargetPhrase());
//	}
//	
//	private void mergeScore(Phrase leftP, Phrase rightP, Phrase newP) {
//		
//	}
	
	
	
	public void setSourcePhrase(String sourcePhrase) {
		this.sourcePhrase = sourcePhrase;
	}
	
	public void setTargetPhrase(String target) {
		targetPhrase = target;
	}
	
	public void setInPhraseWeight(double weight) {
		inPhraseWeight = weight;
	}
	
	public void setInLexicalWeight(double weight) {
		inLexicalWeight = weight;
	}
	
	public void setDirectPhraseWeight(double weight) {
		directPhraseWeight = weight;
	}
	
	
	public void setDirectLexicalWeight(double weight) {
		directLexicalWeight = weight;
	}
	
	public void setCount1(String count) {
		count1 = count;
	} 
	
	public void setCount2(String count) {
		count2 = count;
	}
	
	public void setCount3(String count) {
		count3 = count;
	}
	
	public void setAlignment(String alig) {
		alignment = alig;
	}
	
	public String getSourcePhrase() { return sourcePhrase;}
	public String getTargetPhrase(){return targetPhrase;}
	
	public double getInPhraseWeight(){return inPhraseWeight;}
	public double getInLexicalWeight() {return inLexicalWeight;}
	public double getDirectPhraseWeight() {return directPhraseWeight;}
	public double getDirectLexicalWeight() {return directLexicalWeight;}
	
	public double getQuality() {
		return getInPhraseWeight() +
				getInLexicalWeight() +
				getDirectPhraseWeight() +
				getDirectLexicalWeight();
	}
	
	public String getAlignment() {return alignment;}
	
	public String getCount1() {return count1;}
	public String getCount2() {return count2;}
	public String getCount3() {return count3;}
	
	
	@Override
	public String toString() {
		return sourcePhrase + " ||| " +
				targetPhrase + " ||| " +
				inPhraseWeight + " " + inLexicalWeight + " " + directPhraseWeight + " " + directLexicalWeight +" ||| "+
				alignment + " ||| " +
				count1 + " " + count2 + " " + count3 + " ||| |||";
				
	
	}

	public int compareTo(Phrase o) {
		// TODO Auto-generated method stub
		if (getQuality() > o.getQuality()) {
			return 1;
		} else if (getQuality() < o.getQuality()) {
			return -1;
		}
		return 0;
	}


}
