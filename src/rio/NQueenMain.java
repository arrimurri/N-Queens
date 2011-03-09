package rio;

public class NQueenMain {
	public static void main(String[] args) {
		System.out.println("N-Queens parallelization");
		
		int queenAmount = 8;
		int threads = 0;
		
		if(args.length == 1)
			queenAmount = Integer.parseInt(args[0]);
		else if(args.length == 2) {
			queenAmount = Integer.parseInt(args[0]);
			threads = Integer.parseInt(args[1]);
		}
		else
			if(args.length != 0)
				throw new IllegalArgumentException("Give zero, one or two arguments");
		
		NQueen q;
		if(threads != 0)
			q = new NQueen(queenAmount, threads);
		else
			q = new NQueen(queenAmount);
		
		q.run();
		
	}
}
