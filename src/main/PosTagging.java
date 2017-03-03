package main;

import constant.Constant;
import reader.FileReader;
import writer.FileWriter;

public class PosTagging {
	static String inputFile = "/home/truong/gr/training/test/corpus/train.mecab.ja";
	static String outputFile = "/home/truong/gr/training/test/corpus/train.tagged.ja";
	
	public static void main(String[] args) {
		FileReader reader = new FileReader();
		reader.read(inputFile);
		String fileContent = reader.getContent();
		FileWriter.write(tokenizer(fileContent), outputFile);
	}
	
	public static String tagPos(String fileContent) {
		StringBuilder builder = new StringBuilder();
		
		String[] fileContentArr = fileContent.split("\n");
		String[] lineArr;
		String[] tag;
		for (String line: fileContentArr) {
			if (line.equals("EOS")) {
				builder.append("\n");
				continue;
			}
			
			lineArr = line.split("[\t ]");
			tag = lineArr[1].split(",");
			builder.append(lineArr[0]);
			builder.append("_");
			builder.append(tag[0]);
			builder.append(" ");
		}
		
		return builder.toString();
	}
	
	public static String tokenizer(String fileContent) {
		StringBuilder builder = new StringBuilder();
		
		String[] fileContentArr = fileContent.split("\n");
		String[] lineArr;
		String[] tag;
		for (String line: fileContentArr) {
			if (line.equals("EOS")) {
				builder.append("\n");
				continue;
			}
			
			lineArr = line.split("[\t ]");
			tag = lineArr[1].split(",");
			builder.append(lineArr[0]);
			builder.append(Constant.TAG_WORD_DEVIDE_SYMBOL);
			builder.append(tag[0]);
			builder.append(" ");
		}
		
		return builder.toString();
	}
}
