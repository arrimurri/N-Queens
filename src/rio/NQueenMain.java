package rio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NQueenMain {
	public static void main(String[] args) {
		System.out.println("N-Queens parallelization");
		
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new BoardCalculation());
	}
}
