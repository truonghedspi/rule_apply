package rule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Logger.Log;


public class RuleContainer {
	private static final String TAG = "RuleContainer";
	
	private List<Rule> rules = new ArrayList<Rule>();
	
	
	public  void addRule(Rule rule) {
		Log.toConsole(TAG, "addRule:" + rule.toString());
		Rule res = getRule(rule);
		if (res == null) {
			rules.add(rule);
			rule.setCount(1);
		} else {
			res.increaseCount();
		}
	}
	
	public void addRule(String pos, String align, int count) {
		Rule newRule = new Rule(pos,align,count);
		getRuleList().add(newRule);
	}
	/**
	 * @return rule equals with parameter in source and target side
	 */
	public Rule getRule(Rule rule) {
		for (Rule element: rules) {
			if (element.equals(rule)) {
				return element;
			}
		}
		return null;
	}
	
	public void removeRule(Rule rule) {
		rules.remove(rule);
	}
	
//	public  boolean contains(Rule rule) {
//		for (Rule element: rules) {
//			if (element.equals(rule)) {
//				return true;
//			}
//		}
//		
//		return false;
//	} 
	
	public static RuleContainer loadData(String fileName) {
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
				System.out.println("read line:"+line);
			    sentenceArr = line.split("/");
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
	
	//Getter
	public List<Rule> getRuleList() {
		return rules;
	}
	
	
	public void sort() {
		Collections.sort(rules);
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (Rule rule: getRuleList()) {
			builder.append(rule.toString());
			builder.append("\n");
		}
		
		return builder.toString();
	}
}
