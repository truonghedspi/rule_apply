package main;

import reader.FileReader;
import writer.FileWriter;

public class PosTagging {
	static String inputFile = "/home/truong/Gr/corpus/test.mecab.ja";
	static String outputFile = "/home/truong/Gr/corpus/test.tagged.ja";
	
	public static void main(String[] args) {
		FileReader reader = new FileReader();
		reader.read(inputFile);
		String fileContent = reader.getContent();
		FileWriter.write(tagPos(fileContent), outputFile);
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
			builder.append("/");
			builder.append(tag[0]);
			builder.append(" ");
		}
		
		return builder.toString();
	}
}
