package main;

import rule.*;
public class RuleTestMain {
	
	public static void main(String[] args) {
		Rule r1 = new Rule("A B C D ", "1 2 3 4");
		Rule r2 = new Rule("A B C D ", "1 2 3 4");
		System.out.println(r1.equals(r2));
		
	}
}
