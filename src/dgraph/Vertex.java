package dgraph;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex>{
	private int mId;
	private List<Edge> mInEdges;
	private List<Edge> mOutEdges;
	
	public Vertex() {
		Graph.i++;
		this.mInEdges = new ArrayList<Edge>();
		this.mOutEdges = new ArrayList<Edge>();
	}
	
	
	public List<Edge> getInEdges() {
		return mInEdges;
	}
	
	public List<Edge> getOutEdges() {
		return mOutEdges;
	}
	
	public int getId() {
		return mId;
	}
	
	public void setId(int id) {
		this.mId = id;
	}
	
	/**
	 * @param e : Edge come to vertex
	 */
	private void addInEdge(Edge e) {
		if (mInEdges.contains(e)) throw new IllegalArgumentException();
		mInEdges.add(e);
		e.setToVertex(this);
	}
	
	/**
	 * @param e : Edge out from vertex
	 */
	private void addOutEdge(Edge e) {
		if (mOutEdges.contains(e)) throw new IllegalArgumentException();
		mOutEdges.add(e);
		e.setFromVertex(this);
	}
	
	/**
	 * Add new adjacency
	 * @param v: new adjacency vertex
	 * @param e: edge between two vertex
	 */
	public Vertex addVertical(Vertex v, Edge e) {
		if (mOutEdges.contains(e) ||
				v.getInEdges().contains(e)) {
			throw new IllegalArgumentException();
		}
		
		addOutEdge(e);
		v.addInEdge(e);
		return this;
	}
	
	
	public boolean isEndVertex() {
		return mOutEdges.isEmpty();
	}
	
	public Edge getEdgeOnMainPath() {
		for (Edge edge: getOutEdges()) {
			if (edge.isOnMainPath()) {
				return edge;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vertex id:"+mId+"\n");
		builder.append("Edges:\n");
		for (Edge edge: getOutEdges()) {
			builder.append(edge.toString());
		}
		return builder.toString();
	}


	public int compareTo(Vertex o) {
		// TODO Auto-generated method stub
		if (getId() > o.getId()) {
			return 1;
		} 
		
		if (getId() < o.getId()) {
			return -1;
		}
		
		return 0;
	}
	

	
}