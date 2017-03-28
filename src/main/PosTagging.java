package main;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import constant.Constant;
import reader.FileReader;
import writer.FileWriter;

public class PosTagging implements FileReader.Listener {
	static String inputFile = "/home/truong/gr/phrase_improve/sub_16_ja_en/corpus/train.mecab.ja";
	static String outputFile = "/home/truong/gr/phrase_improve/sub_16_ja_en/corpus/train.tok.ja";
	static FileWriter writer = new FileWriter(outputFile);

	public static void main(String[] args) {
		PosTagging a = new PosTagging();
		FileReader reader = new FileReader(a);
		reader.read(inputFile);
		// String fileContent = reader.getContent();
		// tokenizer(fileContent);
	}

	public static void tagPos(String line) {
		StringBuilder builder = new StringBuilder();

		String[] lineArr;

		if (line.equals("EOS")) {
			builder.append("\n");
			return;
		}

		lineArr = line.split("[\t ]");
		tag = lineArr[1].split(",");
		builder.append(lineArr[0]);
		builder.append("_");

		builder.append(" ");

	}

	public static void tokenizer(String line) {
		StringBuilder builder = new StringBuilder();
		
		String[] lineArr;
		String[] tag;
		
		
		
		if (line.equals("EOS")) {
			builder.append("\n");
		    Files.write(Paths.get(outputFile), "\n".getBytes(), StandardOpenOption.APPEND);
			return ;
		}
		
		lineArr = line.split("[\t]");
		Files.write(Paths.get(outputFile), (lineArr[0]+" ").getBytes(), StandardOpenOption.APPEND);
	
	}

	public void execute(String line) {
		// TODO Auto-generated method stub
		tokenizer(line);
	}

	public void completedReadFile() {
		// TODO Auto-generated method stub

	}
}
