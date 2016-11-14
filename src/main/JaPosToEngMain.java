package main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;

import reader.*;

public class JaPosToEngMain {
	public static void main(String[] args) {
		String jaPosFileName = "/home/truong/res.txt";
		String jaToEngPosFilePattern = "/home/truong/pos_ja_to_en.txt";
		String posFileContent;
		String jaToEndPosFileContent;
		
		FileReader reader = new FileReader();
		reader.read(jaPosFileName);
		posFileContent = reader.getContent();
		
		reader.read(jaToEngPosFilePattern);
		jaToEndPosFileContent = reader.getContent();
		translate(posFileContent.split("\n"), jaToEndPosFileContent.split("\n"));
	}
	
	public static  void translate(String[] posContent, String[] posTranslater) {
		HashMap<String, String> tranlasterMap = new HashMap<String, String>();
		String[] keys = {"名詞", "助詞", "副詞","助動詞" , "動詞", "形容詞", "接頭詞", "接続詞", "記号", "連体詞", "感動詞", "フィラー", "その他"};
		
		String[] lineArr;
		
		for (String line: posTranslater) {
			lineArr = line.split(" ");
			tranlasterMap.put(lineArr[0], lineArr[1]);
			
		}
		
		StringBuilder builder = new StringBuilder();
		String res = null;
		for (String line: posContent) {
			res = line;
			for (String key: keys) {
				res = res.replace(key, tranlasterMap.get(key));
			}
			builder.append(res);
			builder.append("\n");
		}
		
		Writer writer;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("/home/truong/pos_trans.txt"), "utf-8"));
			
			writer.write(builder.toString());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
}
