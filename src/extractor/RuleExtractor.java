package extractor;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import alignment.AlignMatrix;
import Logger.Log;
import reader.*;
import rule.*;

/*
 * Use to extact rule from inpu file
 * Write all rule to File 
 */
public class RuleExtractor {
	
	private static final String TAG = "RuleExtractor";
	private static final String LINE_DEVIDE_SYMBOL = "\n";
	private static final String WORD_DEVIDE_SYMBOL = " ";
	
	//output file
	public static final String OUTPUT_FILE = "/home/truong/res.txt";
		
	/*
	 * input file
	 */
	private String mSourceFile;
	
	private String mAlignFile;
	
	
	
	//file content
	private String[] mSourceContent;
	private String[] mAlignContent;
	
	
	//rule container
	RuleContainer mRuleContainer;
	
	public RuleExtractor(String sourceFile, String alignFile) {
		mSourceFile= sourceFile;
		mAlignFile = alignFile;
		mRuleContainer = new RuleContainer();
	}
	
	private void readInputFile() {
		FileReader reader = new FileReader();

		//read source file content
		Log.toConsole(TAG, "read source file content");
		reader.read(mSourceFile);
		mSourceContent = reader.getContent().split(LINE_DEVIDE_SYMBOL);
		
		
		//read alignment file content
		Log.toConsole(TAG, "read alignment file content");
		reader.read(mAlignFile);
		mAlignContent = reader.getContent().split(LINE_DEVIDE_SYMBOL);
		
		if (mAlignContent.length != mSourceContent.length)
						throw new IllegalArgumentException("Files input not same the number of lines");
	}
	
	private void extractRule() {
		String line;
		String lineArray;
		for (int lineIndex = 0; lineIndex < mSourceContent.length; ++lineIndex) {
			extractRuleInLine(mSourceContent[lineIndex],mAlignContent[lineIndex]);
		}
	}
	
	private void extractRuleInLine(String sourceLine, String alignLine) {
		
		String[] sourceSentenceArr = sourceLine.split(WORD_DEVIDE_SYMBOL);
		AlignMatrix alignMatrix = new AlignMatrix(alignLine);
		
		
		if (sourceSentenceArr.length <= 1) {
			return;
		}
		
		int maxSourcePositionAlign;
		int maxPrevSourcePositionAlign;
		for (int i = 1; i < sourceSentenceArr.length; ++i) {
			maxSourcePositionAlign = alignMatrix.getMaxAlignment(i);
			if (maxSourcePositionAlign == -1) {
				continue;
			}
			for (int j = 0; j < i; ++j) {
				

				maxPrevSourcePositionAlign = alignMatrix.getMaxAlignment(j);
				
				if (maxSourcePositionAlign >= maxPrevSourcePositionAlign) {
					continue;
				}
				extractRuleBetween(j, i, sourceSentenceArr, alignMatrix);
			}
		}
	}
	
	/**
	 * extract rule between lower and upper index of source sentence
	 * @param lower: lower index of source sentence
	 * @param upper: upper index of source sentence
	 * @param sourceSentence: String array words of source sentence
	 * @param matrix: align matrix
	 */
	private void extractRuleBetween(int lower, int upper, String[] sourceSentence, AlignMatrix matrix) {
		List<Word> words = new ArrayList<Word>();
		for (int i = lower; i <= upper; ++i) {
			words.add(new Word(i,matrix.getMaxAlignment(i)));
		}
		Collections.sort(words);
		
		int minIndex = sourceSentence.length;
		
		for (Word word: words) {
			if (word.index < minIndex) {
				minIndex = word.index;
			}
		}
		
		StringBuilder source = new StringBuilder();
		StringBuilder target = new StringBuilder();
		for (Word word: words) {
			source.append(sourceSentence[word.index] + " ");
			target.append((word.index-minIndex)+" ");
		}
		
		mRuleContainer.addRule(new Rule(source.toString(),target.toString()));
	}
	
	
	public  class Word implements Comparable<Word>{
		public int index;
		public int alignment;
		
		public Word(int index, int alignment) {
			this.index = index;
			this.alignment = alignment;
		}

		public int compareTo(Word other) {
			if (alignment > other.alignment) return 1;
			if (alignment < other.alignment) return -1;
			if (index > other.index) return 1;
			if (index < other.index) return -1;
			return 0;
		}
		
		
	}

	private void writeRulesToFile() {
		StringBuilder builder = new StringBuilder();
		mRuleContainer.sort();
		
		for(Rule rule: mRuleContainer.getRuleList()) {
			builder.append(rule.toString());
			builder.append("\n");
		}
		
		Writer writer;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream(OUTPUT_FILE), "utf-8"));
			
			writer.write(builder.toString());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
	public void extractRules() {
		readInputFile();
		extractRule();
		writeRulesToFile();
	}
}
