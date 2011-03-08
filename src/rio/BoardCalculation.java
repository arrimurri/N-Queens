package rio;

import java.util.concurrent.Callable;

public class BoardCalculation implements Callable<boolean[]> {
	
	final BoardState bState;
	final int rNumber;
	
	public BoardCalculation(BoardState boardState, int rowNumber) {
		this.bState = boardState;
		this.rNumber = rowNumber;
	}

	@Override
	public boolean[] call() throws Exception {
		int n = this.bState.getBoardSize();
		boolean[] ret = new boolean[n];
		for(int i = 0; i < n; i++) {
			if(this.checkUp(i) &&
			   this.checkUpLeft(i) &&
			   this.checkUpRight(i)) {
				ret[i] = true;
				System.out.println("True for this " + i + " in row " + this.rNumber);
				System.out.println(this.bState);
			}
			else {
				ret[i] = false;
				System.out.println("False for this " + i + " in row " + this.rNumber);
				System.out.println(this.bState);
			}
		}
		return ret;
	}
	
	private boolean checkUpRight(int colIndex) {
		for(int i = this.rNumber, j = colIndex; i > -1; i--, j++) {
			if(j < this.bState.getBoardSize()) {
				if(this.bState.isOccupied(i, j))
					return false;
			}
		}
		return true;
	}

	private boolean checkUpLeft(int colIndex) {
		for(int i = this.rNumber, j = colIndex; i > -1; i--, j--) {
			if(j > -1) {
				if(this.bState.isOccupied(i, j))
					return false;
			}
		}
		return true;
	}

	private boolean checkUp(int colIndex) {
		for(int i = 0; i < this.rNumber; i++) {
			if(this.bState.isOccupied(i, colIndex))
				return false;
		}
		return true;
	}
	
	public int getRowNumber() {
		return this.rNumber;
	}
	
}
