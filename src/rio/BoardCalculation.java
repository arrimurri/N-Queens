package rio;

import java.util.List;
import java.util.concurrent.Callable;

public class BoardCalculation implements Callable<BoardState> {
	
	final BoardState bState;
	int rNumber;
	List<Boolean> retList;
	int boardSize;
	
	public BoardCalculation(BoardState boardState, int rowNumber, List<Boolean> retList2) {
		this.bState = boardState;
		this.rNumber = rowNumber;
		this.retList = retList2;
		this.boardSize = this.bState.getBoardSize();
	}

	@Override
	public BoardState call() throws Exception {
		// If all rows have been counted return with that state
		/*
		if(this.rNumber >= this.boardSize) {
			//System.out.println("There is a solution");
			BoardState retbs = new BoardState(this.bState.getBoard());
			synchronized (this.retList) {
				this.retList.add(retbs);
			}
			return this.bState;		
		}
		*/
		
		BoardState ret = recCheck();
		
		if(ret == null)
			return null;
		return ret;
	}
	
	private BoardState recCheck() {
		if(this.rNumber >= this.boardSize) {
			//System.out.println("There is a solution");
			//System.out.println(bs);
			BoardState retbs = new BoardState(this.bState.getBoard());
		
			synchronized (this.retList) {
				this.retList.add(true);
			}
			
			return retbs;		
		}
		BoardState retState = null;
		for(int i = 0; i < this.bState.getBoardSize(); i++) {
			if(this.checkUp(this.bState, i, this.rNumber) &&
			   this.checkUpLeft(this.bState, i, this.rNumber) && 
			   this.checkUpRight(this.bState, i, this.rNumber)) {
				this.bState.modifyBoard(this.rNumber, i, true);
				
				this.rNumber++;
				retState = recCheck();
				this.rNumber--;
				
				this.bState.modifyBoard(this.rNumber, i, false);
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
