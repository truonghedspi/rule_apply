package dgraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.antlr.v4.runtime.misc.NotNull;

import Logger.Log;

import com.mxgraph.shape.mxCloudShape;

import rule.Rule;


public class Graph{
	private static final String TAG = "Graph";
	
  /** Color used to mark unvisited nodes */
  public static final String VISIT_COLOR_WHITE = "white";

  /** Color used to mark nodes as they are first visited in DFS order */
  public static final String VISIT_COLOR_GREY = "grey";

  /** Color used to mark nodes after descendants are completely visited */
  public static final String VISIT_COLOR_BLACK = "black";
  
  public static final String TAG_WORD_DEVIDE_SYMBOL ="/";
  
   
  
  public static int i = 0;

  /**List vertexs in graph */
  private List<Vertex> mVertexs;
  
  /** Root vertex */
  private Vertex mRootVerTex;
  
  private int mLengthOfSentence;
  
  private List<String> mStringList;
  
  private StringBuilder builder;
  
  private HashMap<Vertex, String> mColor;
  
  
  public Graph() {
	  mVertexs = new ArrayList<Vertex>();
  }
  
  /** Set Root Vertex */
  public void setRootVertex(Vertex v) {
	  this.mRootVerTex = v;
  }
  
  /** Get Root Vertex */
  public Vertex getRootVertex() {
	  return this.mRootVerTex;
  }
  
  
  public void addVertex(Vertex v) {
	  if (mVertexs.contains(v)) {
		  throw new IllegalArgumentException();
	  }
	  
	  mVertexs.add(v);
  } 
  
  /**
   * Append new edge to last of Vertex
   * @param word: word of sentence
   * @param pos: pos of word
   * @param vIn: vertex come to new edge
   * @param vOut: vertex go out new edge 
   */
  public void addEdge(Edge edge, Vertex vIn, Vertex vOut) {
	  if (!contains(vIn)
			  || !contains(vOut)) {
		  throw new IllegalArgumentException("vertex not in graph");
	  }
	  vIn.addVertical(vOut, edge);
	  vOut.setId(vIn.getId()+1);
  }
  
  /**
   * Add new word and pos to graph
   * @param word : word of sentence
   * @param pos : pos of word
   * @return last Vertex
   */
  public Vertex addEdgeToEndVertex(String word, String pos, boolean mainPath){
	  Vertex endVertex = getEndVertex();
	  
	  if (endVertex == null) {
		  throw new ExceptionInInitializerError("Graph not init yet!");
	  }
	  
	  Vertex newVertex = new Vertex();
	  Edge newEdge = new Edge(pos,word,mainPath);
	  addVertex(newVertex);
	  
	  addEdge(newEdge,endVertex,newVertex);
	  return newVertex;
  }
  
  /**
   * Check if vertex existed!
   * @param v : vertex want to check existed in graph
   * @return true if vertex already on graph, false if not
   */
  private boolean contains(Vertex v) {
	  return mVertexs.contains(v);
  }
  
  /** Get end vertex of graph */
  public Vertex getEndVertex() {
	  Vertex curVertex = getRootVertex();
	  while(curVertex.getOutEdges().size() != 0) {
		  curVertex = curVertex.getOutEdges().get(0).getToVertex();
	  }
	  return curVertex;
  }
  
  /**
   * Create graph with 1 vertex
   */
  public void initGraph() {
	  Vertex vertex = new Vertex();
	  setRootVertex(vertex);
	  mColor = new HashMap<Vertex, String>();
	  addVertex(vertex);
  }
  
  public void createMonoTone(String sourceTaggedSentence) {
	  String[] wordArr = sourceTaggedSentence.split(" ");
	  mLengthOfSentence = wordArr.length;
	  
	  for(String wordWithPosStr: wordArr) {
		  String[] wordPosArr = wordWithPosStr.split(TAG_WORD_DEVIDE_SYMBOL);
		  addEdgeToEndVertex(wordPosArr[0], wordPosArr[1], true);
	  }
  }
  
  /** getAll Sentence of graph */

  
  private class VertexEdgeSet {
	  public Vertex vertex;
	  public Edge edge;
	  
	  public VertexEdgeSet(Vertex v, Edge e) {
		  vertex = v;
		  edge = e;
	  }
  }
  
 
  
  public void printGraph() {
	  for (Vertex v: mVertexs) {
		  System.out.println(v.toString());
	  }
  }
  
  /**
   * Apply rule to graph
   * @param rule apply to graph
   */
  public void applyRule(Rule rule) {
	  int lengthOfRule = rule.getSourceSideArray().length;
	  int j =0;
	  String pos[] ;
	  String word[] ;
	  String newPos[];
	  String newWord[];
	  Edge edge;
	  for (int i = 0 ; i < mLengthOfSentence-lengthOfRule+1; ++i) {
		  
		  //get pos sequence
		  pos = new String[lengthOfRule];
		  word = new String[lengthOfRule];
		  j = 0;
		  while (j < lengthOfRule) {
			  edge = mVertexs.get(i+j).getEdgeOnMainPath();
			  Log.toConsole(TAG, edge.getPos());
			  pos[j] = edge.getPos();
			  word[j] = edge.getWord();
			  ++j;
		  }
		
		  //if pos sequence not match with rule continue
		  if (!Arrays.equals(pos, rule.getSourceSideArray())) {
			  continue;
		  }
		  //else if rule after apply not exsisted add new arc
		  newWord = new String[lengthOfRule];
		  newPos = new String[lengthOfRule];
		  //get POS after reorder
		  for (int k = 0 ; k < lengthOfRule; ++k) {
			  newWord[Integer.parseInt(rule.getTargetSideArray()[k])] = word[k];
			  newPos[Integer.parseInt(rule.getTargetSideArray()[k])] = pos[k];
		  }
		  
		  Vertex lower = mVertexs.get(i);
		  Vertex upper = mVertexs.get(i+j);
		  
		  //if word sequence not exsisted
		  //then add it to graph
		  if (!isWordSequenceExsisted(lower, upper, newWord)) {
			  addNewArc(lower, upper, newWord,newPos);
		  }
		  
		  
	  }
  }
  
