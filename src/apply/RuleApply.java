package apply;

import java.util.ArrayList;
import java.util.List;

import dgraph.Graph;
import rule.Rule;
import rule.RuleContainer;
import reader.FileReader;
import writer.FileWriter;

public class RuleApply implements FileReader.Listener{
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
		
		applyRule(graph);
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
				
				completeApplyRuleForSentence();
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
		compable = true;
	} 
	
	
	
}
