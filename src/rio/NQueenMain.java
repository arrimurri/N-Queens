package rio;

public class NQueenMain {
	public static void main(String[] args) {
		System.out.println("N-Queens parallelization");
		
		long start = System.currentTimeMillis();
		int queenAmount = 8;
		int threads = 0;
		
		if(args.length == 1)
			queenAmount = Integer.parseInt(args[0]);
		else
			if(args.length != 0)
				throw new IllegalArgumentException("Give zero, one or two arguments");
		
		NQueen q;
		if(threads != 0)
			q = new NQueen(queenAmount, threads);
		else
			q = new NQueen(queenAmount);
		
		q.run();
		long end = System.currentTimeMillis();
		
		System.out.println("Time it took to do the calculations: " + ((end - start) / 1000) + " " +
				"seconds, more accurately " + (end - start) + " milliseconds");
		
	}
}
