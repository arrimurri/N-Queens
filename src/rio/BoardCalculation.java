package rio;

import java.util.List;
import java.util.concurrent.Callable;

public class BoardCalculation implements Callable<BoardState> {
	
	final BoardState bState;
	final int rNumber;
	List<BoardState> retList;
	int boardSize;
	
	public BoardCalculation(BoardState boardState, int rowNumber, List<BoardState> l) {
		this.bState = boardState;
		this.rNumber = rowNumber;
		this.retList = l;
		this.boardSize = this.bState.getBoardSize();
	}

	@Override
	public BoardState call() throws Exception {
		// If all rows have been counted return with that state
		if(this.rNumber >= this.boardSize) {
			//System.out.println("There is a solution");
			synchronized (retList) {
				this.retList.add(this.bState);
			}
			return this.bState;		
		}
		
		int n = this.boardSize;
		BoardState ret = null;
		
		for(int i = 0; i < n; i++) {
			if(this.checkUp(this.bState, i, this.rNumber) &&
			   this.checkUpLeft(this.bState, i, this.rNumber) &&
			   this.checkUpRight(this.bState, i, this.rNumber)) {
				//System.out.println("True for this " + i + " in row " + this.rNumber);
				//System.out.println(this.bState);
				BoardState newState = new BoardState(this.bState.getBoard());
				boolean[] temp = new boolean[this.boardSize];
				for(int j = 0; j < temp.length; j++) 
					temp[j] = false;
				temp[i] = true;
				newState.changeRow(temp, this.rNumber);
				
				ret = recCheck(newState, this.rNumber + 1);
				//System.out.println("New state");
				//System.out.println(newState);
				
				// "Recursively" call a new BoardCalculation
				// BoardCalculation newbc = new BoardCalculation(newState, this.rNumber + 1, this.eService, this.retList);
				
				// Return a final state
				// ret = this.eService.submit(newbc);
			}
			else {
				ret = null;
				//System.out.println("False for this " + i + " in row " + this.rNumber);
				//System.out.println(this.bState);
			}
		}
		if(ret == null)
			return null;
		return ret;
	}
	
	private BoardState recCheck(BoardState bs, int rowNumber) {
		if(rowNumber >= this.boardSize) {
			//System.out.println("There is a solution");
			//System.out.println(bs);
			synchronized (this.retList) {
				this.retList.add(bs);
			}
			return bs;		
		}
		BoardState retState = null;
		for(int i = 0; i < bs.getBoardSize(); i++) {
			if(this.checkUp(bs, i, rowNumber) &&
			   this.checkUpLeft(bs, i, rowNumber) && 
			   this.checkUpRight(bs, i, rowNumber)) {
				BoardState newState = new BoardState(bs.getBoard());
				boolean[] temp = new boolean[this.boardSize];
				for(int j = 0; j < temp.length; j++) 
					temp[j] = false;
				temp[i] = true;
				newState.changeRow(temp, rowNumber);
				retState = recCheck(newState, rowNumber + 1);
			}
			else {
				retState = null;
			}
		}
		return retState;
	}
	
	private boolean checkUpRight(BoardState bs, int colIndex, int rowindex) {
		for(int i = rowindex, j = colIndex; i > -1; i--, j++) {
			if(j < this.boardSize) {
				if(bs.isOccupied(i, j))
					return false;
			}
		}
		return true;
	}

	private boolean checkUpLeft(BoardState bs, int colIndex, int rowindex) {
		for(int i = rowindex, j = colIndex; i > -1; i--, j--) {
			if(j > -1) {
				if(bs.isOccupied(i, j))
					return false;
			}
		}
		return true;
	}

	private boolean checkUp(BoardState bs, int colIndex, int rowindex) {
		for(int i = 0; i < rowindex; i++) {
			if(bs.isOccupied(i, colIndex))
				return false;
		}
		return true;
	}
	
	public int getRowNumber() {
		return this.rNumber;
	}
	
}
