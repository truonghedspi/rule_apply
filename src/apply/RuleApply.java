package apply;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dgraph.Graph;
import rule.Rule;
import rule.RuleContainer;
import reader.FileReader;
import writer.FileWriter;

public class RuleApply implements FileReader.Listener{
	
	public final static int MAX_WORDS = 15;
	
	private final static String outputFileName = "/home/truong/Gr/corpus/test.plf";
	private final static String intputFileName = "/home/truong/Gr/corpus/test.tagged.ja";
	
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
		StringBuilder builder = new StringBuilder(); 
		for (Graph graph: graphs) {
			//System.out.println(graph.toPlf());
			builder.append(graph.toPlf());
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
		
	}
	
	public List<Graph> getListGraph() {
		return graphs;
	}
	
	/**
	 * apply rule on graph
	 * @param g graph that apply rule on this
	 */
	private void applyRule(final Graph g) {
//		Thread thread = new Thread() {
//			@Override
//			public void run() {
				for (Rule rule: RuleApply.this.mContainer.getRuleList()) {
					g.applyRule(rule);
				}
				
				g.postProcess();
				
				//completeApplyRuleForSentence();
//			}
//		};
		
	//	thread.start();
	}
	
	//listener for each time read a line from corpus
	public void execute(String line) {
		++mNumberOfLine;
		applyRule(line);		
	}
	
	
	public void completedReadFile() {
		//writeResultToFile();
		StringBuilder res = new StringBuilder();
		for (Graph g: getListGraph()) {
			res.append(decode(g));
			res.append("\n");
		}
		
		FileWriter.write(res.toString(), outputFileName);

	} 
	
	private String decode(Graph g) {
		HashPair pair = null;
		HashPair tmp = null;
		for (String sentence: 
			g.getStringsBetweenTwoVertex(g.getRootVertex(), g.getEndVertex())) {
			try {
				tmp = decode(sentence);
				if (tmp.score > pair.score) {
					pair.score = tmp.score;
					pair.sentence = tmp.sentence;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return pair.sentence;
	}
	
	private HashPair decode(String sentence) throws IOException, InterruptedException {
		String args[] = new String[3];
		args[0] = "/bin/sh";
		args[1] = "-c";
		args[2] = "echo '" + sentence + "' | ~/mosesdecoder/bin/moses -f ~/nlp/reordering/ja-vi/working/binarised-model/moses.ini";
		//String args[] = new String[] { "/bin/sh", "-c", "echo '私 の 趣味 は 読書 です' | ~/mosesdecoder/bin/moses -f ~/nlp/reordering/ja-vi/working/binarised-model/moses.ini" };
		final Process p = Runtime.getRuntime().exec(args);
		final HashPair hash = new HashPair();
		new Thread(new Runnable() {
			public void run() {
			     BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			     BufferedReader  in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			     String line = null; 
			     int i = 0;
			     
			     try {
			        while ((line = err.readLine()) != null) {
			        	++i;
			        	if (i == 38) {
			        		String res[] = line.split("[\\[\\}=]");
			        		hash.score = Float.parseFloat(res[4]);
			        	}
			        }
			        
			        hash.sentence = in.readLine();
			     } catch (IOException e) {
			            e.printStackTrace();
			     }
			    }
			}).start();
		p.waitFor();
		return hash;
	}
	
	class HashPair {
		public String sentence;
		public float score;
	}
	
}
