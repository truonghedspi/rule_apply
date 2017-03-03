package apply;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dgraph.Graph;
import rule.Rule;
import rule.RuleContainer;
import reader.FileReader;
import writer.FileWriter;

public class RuleApply implements FileReader.Listener{
	
	public final static int MAX_WORDS = 10;
	
	private final static String outputFileName = "/home/truong/gr/training/test/corpus/test.ordered.ja";
	private final static String intputFileName = "/home/truong/gr/training/test/corpus/test.tagged.ja";
	
	// contain all rule 
	private RuleContainer mContainer;
	
	private final String TAG = "RuleApply";
	
	private List<Graph> graphs = new ArrayList<Graph>();
	
	private int mNumberOfLine = 0;
	
	private  int mNumberOFCompletedThread = 0;
	
	private boolean compable = false;
	
	public RuleApply(RuleContainer container) {
		mContainer = container;
	}
	
	private synchronized void completeApplyRuleForSentence() {
		++mNumberOFCompletedThread;
		System.out.println(mNumberOFCompletedThread);
		if (mNumberOFCompletedThread == 415) {
			writeResultToFile();
		}
		
	}
	

	
	public void writeResultToFile() {
//		StringBuilder builder = new StringBuilder(); 
//		for (Graph graph: graphs) {
//			System.out.println(graph.toPlf());
//			builder.append(graph.toPlf());
//			builder.append("\n");
//		}
//		
//		FileWriter.write(builder.toString(), outputFileName);
		
		StringBuilder builder = new StringBuilder(); 
		for (Graph graph: graphs) {
			System.out.println(graph.getWeigetestSentence());
			builder.append(graph.getWeigetestSentence());
			builder.append("\n");
		}
		
		FileWriter.write(builder.toString(), outputFileName);
	}
	
	public void start() {
		FileReader reader = new FileReader(this);
		reader.read(intputFileName);
	}
	
	/**
	 * apply rule on source sentence 
	 * @param sentence source sentence
	 */
	private void applyRule(String sentence) {
		Graph graph = new Graph();
		graph.initGraph();
		graph.createMonoTone(sentence);
		
		graphs.add(graph);
		
		if (graph.getLength() <= MAX_WORDS) {
			applyRule(graph);
		}
		
		graph.postProcess();
		
	}
	
	public List<Graph> getListGraph() {
		return graphs;
	}
	
	/**
	 * apply rule on graph
	 * @param g graph that apply rule on this
	 */
	private void applyRule(final Graph g) {
				
				for (Rule rule: RuleApply.this.mContainer.getRuleList()) {
					g.applyRule(rule);
				}
				//g.postProcess();
	}
	
	//listener for each time read a line from corpus
	public void execute(String line) {
		applyRule(line);		
	}
	
	
	public void completedReadFile() {
		writeResultToFile();
//		StringBuilder res = new StringBuilder();
//		int i = 0;
//		for (Graph g: getListGraph()) {
//			++i;
//			System.out.println("line:"+i);
//			res.append(decode(g));
//			res.append("\n");
//		}
//		
//		FileWriter.write(res.toString(), outputFileName);

	} 
	
	private String decode(Graph g) {
		HashPair pair = null;
		HashPair tmp = null;
		
		List<String> allSentences = 
				g.getStringsBetweenTwoVertex(g.getRootVertex(), g.getEndVertex()); 
//		Set<String> hs = new HashSet<String>();
//		hs.addAll(allSentences);
//		allSentences.clear();
//		allSentences.addAll(hs);
	
		int i =0;
		for (String sentence: allSentences) {
			++i;
			System.out.println("decode:"+i+"/"+allSentences.size());
			tmp = decode(sentence);
			if (pair == null) {
				pair = tmp;
				continue;
			}
			if (tmp.score > pair.score) {
				pair.score = tmp.score;
				pair.sentence = tmp.sentence;
			}
			
		}
		
		return pair.sentence;
	}
	
	private HashPair decode(String sentence) {
		String args[] = new String[3];
		args[0] = "/bin/sh";
		args[1] = "-c";
		args[2] = "echo '" + sentence + "' | ~/mosesdecoder/bin/moses -f ~/nlp/test/working/train/model/moses.ini";
		
		Process p;
		System.out.println("Cau can dich:"+sentence);
		HashPair hash = new HashPair();
		try {
			p = Runtime.getRuntime().exec(args);
			
			 BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		     BufferedReader  in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		     String line = null;
		     String temp, score;
		     int i = 0;
		     while ((line = err.readLine()) != null) {
	        	++i;
	        	System.out.println(line);
	        	if (i == 38) {
	        		String res[] = line.split("[\\[\\]=]");
	        		int index = line.lastIndexOf("total=");
	        		temp = line.substring(index+7, line.length()-1);
	        		res = temp.split("]");
	        		System.out.println("score"+res[0]);
	        		hash.score = Float.parseFloat(res[0]);
	        	}
	        }
	        
	        hash.sentence = in.readLine();
	        System.out.println(hash.sentence);
			return hash;
			 
			//p.waitFor();
		     
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return null;

	}
	
	class HashPair {
		public String sentence;
		public float score;
	}
	
}
