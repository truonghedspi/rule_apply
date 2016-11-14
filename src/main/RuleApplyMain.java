package main;

import apply.RuleApply;
import rule.RuleContainer;

public class RuleApplyMain {
	static final String ruleFileName = "/home/truong/res_filter.txt";
	
	public static void main(String[] args) {
		RuleContainer container = RuleContainer.loadData(ruleFileName);
		
		RuleApply ruleApplyer = new RuleApply(container);
		ruleApplyer.start();
	}
}
