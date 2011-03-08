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
			for(int j = 0; j < this.board[i].length; j++) {
				this.board[i][j] = false;
			}
		}
	}
	
	public void changeRow(boolean[] row, int rowNum) {
		boolean[] tempRow = new boolean[row.length];
		for(int i = 0; i < row.length; i++) {
			tempRow[i] = row[i];
		}
		this.board[rowNum] = tempRow;
	}
	
	public boolean isOccupied(int x, int y) {
		return this.board[x][y];
	}
	
	public int getBoardSize() {
		return this.board.length;
	}
	
	public boolean[][] getBoard() {
		return this.board;
	}
	
	public String toString() {
		String ret = "[";
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				if(j == 0)
					ret += this.board[i][j];
				else
					ret += ", " + this.board[i][j];
			}
			if(i != (this.board.length - 1))
				ret += "]\n[";
			else
				ret += "]";
		}
		return ret;
	}

}
