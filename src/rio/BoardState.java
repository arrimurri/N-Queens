package rio;

public class BoardState {
	
	private boolean[][] board;
	
	public BoardState(boolean[][] b) {
		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b[i].length; i++) {
				this.board[i][j] = b[i][j];
			}
		}
	}
	
	public BoardState(int n) {
		this.board = new boolean[n][n];
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; i++) {
				this.board[i][j] = false;
			}
		}
	}
	
	public boolean isOccupied(int x, int y) {
		return this.board[x][y];
	}
	
	public int getBoardSize() {
		return this.board.length;
	}

}
