package rio;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class BoardCalculation implements Callable<BoardState> {
	
	final BoardState bState;
	final int rNumber;
	final ExecutorService eService;
	List<BoardState> retList;
	
	public BoardCalculation(BoardState boardState, int rowNumber, ExecutorService service, List<BoardState> l) {
		this.bState = boardState;
		this.rNumber = rowNumber;
		this.eService = service;
		this.retList = l;
	}

	@Override
	public BoardState call() throws Exception {
		// If all rows have been counted return with that state
		if(this.rNumber >= this.bState.getBoardSize()) {
			//System.out.println("There is a solution");
			synchronized (retList) {
				this.retList.add(this.bState);
			}
			return this.bState;		
		}
		
		int n = this.bState.getBoardSize();
		Future<BoardState> ret = null;
		
		for(int i = 0; i < n; i++) {
			if(this.checkUp(i) &&
			   this.checkUpLeft(i) &&
			   this.checkUpRight(i)) {
				//System.out.println("True for this " + i + " in row " + this.rNumber);
				//System.out.println(this.bState);
				BoardState newState = new BoardState(this.bState.getBoard());
				boolean[] temp = new boolean[this.bState.getBoardSize()];
				for(int j = 0; j < temp.length; j++) 
					temp[j] = false;
				temp[i] = true;
				newState.changeRow(temp, this.rNumber);
				//System.out.println("New state");
				//System.out.println(newState);
				
				// "Recursively" call a new BoardCalculation
				BoardCalculation newbc = new BoardCalculation(newState, this.rNumber + 1, this.eService, this.retList);
				
				// Return a final state
				ret = this.eService.submit(newbc);
			}
			else {
				ret = null;
				//System.out.println("False for this " + i + " in row " + this.rNumber);
				//System.out.println(this.bState);
			}
		}
		if(ret == null)
			return null;
		return ret.get();
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
