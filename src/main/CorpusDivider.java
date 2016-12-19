package main;

import reader.FileReader;
import writer.FileWriter;

public class CorpusDivider {
	public static final float TRAIN_PERCENT = 0.7f;
	public static final float DEV_PERCENT = 0.2f;
	
	public static final String jaFile = "/home/truong/Downloads/sub.tagged.ja";
	public static final String viFile = "/home/truong/Downloads/sub.tagged.vi";
	
	public static void main(String[] args) {
		FileReader reader = new FileReader();
		
		reader.read(jaFile);
		devideFile(reader.getContent(),jaFile);
		
		reader.read(viFile);
		devideFile(reader.getContent(),viFile);
	}
	
	public static void devideFile(String fileContent, String outFile) {
		String[] sentenceArr = fileContent.split("\n");
		
		int trainLength = (int)(sentenceArr.length * TRAIN_PERCENT);
		int devLength = (int)(sentenceArr.length * DEV_PERCENT);
		int testLength = sentenceArr.length - trainLength - devLength;
		
		String trainContent = getContent(0, trainLength-1, sentenceArr).toString();
		String devContent = getContent(trainLength, trainLength+devLength-1,sentenceArr).toString();
		String testContent = getContent(trainLength+devLength, sentenceArr.length-1, sentenceArr).toString();
		
		FileWriter writer = new FileWriter();
		writer.write(trainContent, outFile+"_train");
		writer.write(devContent, outFile+"_dev");
		writer.write(testContent, outFile+"_test");
	}
	
	private static  StringBuilder getContent(
			int low,
			int up,
			String[] sentenceArr) {
		StringBuilder builder = new StringBuilder();
		for (int i = low; i <= up; ++i) {
			builder.append(sentenceArr[i]);
			builder.append("\n");
		}
		
		return builder;
	}
	
}
