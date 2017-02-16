package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import reader.FileReader;
import writer.FileWriter;

public class Test {
	public final static String in = "/home/truong/gr/training/test/corpus/test.clean.vi";
	public final static String out = "/home/truong/gr/training/test/corpus/test.origin.vi";
	public static void main(String[] args) {
		FileReader reader = new FileReader();
		
		reader.read(in);
		
		String content = reader.getContent();
		String newContent = content.replaceAll("_", " ");
		FileWriter writer = new FileWriter();
		writer.write(newContent, out);
		
	}
}
