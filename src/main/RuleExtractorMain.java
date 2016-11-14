package main;

import extractor.RuleExtractor;

public class RuleExtractorMain {
	
	public static void main(String[] args) {
		String posFileName = "/home/truong/Gr/pos.ja";
		String alignFileName = "/home/truong/Gr/aligned.grow-diag-final-and";
		
		RuleExtractor ruleExtractor = new RuleExtractor(posFileName, alignFileName);
		ruleExtractor.extractRules();
	}
}
