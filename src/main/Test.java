package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
	public static void main(String[] args) {
		

		 Process p;
		try {
			p = Runtime.getRuntime().exec("/home/truong/ls -a");
			p.waitFor();
			  BufferedReader buf = new BufferedReader(new InputStreamReader(
			          p.getInputStream()));
			  String line = "";
			  String output = "";

			  while ((line = buf.readLine()) != null) {
			    output += line + "\n";
			  }

			  System.out.println(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  

	}
}
