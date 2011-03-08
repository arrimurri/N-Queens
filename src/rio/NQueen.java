package rio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
		for(int i = 0; i < this.bSize; i++) {
			for(int j = 0; j < this.bSize; j++) {
				BoardCalculation bc = new BoardCalculation(bstate, i);
				Future<boolean[]> returnRow = service.submit(bc);				
			}
		}
	}
	
}
