package dgraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.antlr.v4.runtime.misc.NotNull;

import Logger.Log;

import com.mxgraph.shape.mxCloudShape;

import constant.Constant;
import rule.Rule;

public class Graph {
	private static final String TAG = "Graph";

	/** Color used to mark unvisited nodes */
	public static final String VISITING = "visiting";

	/** Color used to mark nodes as they are first visited in DFS order */
	public static final String VISITED = "visited";

	/** Color used to mark nodes after descendants are completely visited */
	public static final String UNVISITED = "unvisited";

	public static final String TAG_WORD_DEVIDE_SYMBOL = Constant.TAG_WORD_DEVIDE_SYMBOL;

	/** use for topology numbering node */
	private int count = 0;

	private List<Vertex> mTopologyOrder;

	public static int i = 0;

	/** List vertexs in graph */
	private List<Vertex> mVertexs;

	/** Root vertex */
	private Vertex mRootVerTex;

	private int mLengthOfSentence;

	private List<String> mStringList;
	
	private List<Double> mWeightSum;
	
	private double weightOfSentence;

	private StringBuilder builder;

	private HashMap<Vertex, String> mColor;

	private HashMap<Vertex, String> explored;

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

	public List<Vertex> getVertexs() {
		return mVertexs;
	}

	/**
	 * Append new edge to last of Vertex
	 * 
	 * @param word
	 *            : word of sentence
	 * @param pos
	 *            : pos of word
	 * @param vIn
	 *            : vertex come to new edge
	 * @param vOut
	 *            : vertex go out new edge
	 */
	public void addEdge(Edge edge, Vertex vIn, Vertex vOut) {
		if (!contains(vIn) || !contains(vOut)) {
			throw new IllegalArgumentException("vertex not in graph");
		}
		vIn.addVertical(vOut, edge);
		vOut.setId(vIn.getId() + 1);
	}

	/**
	 * Add new word and pos to graph
	 * 
	 * @param word
	 *            : word of sentence
	 * @param pos
	 *            : pos of word
	 * @return last Vertex
	 */
	public Vertex addEdgeToEndVertex(String word, String pos, boolean mainPath) {
		Vertex endVertex = getEndVertex();

		if (endVertex == null) {
			throw new ExceptionInInitializerError("Graph not init yet!");
		}

		Vertex newVertex = new Vertex();
		Edge newEdge = new Edge(pos, word, mainPath);
		addVertex(newVertex);

		addEdge(newEdge, endVertex, newVertex);
		return newVertex;
	}

	/**
	 * Check if vertex existed!
	 * 
	 * @param v
	 *            : vertex want to check existed in graph
	 * @return true if vertex already on graph, false if not
	 */
	private boolean contains(Vertex v) {
		return mVertexs.contains(v);
	}

