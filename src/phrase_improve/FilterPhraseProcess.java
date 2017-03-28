package phrase_improve;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import writer.FileWriter;

public class FilterPhraseProcess {
	private PhraseTable table;
	private int mostK;
	private String fileName;
	
	public FilterPhraseProcess(String fileName, int mostK) {
		table = new PhraseTable(fileName);
		this.mostK = mostK;
		this.fileName = fileName;
	}
	
	private void sort() {
		Collections.sort(table.getTable());
		Collections.reverse(table.getTable());
	}
	
	public PhraseTable getKMostPhrase() {
		sort();
		PhraseTable res = new PhraseTable();
		StringBuilder builder = new StringBuilder();
		Phrase p;
		for (int i = 0; i < mostK; ++i) {
			p = table.getTable().get(i);
			if (!p.getSourcePhrase().trim().equals(p.getTargetPhrase().trim())) {
				//res.addPhrase(p);
				//builder.append(p.toString()+"\n");
				try {
				    Files.write(Paths.get(fileName+"-filtered"), (p.toString()+"\n").getBytes(), StandardOpenOption.APPEND);
				}catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
			}
			System.out.println(i);
		}
		
//		FileWriter writer = new FileWriter(fileName+"-filtered");
//		writer.write(table.toString());
		return res;
	}
}
