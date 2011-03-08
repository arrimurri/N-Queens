package rio;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Patrik
 * 
 * Contains the logic of the algorithm. Starts out by sending one state of the board
 * to another thread, so that that thread can calculate the legal positions of the queens.
 *
 */
public class NQueen {
	ExecutorService service;
	int bSize;
	
	public NQueen(int boardSize, ExecutorService s) {
		this.service = s;
		this.bSize = boardSize;
	}
	
	public NQueen(int boardSize) {
		this.service = Executors.newCachedThreadPool();
		this.bSize = boardSize;
	}
	
	public NQueen(int boardSize, int threadCount) {
		this.service = Executors.newFixedThreadPool(threadCount);
		this.bSize = boardSize;
	}
	
	public void run() {
		for(int i = 0; i < this.bSize; i++) {
			boolean[][] temp = new boolean[this.bSize][this.bSize];
			temp[0][i] = true;
			BoardState bs = new BoardState(temp);
			BoardState res = recCalc(bs, 1);
			System.out.println("Result:");
			System.out.println(res);
		}
	}
	
	private BoardState recCalc(BoardState bs, int nextRow) {
		BoardState retState = null;
		if(nextRow == bs.getBoardSize()) {
			System.out.println("No more rows to read");
			return bs;
		}
		BoardCalculation bc = new BoardCalculation(bs, nextRow);
		boolean[] returnRow = null;
		try {
			returnRow = service.submit(bc).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(returnRow != null) {
			for(int i = 0; i < returnRow.length; i++) {
				boolean[] temp = new boolean[returnRow.length];
				if(returnRow[i]) {
					BoardState newstate = new BoardState(bs.getBoard());
					System.out.println(returnRow);
					if((nextRow + 1) < newstate.getBoardSize()) {
						newstate.changeRow(returnRow, nextRow);
						System.out.println("New State");
						System.out.println(newstate);
						retState = recCalc(newstate, nextRow + 1);
					}					
				}
			}
		}
		return retState;
	}
	
}
