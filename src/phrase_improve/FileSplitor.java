package phrase_improve;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import reader.FileReader;
import writer.FileWriter;

public class FileSplitor {
	
	
	public static void main(String[] args) {
		String fileName = "/home/truong/gr/phrase_improve/sub_16_ja_en/working/train/model/phrase-table-filtered";
		FileSplitor instance = new FileSplitor();
		FileSplitorProcessor p = instance.new FileSplitorProcessor(fileName);
		p.execute();
	}
	
	public class FileSplitorProcessor implements FileReader.Listener{
		private String fileName;
		private int lineNumber = 0;
		private int MAX_LINE = 1000000;
		private int currentFile = 0;
		
		private Path file;
		
		private StringBuilder builder;
		
		public FileSplitorProcessor(String fileName) {
			this.fileName = fileName;
			lineNumber = 0;
		}
		
		public void execute() {
			
			builder = new StringBuilder();
			FileReader reader = new FileReader(this);
			reader.read(fileName);
		}

		public void execute(String line) {
			++lineNumber;
			if (lineNumber == MAX_LINE ) {
				++currentFile;
				lineNumber = 0;
				FileWriter writer = new FileWriter(fileName+"-"+currentFile);
				writer.write(builder.toString());
				builder = new StringBuilder();
			} else {
				builder.append(line+"\n");
			}
		}

		public void completedReadFile() {
			// TODO Auto-generated method stub
			
		}
		
		
		
		
	}
}