	/** Get end vertex of graph */
	public Vertex getEndVertex() {
		Vertex curVertex = getRootVertex();
		while (curVertex.getOutEdges().size() != 0) {
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

		for (String wordWithPosStr : wordArr) {
			String[] wordPosArr = wordWithPosStr.split(TAG_WORD_DEVIDE_SYMBOL);
			System.out.println("word_Pos:" + wordWithPosStr);
			System.out.println("b:" + wordPosArr[0]);
			System.out.println("a:" + wordPosArr[1]);
			addEdgeToEndVertex(wordPosArr[0],
					wordPosArr[wordPosArr.length - 1], true);
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
		// for (Vertex v: mVertexs) {
		// System.out.println(v.toString());
		// }

		Queue<Vertex> queue = new ArrayDeque<Vertex>();
		queue.add(getRootVertex());
		while (queue.isEmpty()) {
			Vertex v = queue.remove();
			for (Edge edge : v.getOutEdges()) {
				System.out.print(edge.getWord() + " ");
				queue.add(edge.getToVertex());
			}
			System.out.println();
		}

	}

	/**
	 * Apply rule to graph
	 * 
	 * @param rule
	 *            apply to graph
	 */
	public void applyRule(Rule rule) {
		int lengthOfRule = rule.getSourceSideArray().length;
		int j = 0;
		String pos[];
		String word[];
		String newPos[];
		String newWord[];
		Edge edge;
		for (int i = 0; i < mLengthOfSentence - lengthOfRule + 1; ++i) {

			// get pos sequence
			pos = new String[lengthOfRule];
			word = new String[lengthOfRule];
			j = 0;
			while (j < lengthOfRule) {
				edge = mVertexs.get(i + j).getEdgeOnMainPath();
				Log.toConsole(TAG, edge.getPos());
				pos[j] = edge.getPos();
				word[j] = edge.getWord();
				++j;
			}

			// if pos sequence not match with rule continue
			if (!Arrays.equals(pos, rule.getSourceSideArray())) {
				continue;
			}
			// else if rule after apply not exsisted add new arc
			newWord = new String[lengthOfRule];
			newPos = new String[lengthOfRule];
			// get POS after reorder
			for (int k = 0; k < lengthOfRule; ++k) {
				newWord[Integer.parseInt(rule.getTargetSideArray()[k])] = word[k];
				newPos[Integer.parseInt(rule.getTargetSideArray()[k])] = pos[k];
			}

			Vertex lower = mVertexs.get(i);
			Vertex upper = mVertexs.get(i + j);

			// if word sequence not exsisted
			// then add it to graph

			List<String> allStrings = getStringsBetweenTwoVertex(lower, upper);
			StringBuilder temp = new StringBuilder();
			for (String element : newWord) {
				temp.append(element + " ");
			}
			// System.out.println("temp:"+temp);
			if (allStrings.contains(temp.toString())) {
				System.out.println("Order exsisted");
				continue;
			}
			if (!isWordSequenceExsisted(lower, upper, newWord)) {
				addNewArc(lower, upper, newWord, newPos);
			}

		}
	}

	public List<String> getStringsBetweenTwoVertex(Vertex lower, Vertex upper) {

		builder = new StringBuilder();
		mStringList = new ArrayList<String>();
		mWeightSum = new ArrayList<Double>();
		weightOfSentence = 0f;
		if (lower == upper) {
			return mStringList;
		}
		DFS(lower, upper);

		return mStringList;
	}
	
	public String getWeigetestSentence() {
		Vertex lower = getRootVertex();
		Vertex upper = getEndVertex();
		
		builder = new StringBuilder();
		mStringList = new ArrayList<String>();
		mWeightSum = new ArrayList<Double>();
		weightOfSentence = 0f;

		DFS(lower, upper);
		
		double maxWeight = mWeightSum.get(0);
		int index = 0;
		
		for (int i = 1 ; i < mWeightSum.size(); ++i) {
			if (mWeightSum.get(i) > maxWeight) {
				maxWeight =mWeightSum.get(i);
				index = i; 
			}
		}
		
		return mStringList.get(index);
	}

	private void DFS(Vertex v, Vertex upper) {
		String tmp = builder.toString();
		for (Edge edge : v.getOutEdges()) {
			builder.append(edge.getWord() + " ");
			weightOfSentence += edge.getWeight();
			
			
			Log.toConsole(TAG, "builder:" + builder.toString());
			DFS(edge.getToVertex(), upper);
			builder = new StringBuilder(tmp);
			weightOfSentence = 0f;
			
		}

		if (v.equals(upper)) {
			// Add List
			mStringList.add(builder.toString());
			mWeightSum.add(weightOfSentence);
			Log.toConsole(TAG, "String:" + builder.toString());
			
		}
	}

	// private void addNewArc( Vertex lower, Vertex upper,
	// String[] words, String[] poss) {
	// if (words.length != poss.length)
	// throw new
	// IllegalArgumentException("word array and pos array not same length!");
	// Vertex tmp = lower;
	// boolean flag = false;
	//
	// for (int i = 0; i < words.length-1; ++i) {
	//
	// for (Edge edge: tmp.getOutEdges()) {
	// if (edge.getWord().equals(words[i])) {
	// tmp = edge.getToVertex();
	// flag = true;
	// edge.increaseCount();
	// break;
	// }
	// }
	//
	// // neu da co tu do trong lattice
	// if (flag == true) {
	// flag = false;
	// continue;
	// }
	//
	// //neu khong tao them 1 dinh
	// Vertex newVertex = new Vertex();
	//
	// addVertex(newVertex);
	//
	// //them canh moi
	// Edge newEdge = new Edge(poss[i],words[i], false);
	// addEdge(newEdge, tmp, newVertex);
	// tmp = newVertex;
	// }
	// Edge edge = new Edge(poss[poss.length-1],words[words.length-1],false);
	// addEdge(edge,tmp,upper);
	// Log.toConsole(TAG, "edge:"+edge.getToVertex().getId());
	// Log.toConsole(TAG, "edge:"+edge.getFromVertex().getId());
	// }

	private void addNewArc(Vertex lower, Vertex upper, String[] words,
			String[] poss) {
		if (words.length != poss.length)
			throw new IllegalArgumentException(
					"word array and pos array not same length!");
		Vertex tmp = lower;
		boolean flag = false;

		for (int i = 0; i < words.length - 1; ++i) {

			// neu khong tao them 1 dinh
			Vertex newVertex = new Vertex();

			addVertex(newVertex);

			// them canh moi
			Edge newEdge = new Edge(poss[i], words[i], false);
			addEdge(newEdge, tmp, newVertex);
			tmp = newVertex;
		}
		Edge edge = new Edge(poss[poss.length - 1], words[words.length - 1],
				false);
		addEdge(edge, tmp, upper);
		Log.toConsole(TAG, "edge:" + edge.getToVertex().getId());
		Log.toConsole(TAG, "edge:" + edge.getFromVertex().getId());

	}

	private boolean isWordSequenceExsisted(Vertex lower, Vertex upper,
			String[] word) {
		List<String> list = getStringsBetweenTwoVertex(lower, upper);
		for (String str : list) {
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
		// for (Vertex v: mVertexs) {
		// if (v.getOutEdges().size() == 0) continue;
		//
		// int numberOfEdge = v.getOutEdges().size();
		// float weight = Math.round(100.0/numberOfEdge);
		// for (Edge edge: v.getOutEdges()) {
		// edge.setWeight(weight/100f);
		// }
		//
		// v.getOutEdges().get(0).setWeight((100-weight*(numberOfEdge-1))/100f);
		// }

		for (Vertex v : mVertexs) {
			if (v.getOutEdges().size() == 0)
				continue;
			int totalCount = 0;
			for (Edge edge : v.getOutEdges()) {
				totalCount += edge.getCount();
			}
			// int numberOfEdge = v.getOutEdges().size();
			float weight = Math.round(100.0 / totalCount);
			for (Edge edge : v.getOutEdges()) {
				edge.setWeight(edge.getCount() * 1.0f / totalCount);
			}

			// v.getOutEdges().get(0).setWeight((100-weight*(numberOfEdge-1))/100f);
		}
	}

	private void numberingVertex() {
		// if (getRootVertex() == null) {
		// return;
		// }
		// HashMap<Vertex, Integer> deg = new HashMap<Vertex,Integer>();
		// for (Vertex v: mVertexs) {
		// deg.put(v, new Integer(v.getInEdges().size()));
		// }
		// int i =0;
		//
		// Queue<Vertex> queue = new ArrayDeque<Vertex>();
		// queue.add(getRootVertex());
		//
		//
		// Vertex v;
		// Vertex u; //adj vertex of v
		// while(!queue.isEmpty()) {
		// v = queue.remove();
		// v.setId(i);
		// for (Edge edge: v.getOutEdges()) {
		// u = edge.getToVertex();
		// deg.put(u, deg.get(u)-1);
		// if (deg.get(u) == 0) {
		// queue.add(u);
		// }
		// }
		// ++i;
		// }
		explored = new HashMap<Vertex, String>();
		mTopologyOrder = new ArrayList<Vertex>();
		Stack<Vertex> stack = new Stack<Vertex>();
		stack.add(mRootVerTex);

		for (Vertex v : getVertexs()) {
			v.calculateInVertexsNumber();
			explored.put(v, UNVISITED);
		}

		count = 0;
		Vertex v;

		while (!stack.isEmpty()) {
			v = stack.pop();
			v.setId(count);
			++count;
			mTopologyOrder.add(v);
			explored.put(v, VISITED);

			for (Vertex neighbor : v.getNeighbors()) {
				neighbor.decreaseInVertexsNumber();
				if (neighbor.getInVertexsNumber() == 0
						&& explored.get(neighbor).equals(UNVISITED)) {
					stack.add(neighbor);
				}

			}
		}

	}

	// public String toPlf() {
	// StringBuilder builder = new StringBuilder();
	// Vertex cur;
	// Queue<Vertex> queue = new ArrayDeque<Vertex>();
	// queue.add(getRootVertex());
	// builder.append("(");
	// while (!queue.isEmpty()) {
	// cur = queue.remove();
	// builder.append(toPlfByVertex(cur));
	//
	// List<Vertex> adjacentVertexs = new ArrayList<Vertex>();
	//
	// for (Edge edge: cur.getOutEdges()) {
	// if (queue.contains(edge.getToVertex()) == false) {
	// adjacentVertexs.add(edge.getToVertex());
	// }
	//
	// }
	// Collections.sort(adjacentVertexs);
	// for (int i = 0; i < adjacentVertexs.size(); ++i) {
	// queue.add(adjacentVertexs.get(i));
	// }
	// }
	// builder.append(")");
	// return builder.toString();
	// }

	private String toPlfByVertex(Vertex v) {
		StringBuilder builder = new StringBuilder();
		if (v.getOutEdges().size() == 0) {
			return "";
		}

		builder.append("(");
		for (Edge edge : v.getOutEdges()) {
			builder.append("('");
			builder.append(edge.getWord());
			builder.append("', ");
			builder.append(edge.getWeight());
			builder.append(", ");
			builder.append(edge.getToVertex().getId() - v.getId());
			builder.append("),");
		}
		builder.append("),");

		return builder.toString();
	}

	public int getLength() {
		return mVertexs.size();
	}

	public String toPlf() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		Vertex v;
		int dif;
		for (int i = 0; i < mTopologyOrder.size() - 1; ++i) {
			v = mTopologyOrder.get(i);
			builder.append("(");

			for (Edge out : v.getOutEdges()) {
				dif = out.getToVertex().getId() - out.getFromVertex().getId();
				builder.append("('" + out.getWord() + "', " + out.getWeight()
						+ ", " + dif + "),");
			}

			builder.append("),");

		}

		builder.append(")");
		return builder.toString();
	}

}
