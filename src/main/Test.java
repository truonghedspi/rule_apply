package main;

import japanese.JapaneseCharacter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.gosen.GosenTokenizer;

import reader.FileReader;
import writer.FileWriter;

public class Test {
	public final static String in = "/home/truong/gr/training/test/corpus/test.clean.vi";
	public final static String out = "/home/truong/gr/training/test/corpus/test.origin.vi";
	public static void main(String[] args) {
		try {
			Path p = new Path ();
			File f = new File("/home/truong/a.txt");
			
			Files.write(Paths.get("/home/truong/a.txt"), "aaa".trim().getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(Integer.MAX_VALUE);
	}
}
