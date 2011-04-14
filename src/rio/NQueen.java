package rio;

import java.util.ArrayList;
import java.util.List;
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
	List<BoardState> retList = new ArrayList<BoardState>();
	
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
			boolean[] temp = new boolean[this.bSize];
			temp[i] = true;
			BoardState bs = new BoardState(this.bSize);
			bs.changeRow(temp, 0);
			// BoardState res = recCalc(bs, 1);
			BoardCalculation bc = new BoardCalculation(bs, 1, this.retList);

			try {
				this.service.submit(bc).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			//System.out.println("Result:");
			//System.out.println(retstate);
		}
		/*
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		*/

		this.service.shutdown();
		while(!this.service.isTerminated()) {}
		int count = 0;
		if(this.retList.isEmpty()) {
			System.out.println("There are no solutions");
		}
		for(BoardState b : this.retList) {
			if(b != null) {
				count++;
				//System.out.println("Solution #" + count + ":");
				//System.out.println(b);
			}
		}
		System.out.println("Number of solutions: " + count);
	}	
}
