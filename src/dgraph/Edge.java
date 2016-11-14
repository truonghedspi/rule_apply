package dgraph;

import java.util.ArrayDeque;
import java.util.Queue;

public class Edge {
	/**Pos Tag of Word*/
	private String mPos;
	
	/**Word*/
	private String mWord;
	
	/**Weight */
	private float mWeight;
	
	private Vertex mFrom;

	private Vertex mTo;
	
	/**Edge in origin source sentence */
	private boolean mIsMainPath;
	
	public Edge(String pos, String word, boolean mainPath ) {
		this.mPos =pos;
		this.mWord = word;
		this.mIsMainPath = mainPath;
	}
	
	// Getter
	public Vertex getFromVertex() {
		return mFrom;
	}
	
	public Vertex getToVertex() {
		return mTo;
	}
	
	public String getWord() {
		return mWord;
	}
	
	public String getPos() {
		return mPos;
	}
	
	public float getWeight() {
		return mWeight;
	}
	
	//Setter
	public void setFromVertex(Vertex from) {
		this.mFrom = from;
	}
	
	public void setToVertex(Vertex to) {
		this.mTo = to;
	}
	
	public boolean isOnMainPath() {
		return this.mIsMainPath;
	}
	
	public void mainPath(boolean isMain) {
		this.mIsMainPath = isMain;
	}
	
	public void setWeight(float weight) {
		this.mWeight = weight;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("word:"+mWord+"\n");
		builder.append("pos:"+mPos+"\n");
		
		return builder.toString();
	}
	

}
