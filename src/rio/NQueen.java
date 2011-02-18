package rio;

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
		BoardState bstate = new BoardState(this.bSize);
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(new BoardCalculation(BoardState, 0));
	}
	
}
