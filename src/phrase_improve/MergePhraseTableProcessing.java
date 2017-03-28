package phrase_improve;

import writer.FileWriter;
import reader.FileReader;

public class MergePhraseTableProcessing implements FileReader.Listener{
	private PhraseTable sourcePivotTable;
	private PhraseTable pivotTargetTable;
	
	private PhraseTable newTable;
	
	private String pivotTargetTableFileName;
	private String sourcePivotFileName;
	
	public final static int LEFT_RIGHT = 0;
	public final static int RIGHT_LEFT = 1;
	
	private int type;
	
	public MergePhraseTableProcessing(PhraseTable sourcePivot, PhraseTable pivotTarget) {
		sourcePivotTable = sourcePivot;
		pivotTargetTable = pivotTarget;
		newTable = new PhraseTable();	
	}
	

	
	public MergePhraseTableProcessing(String sourcePivot, String pivotTarget, int type) {
		sourcePivotFileName = sourcePivot;
		pivotTargetTableFileName = pivotTarget;
		newTable = new PhraseTable();
		this.type = type;
	}
	
	public MergePhraseTableProcessing(String sourcePivot, String pivotTarget, String newTable, int type) {
		sourcePivotFileName = sourcePivot;
		pivotTargetTableFileName = pivotTarget;
		this.newTable = new PhraseTable(newTable);
		this.type = type;
	}
	
	public void merge() {
//		for (Phrase leftP: sourcePivotTable.getTable()) {
//			for (Phrase rightP: pivotTargetTable.getTable()) {
//				if (leftP.getTargetPhrase().equals(rightP.getSourcePhrase())) {
//					mergePhrase(leftP,rightP);
//				}
//			}
//		}
		
		if (type == LEFT_RIGHT) {
			sourcePivotTable = new PhraseTable(sourcePivotFileName);
			FileReader reader = new FileReader(this);
			reader.read(pivotTargetTableFileName);
		}
		
		if (type == RIGHT_LEFT) {
			pivotTargetTable = new PhraseTable(pivotTargetTableFileName);
			FileReader reader = new FileReader(this);
			reader.read(sourcePivotFileName);
		}
		
	}
	
	
	private void mergePhrase(Phrase leftP, Phrase rightP) {
		Phrase newPhrase = new Phrase();
		
		mergeSourceTargetPhrase(leftP,rightP,newPhrase);
		mergeScore(leftP,rightP,newPhrase);
		mergeAlignment(leftP,rightP,newPhrase);
		mergeCount(leftP,rightP,newPhrase);
		
		newTable.addMergedPhrase(newPhrase);
	}
	
	private void mergeSourceTargetPhrase(Phrase leftP,Phrase rightP,Phrase newPhrase) {
		newPhrase.setSourcePhrase(leftP.getSourcePhrase());
		newPhrase.setTargetPhrase(rightP.getTargetPhrase());
	}
	
	private void mergeScore(Phrase leftP, Phrase rightP, Phrase newPhrase) {
		newPhrase.setInPhraseWeight(leftP.getInPhraseWeight()*rightP.getInPhraseWeight());
		newPhrase.setInLexicalWeight(leftP.getInLexicalWeight()*rightP.getInLexicalWeight());
		newPhrase.setDirectPhraseWeight(leftP.getDirectPhraseWeight()*rightP.getDirectPhraseWeight());
		newPhrase.setDirectLexicalWeight(leftP.getDirectLexicalWeight()*rightP.getDirectLexicalWeight());
	}
	
	
	private  void mergeAlignment(Phrase leftP,Phrase rightP,Phrase newPhrase) {
		String[] leftPhraseAlign = leftP.getAlignment().split(" ");
		String[] rightPhraseAlign = rightP.getAlignment().split(" ");
		StringBuilder builder = new StringBuilder();
		String newAlign;
		for (String leftE: leftPhraseAlign) {
			String[] leftEAlignArr = leftE.split("-");
			for (String rightE: rightPhraseAlign) {
				String[] rightEAlignArr = rightE.split("-");
				if (leftEAlignArr[1].equals(rightEAlignArr[0])) {
					newAlign = leftEAlignArr[0]+"-"+rightEAlignArr[1] +" ";
					if (!builder.toString().contains(newAlign)) {
						builder.append(newAlign);
					}
				}
			}
		}
		newPhrase.setAlignment(builder.toString().trim());
	}
	
	private void mergeCount(Phrase leftP,Phrase rightP,Phrase newPhrase) {
		newPhrase.setCount1(leftP.getCount1());
		newPhrase.setCount2(leftP.getCount2());
		newPhrase.setCount3(leftP.getCount3());
	}
	
	public void writeResultToFile(String fileName) {
		newTable.printTable();
		StringBuilder builder = new StringBuilder();
		if (newTable.getTable()== null) {
			return;
		}
		for (Phrase phrase: newTable.getTable()) {
			builder.append(phrase.toString());
			builder.append("\n");
		}
		
		FileWriter writer = new FileWriter(fileName);
		writer.write(builder.toString());
		
		System.out.println(newTable.getTable().size());
	}

	public void execute(final String line) {
		// TODO Auto-generated method stub
		
		Thread thread = new Thread() {
			public void run() {
				Phrase newP = new Phrase(line);
				if (type == LEFT_RIGHT) {
					
					for (Phrase p: sourcePivotTable.getTable()) {
						if (p.getTargetPhrase().equals(newP.getSourcePhrase())) {
							mergePhrase(p, newP);
						}
					}
				} 
				
				if (type == RIGHT_LEFT) {
					for (Phrase p: pivotTargetTable.getTable()) {
						if (p.getSourcePhrase().equals(newP.getTargetPhrase())) {
							mergePhrase(newP,p);
						}
					}
				}
				
			}
		};
		
		thread.start();
		
	}

	public void completedReadFile() {
		// TODO Auto-generated method stub
		
	}
}
