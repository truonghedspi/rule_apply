package lattice;

public class Node {
	private String mLabel;
	private float mWeight;
	private int mNumber;
	
	public Node(String label, int number) {
		setLabel(label);
		setNumber(number);
		setWeight(0);
	}
	
	public Node(String label, float weight, int number) {
		setLabel(label);
		setWeight(weight);
		setNumber(number);
	}
	
	//Setter
	
	public void setLabel(String label) {
		this.mLabel = label;
	}
	
	public void setWeight(float weight) {
		this.mWeight = weight;
	}
	
	public void setNumber(int number) {
		this.mNumber = number;
	}
	
	//Getter
	
	public String getLabel() {
		return mLabel;
	}
	
	public float getWeight() {
		return mWeight;
	}
	
	public int getNumber() {
		return mNumber;
	}
	
	@Override
	public String toString() {
		String res = "";
		res += "label:" + getLabel() + "-";
		res += "weight:" + getWeight() + "-";
		res += "number:" + getNumber();
		
		return res;
	}
	

	
	
}
