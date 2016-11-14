package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import rule.Rule;
import rule.RuleContainer;
import writer.FileWriter;

public class RuleFilter {
	public static final int MAX_WORDS = 7;
	public static final int MIN_COUNT = 4000;
	
	public static void main(String[] args) {
		RuleContainer container = readFile("/home/truong/res.txt");
//		filterNumberOfWords(container, MAX_WORDS);
//		filterFreq(container, MIN_COUNT);
		System.out.println(container.toString());
		FileWriter.write(container.toString(), "/home/truong/res_filter.txt");
		
	}
	
//	public static void filterNumberOfWords(RuleContainer container, int maxSize) {
//		for (Rule rule: container.getRuleList()) {
//			if (rule.getSourceSideArray().length > maxSize) {
//				container.removeRule(rule);
//			}
//		}
//	}
	
//	public static void filterFreq(RuleContainer container, int minCount) {
//		for (Rule rule: container.getRuleList()) {
//			if (rule.getCount() < minCount) {
//				container.removeRule(rule);
//			}
//		}
//	}
	
	public static RuleContainer readFile(String fileName) {
		RuleContainer container = new RuleContainer();
		
		File fileDirs = new File(fileName);

		BufferedReader in;
		try {
			in = new BufferedReader(
			new InputStreamReader(new FileInputStream(fileDirs), "UTF-8"));
			String line;
			String[] sentenceArr;
			String[] posArr;
			int i =1;
			while ((line = in.readLine()) != null) {
			    System.out.println("line " + i + ":"+line);
			    ++i;
			    sentenceArr = line.split("/");
			    System.out.println(sentenceArr[0]);
			    posArr = sentenceArr[0].split(" ");
			    if (posArr.length > MAX_WORDS) {
			    	continue;
			    }
			    
			    System.out.println(Integer.parseInt(sentenceArr[2]));
			    if (Integer.parseInt(sentenceArr[2]) < MIN_COUNT) {
			    	continue;
			    }
			    container.addRule(
			    		sentenceArr[0],
			    		sentenceArr[1], 
			    		Integer.parseInt(sentenceArr[2]));
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}


		return container;
	}
}
