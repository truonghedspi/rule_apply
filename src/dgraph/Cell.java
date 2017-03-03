package dgraph;

public class Cell {
	private String mWord;
	
	private String mPos;
	
	private float mWeight;
	
	public Cell(String word, String pos) {
		setWord(word);
		setPos(pos);
	}
	
	public void setWord(String word) {
		mWord = word;
	}
	
	public void setPos(String pos) {
		mPos = pos;
	}
	
	public void setWeight(float weight) {
		mWeight = weight;
	
	}
	
	public String getPos() {return mPos;}
	
	public String getWord() {return mWord;}
	
	public float getWeight() {return mWeight;}
	
	
}