  public List<String> getStringsBetweenTwoVertex(Vertex lower, Vertex upper) {
	  
	  builder = new StringBuilder();
	  mStringList = new ArrayList<String>();
	  DFS(lower,upper);
	  
	  return mStringList;
  }
  
  private void DFS(Vertex v, Vertex upper) {
	  String tmp = builder.toString();
	  for (Edge edge: v.getOutEdges()) {
		  builder.append(edge.getWord()+ " ");
		  Log.toConsole(TAG, "builder:"+builder.toString());
		  DFS(edge.getToVertex(),upper);
		  builder = new StringBuilder(tmp);
	  }
	  
	  if (v.equals(upper)) {
		  //Add List
		  mStringList.add(builder.toString());
		  Log.toConsole(TAG, "String:"+builder.toString());
	  }
  }
  
 
  
  
 private void addNewArc( Vertex lower, Vertex upper,
		 String[] words, String[] poss) {
		 if (words.length != poss.length)
			 throw new IllegalArgumentException("word array and pos array not same length!");
		 Vertex tmp = lower;
		 
		 for (int i = 0; i < words.length-1; ++i) {
			 Vertex newVertex = new Vertex();
			 addVertex(newVertex);
			 Edge newEdge = new Edge(poss[i],words[i], false);
			 addEdge(newEdge, tmp, newVertex);
			 tmp = newVertex;
		 } 
		 Edge edge = new Edge(poss[poss.length-1],words[words.length-1],false);
		 addEdge(edge,tmp,upper);
		 Log.toConsole(TAG, "edge:"+edge.getToVertex().getId());
		 Log.toConsole(TAG, "edge:"+edge.getFromVertex().getId());
	 }
 
 private boolean isWordSequenceExsisted(Vertex lower, Vertex upper, String[] word) {
	 List<String> list = getStringsBetweenTwoVertex(lower, upper);
	 for (String str: list) {
		 String[] strArr = str.split(" ");
		 if (Arrays.equals(strArr, word)) {
			 return true;
		 }
	 }
	 
	 return false;
 }
 
 public void postProcess() {
	 calculateEdgesWeight();
	 numberingVertex();
 }
 
 
 private void calculateEdgesWeight() {
	 for (Vertex v:  mVertexs) {
		if (v.getOutEdges().size() == 0) continue;
		
		int numberOfEdge = v.getOutEdges().size();
		float weight = (float)Math.round(100.0/numberOfEdge)/100;
		for (Edge edge: v.getOutEdges()) {
			edge.setWeight(weight);
		}
		
		v.getOutEdges().get(0).setWeight(1-weight*(numberOfEdge-1));
	 }
 }
 
 private void numberingVertex() {
	 	if (getRootVertex() == null) {
	 		return;
	 	}
	 	HashMap<Vertex, Integer> deg = new HashMap<Vertex,Integer>();
	 	for (Vertex v: mVertexs) {
	 		deg.put(v, new Integer(v.getInEdges().size()));
	 	}
	 	int i =0;
	 	
		Queue<Vertex> queue = new ArrayDeque<Vertex>();
		queue.add(getRootVertex());
		
		
		Vertex v;
		Vertex u; //adj vertex of v
		while(!queue.isEmpty()) {
			v = queue.remove();
			v.setId(i);
			for (Edge edge: v.getOutEdges()) {
				u = edge.getToVertex();
				deg.put(u, deg.get(u)-1);
				if (deg.get(u) == 0) {
					queue.add(u);
				}
			}
			++i;
		}
	}
 
 public String toPlf() {
	 StringBuilder builder = new StringBuilder();
	 Vertex cur;
	 Queue<Vertex> queue = new ArrayDeque<Vertex>();
	 queue.add(getRootVertex());
	 builder.append("(");
	 while (!queue.isEmpty()) {
		 cur = queue.remove();
		 builder.append(toPlfByVertex(cur));
		 for (Edge edge: cur.getOutEdges()) {
			 if (queue.contains(edge.getToVertex()) == false)
				 queue.add(edge.getToVertex());
		 }
	 }
	 builder.append(")");
	 return builder.toString();
 }
 
 private String toPlfByVertex(Vertex v) {
	 StringBuilder builder = new StringBuilder();
	 if (v.getOutEdges().size() == 0) {
		 return "";
	 }
	 
	 builder.append("(");
	 for (Edge edge: v.getOutEdges()) {
		builder.append("('");
		builder.append(edge.getWord());
		builder.append("', ");
		builder.append(edge.getWeight());
		builder.append(", ");
		builder.append(edge.getToVertex().getId()-v.getId());
		builder.append("),");
	 }
	 builder.append("),");

	 return builder.toString();
 }

}

