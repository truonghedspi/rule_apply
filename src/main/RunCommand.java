package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class RunCommand {
	
	class Background extends Thread {
		public Process p;
		
		public void run() {
			// TODO Auto-generated method stub
			Runtime r = Runtime.getRuntime();
			try {
				p = r.exec("/home/truong/C/hello");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	public static void main(String[] args) throws IOException, InterruptedException{
		
	
		BufferedReader is;
		String line;
		
		Background g = new RunCommand().new Background();
		g.start();
		
		Thread.sleep((long) 1000f);
		
		
		Process p = g.p;
		
		
		System.out.println("Aa");
		InputStream out; OutputStream in;
		InputStream err;
		out = p.getInputStream();
		in = p.getOutputStream();
		err = p.getErrorStream();
		
		
	BufferedReader reader = new BufferedReader(new InputStreamReader(out));
		

		BufferedReader errReader = new BufferedReader(new InputStreamReader(err));
		
		while ((line = errReader.readLine()) != null) {
			System.out.println("out:"+line);
		}
		
		
		while ((line = reader.readLine()) != null) {
			System.out.println("err:"+line);
			
		} 
		
		PrintWriter outPw = new PrintWriter(in);
		outPw.println("CC");
		outPw.flush();
	
		
		

		
	}
	
}
