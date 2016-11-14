package lattice;


public class Lattice {
	
	public static final int MAX_LENGTH = 100;
	public static final String WORD_DEVIDE_SYMBOL = " ";
	
	private ListNode nodeMatrix[][] = new ListNode[MAX_LENGTH][MAX_LENGTH];
	
	public Lattice(String sourceSentence) {
		createMonotone(sourceSentence);
	}
	
	private void createMonotone(String sourceSentence) {
		String[] wordArray = sourceSentence.split(WORD_DEVIDE_SYMBOL);
		ListNode listNode;
		Node newNode;
		for (int i = 0; i < wordArray.length; ++i) {
			listNode = new ListNode();
			newNode = new Node(wordArray[i], i+1);
			listNode.addNode(newNode);
		}
	}
}
