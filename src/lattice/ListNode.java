package lattice;

import java.util.ArrayList;
import java.util.List;

public class ListNode {
	private List<Node> nodeList = new ArrayList<Node>();
	
	public List<Node> getList() {
		return nodeList;
	}
	
	public Node getElement(int index) {
		return nodeList.get(index);
	}
	
	public void addNode(Node node) {
		nodeList.add(node);
	}
	
	
}
