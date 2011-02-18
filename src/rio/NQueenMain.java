package rio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.ws.FaultAction;

public class NQueenMain {
	public static void main(String[] args) {
		System.out.println("N-Queens parallelization");
		
		int n;
		if(args.length == 0)
			n = 8;
		else if(args.length == 1)
			n = Integer.parseInt(args[0]);
		else
			throw new IllegalArgumentException("Give no or one argument");

	}
}
