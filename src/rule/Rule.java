package rule;

import java.util.Arrays;

public class Rule implements Comparable<Rule>{
	public static final String WORD_DEVIDE_SYMBOL = " ";
	
	private String[] mSourceSide;
	private String[] mTargetSide;
	
	private String mSourceSideString;
	private String mTargetSideString;
	
	private int mCount;
	private float mFreq;
	
	public Rule(String[] sourceSide, String[] targetSide){
		mSourceSide = sourceSide;
		mTargetSide = targetSide;
		mCount = 0;
		mFreq = 0;
	}
	
	public Rule(String sourceSide, String targetSide) {
		mSourceSide = sourceSide.split(WORD_DEVIDE_SYMBOL);
		mTargetSide = targetSide.split(WORD_DEVIDE_SYMBOL);
		mSourceSideString = sourceSide;
		mTargetSideString = targetSide;
		mCount = 0;
		mFreq = 0;
	}
	
	public Rule(String sourceSide, String targetSide, int count) {
		mSourceSide = sourceSide.split(WORD_DEVIDE_SYMBOL);
		mTargetSide = targetSide.split(WORD_DEVIDE_SYMBOL);
		mSourceSideString = sourceSide;
		mTargetSideString = targetSide;
		mCount = count;
		mFreq = 0;
	}
	
	public void increaseCount() {
		mCount++;
	}
	
	//Getter
	public String[] getSourceSideArray() {
		return mSourceSide;
	}
	
	public String getSourceSideString() {
		return mSourceSideString;
	}
	
	public String[] getTargetSideArray() {
		return mTargetSide;
	}
	
	public String getTargetSideString() {
		return mTargetSideString;
	}
	
	
	
	public int getCount() {
		return mCount;
	} 
	
	public float getFreq() {
		return mFreq;
	}
	
	//Setter
	public void setCount(int count) {
		mCount = count;
	}
	
	public void setFreq(float freq) {
		mFreq = freq;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(getSourceSideString());
		builder.append("/");
		builder.append(getTargetSideString());
		builder.append("/");
		builder.append(mCount+"");
//		builder.append("|");
//		builder.append(mFreq+"");
		
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Rule)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		
		
		Rule rule = (Rule)obj;
		return rule.getSourceSideString().equals(getSourceSideString()) 
				&& rule.getTargetSideString().equals(getTargetSideString()); 
	}

	public int compareTo(Rule rule) {
		if (mCount > rule.getCount()) return -1;
		if (mCount < rule.getCount()) return 1;
		return 0;
	}
}
