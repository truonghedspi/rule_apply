package main;

import reader.FileReader;
import reader.FileReader.Listener;

public class FileReaderMain {
	public static void main(String[] args) {
		FileReader fileReader = new FileReader(new Listener() {
			
			public void execute(String line) {
				System.out.println(line);
			}
		});
		
		fileReader.read("/home/truong/Gr/pos.ja");
	}
}
