package alignment;

import Logger.Log;

public class AlignMatrix {
	private static final String TAG = "AlignMatrix";
	private static int line = 0;
	public static final int MAX = 100;
	public static final String WORD_DEVIDE_SYMBOL = " ";
	private boolean[][] mMatrix;
	
	public AlignMatrix(String alignLine) {
		createMatrix(alignLine);
		++line;
		fillNullAlignment();
	}
	
	//fill null alignment in all matrix
	private void fillNullAlignment() {
		for (int row = 1; row < MAX; ++row) {
			if (isNullAlignment(row)) {
				fillNullAlignment(row);
			}
		}
	}
	
	//fill nullAlignment in source index
	//return align of index after fill
	private void fillNullAlignment(int index) {
		for (int prev = index-1; prev >= 0; --prev) {
			if (!isNullAlignment(prev)) {
				mMatrix[index][getMaxAlignment(prev)] = true;
				return;
			}
		}
	}
	
	private void createMatrix(String alignLine) {
		mMatrix = new boolean[MAX][MAX];
		
		for (int row = 0; row < MAX; ++row) {
			for (int col = 0; col < MAX; ++col) {
				mMatrix[row][col] = false;
			}
		}
		
		String[] alignArr = alignLine.split(WORD_DEVIDE_SYMBOL);
		String[] alignElementArr;
		int row, col;
		for(String align: alignArr) {
			alignElementArr = align.split("-");
			row = Integer.parseInt(alignElementArr[0]);
			col = Integer.parseInt(alignElementArr[1]);
			Log.toConsole(TAG, "line:" +line+":"+row+"-"+col);
			mMatrix[row][col] = true;
			
		} 
	}
	
	public int getMaxAlignment(int sourceIndex) {
		if (sourceIndex < 0 || sourceIndex >= MAX) {
			return -1;
		}
		
		int i = MAX-1;
		while(i > 0) {
			if (mMatrix[sourceIndex][i] == true) {
				return i;
			}
			i--;
		}
		
		return i;
	}
	
	
	public boolean isNullAlignment(int sourceIndex) {
		if (getMaxAlignment(sourceIndex) == -1) {
			return true;
		}
		
		return false;
	}
}
