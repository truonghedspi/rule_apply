package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/*
 * Use to read file
 * 
 */
public class FileReader {
	/*
	 * Callback when read one line
	 */

	public interface Listener{
		//execute each time read a line
		void execute(String line);
		
		//on completed read file
		void completedReadFile();
	}
	
	//content of file
	private String mContent = null;
	
	private Listener mCallback;
	
	public FileReader(Listener listener) {
		mCallback = listener;
	}
	
	public FileReader() {
		
	}
	
	public void read(String fileName) {
		try {
		        File fileDir = new File(fileName);
	
		        BufferedReader in = new BufferedReader(
		           new InputStreamReader(
		                      new FileInputStream(fileDir), "UTF8"));
	
		        String line;
		        StringBuilder builder = new StringBuilder();
		        int i = 0;
	
		        while ((line = in.readLine()) != null) {
		        	
		        	++i;
		        	if (mCallback != null) {
		        		System.out.println("Readline:"+line);
		        		mCallback.execute(line);
		        	}
		            
		            builder.append(line);
		            builder.append("\n");
		        }
		        if (mCallback != null) {
		        	mCallback.completedReadFile();
		        	System.out.println("Done");
		        }
		        	
		        in.close();
		        mContent = builder.toString();
	       	} catch (UnsupportedEncodingException e) {
	            System.out.println(e.getMessage());
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	   }
	}	
	
	
	//Getter
	public String getContent() {
		return mContent;
	}
	
}
