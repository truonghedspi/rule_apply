package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import rule.Rule;
import rule.RuleContainer;
import writer.FileWriter;

public class RuleFilter {
	public static final int MAX_WORDS = 7;
	public static final int MIN_COUNT = 4000;
	
	public static final int LOW = 2;
	public static final int UP = 15;
	
	public static void main(String[] args) {
		RuleContainer container = readFile("/home/truong/res.txt");
		statistic(container);
//		FileWriter.write(container.toString(), "/home/truong/res_filter.txt");
//		System.out.println(container.getRuleList().size());
		
		//statisticRuleByLengthAndCount(container);
		
	}
	
	public static void statisticRuleByLengthAndCount(RuleContainer container) {
		HashMap<Integer, Integer> resMap = new HashMap<Integer,Integer>();
		
		for (int i = LOW; i <= UP; ++i) {
			int count = 0;
			for (Rule rule: container.getRuleList()) {
				System.out.println(rule.getSourceSideArray().length);
				if (rule.getSourceSideArray().length == i) {
					count++;
				}
			}
			
			resMap.put(new Integer(i), new Integer(count));
		}
		
		
		//print result
		
		for (Integer key: resMap.keySet()) {
			System.out.println("length: "+ key +" - " + resMap.get(key));
		}
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
	
	public static void statistic(RuleContainer container) {
		HashMap<Integer, Integer> filterMap = new HashMap<Integer, Integer>();
		filterMap.put(4, 4000);
		filterMap.put(5, 3000);
		filterMap.put(6, 2000);
		filterMap.put(7, 1500);
		filterMap.put(8, 1000);
		HashMap<Integer, Integer> res = new HashMap<Integer, Integer>();
		
		for (Integer key: filterMap.keySet()) {
			int value = filterMap.get(key);
			int count = 0;
			for (Rule rule: container.getRuleList()) {
				
				if (rule.getCount() > value && rule.getSourceSideArray().length < key) {
					++count;
				}
			}
			
			
			res.put(key, count);
		}
		
		for (Integer key: res.keySet()) {
			System.out.println(key + " - " + res.get(key));
		}
	}
	
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
			    
			    //filter
//			    if (posArr.length > MAX_WORDS) {
//			    	continue;
//			    }
			    
			    //System.out.println(Integer.parseInt(sentenceArr[2]));
			    
			    //filter
//			    if (Integer.parseInt(sentenceArr[2]) < MIN_COUNT) {
//			    	continue;
//			    }
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
