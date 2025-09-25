package ca.utoronto.utm.assignment1.othello;

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 * 
 * Othello makes use of the OthelloBoard.
 * z
 * @author arnold
 *
 */
public class OthelloBoard {
	
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;

	public OthelloBoard(int dim) {
		this.dim = dim;
		board = new char[this.dim][this.dim];
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.board[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
		this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
	}

	public int getDimension() {
		return this.dim;
	}{
    }

	/**
	 * 
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
	public static char otherPlayer(char player) {
        if(player==P1){
            return P2;
        } else if(player==P2){
            return P1;
        }else{
            return EMPTY;
        }
    }

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
		if(validCoordinate(row, col)) {
            return board[row][col];
        } else{
            return EMPTY;
        }
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether (row,col) is a position on the board. Example: (6,12) is not
	 *         a position on the board.
	 */
	private boolean validCoordinate(int row, int col) {
		if(row<0 || row>this.dim-1 || col<0 || col>this.dim-1){
            return false;
        } else{
            return true;
        }
	}

	/**
	 * Check if there is an alternation of P1 next to P2, starting at (row,col) in
	 * direction (drow,dcol). That is, starting at (row,col) and heading in
	 * direction (drow,dcol), you encounter a sequence of at least one P1 followed
	 * by a P2, or at least one P2 followed by a P1. The board is not modified by
	 * this method. Why is this method important? If
	 * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
	 * assuming that square is EMPTY, is a valid move, resulting in a collection of
	 * P2 being flipped.
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
	 *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
	 *         alternation
	 */
	private char alternation(int row, int col, int drow, int dcol) {
		if ((drow==0 && dcol==0) ||
                (!validCoordinate(row, col))||
                get(row,col)==EMPTY) {
            return EMPTY;
        }
        char thisPlayer = get(row,col);
        char oppPlayer = otherPlayer(thisPlayer);
        int currRow = row+drow;
        int currCol = col+dcol;
        boolean sawOpp = false;
        while(validCoordinate(currRow, currCol) && get(currRow, currCol) != EMPTY){
            char curr = get(currRow, currCol);
            if(curr==oppPlayer){
                return oppPlayer;
            } else if (curr==thisPlayer){
                currRow = currRow + drow;
                currCol = currCol + dcol;
            }
        }
        return EMPTY;
	}

    public char testAlt(int row, int col, int drow, int dcol) {
        return alternation(row,col,drow,dcol);
    }

	/**
	 * flip all other player tokens to player, starting at (row,col) in direction
	 * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
	 * result in a flip to OOOO
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   the row direction, in {-1,0,1}
	 * @param dcol   the col direction, in {-1,0,1}
	 * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               flip to.
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flip(int row, int col, int drow, int dcol, char player) {

        char oppPlayer = otherPlayer(player);
        int currRow = row;
        int currCol = col;
        int count = 0;
        boolean sawPlayer = false;
        while(validCoordinate(currRow, currCol) && get(currRow, currCol) != EMPTY){
            if (get(currRow,currCol)==oppPlayer){
                count++;
                currRow += drow;
                currCol += dcol;
            } else if (get(currRow,currCol)==player){
                sawPlayer = true;
                break;
            } else{
                break;
            }
        }
        if(!sawPlayer){
            return -1;
        } else if(count==0){
            return 0;
        }
        int thisRow = row;
        int thisCol = col;
        for(int i=0;i<count;i++){
            board[thisRow][thisCol] = player;
            thisRow += drow;
            thisCol += dcol;
        }
        return count;

	}

	/**
	 * Return which player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1,P2,EMPTY
	 */
	private char hasMove(int row, int col, int drow, int dcol) {
        if(!validCoordinate(row, col)||get(row,col)!=EMPTY){
            return EMPTY;
        }
        int currRow = row+drow;
        int currCol = col+dcol;
        if(!validCoordinate(currRow, currCol) || get(currRow,currCol)==EMPTY){
            return EMPTY;
        }
        char oppPlayer = get(currRow,currCol); //beside empty space
        char thisPlayer = otherPlayer(oppPlayer); //opposite
        boolean sawPlayer = false;
		while(validCoordinate(currRow, currCol) && get(currRow,currCol)==oppPlayer){
                currRow+=drow;
                currCol+=dcol;
        }
        if(validCoordinate(currRow,currCol) && get(currRow,currCol)==thisPlayer){
            return thisPlayer;
        } else{
            return EMPTY;
        }
	}

	/**
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	public char hasMove() {
        boolean p1 = false; //X
        boolean p2 = false; //Y
		for (int row = 0; row < this.dim; row++) { // each row
            for (int col = 0; col < this.dim; col++) { // each column

                for (int r=-1;r<=1;r++){ // each row direction
                    for (int c=-1;c<=1;c++){ // each col directions
                        char res = hasMove(row, col, r, c);
                        if(res==P1){
                            p1 = true;
                        } else if(res==P2){
                            p2 = true;
                        }
                    }
                }
            }
        }
        if(p1&&p2){
            return BOTH;
        } else if(p1){
            return P1;
        } else if (p2){
            return P2;
        }
        return EMPTY;

	}

	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {
		// HINT: Use some of the above helper methods to get this methods
		// job done!!
        if(!validCoordinate(row, col) || get(row,col)!=EMPTY){
            return false;
        }
        boolean moved = false;
        for(int r=-1;r<=1;r++){
            for(int c=-1;c<=1;c++){
                if (!(r==0 && c==0)) {
                    char res = hasMove(row, col, r, c);
                    if (res == player) {
                        int count = flip(row + r, col + c, r, c, player);
                        if (count > 0) {
                            moved = true;
                        }
                    }
                }
            }
        }
        if (moved){
            board[row][col] = player;
        }
		return moved;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {
		int count = 0;
        for(int row = 0; row < dim; row++){
            for(int col = 0; col < dim; col++){
                if(get(row,col)==player){
                    count++;
                }
            }
        }
		return count;
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		/**
		 * See assignment web page for sample output.
		 */
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += this.board[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard. Output is on assignment page.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloBoard ob = new OthelloBoard(8);
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				ob.board[row][col] = P1;
			}
		}
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));

		// Should all be blank
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (row == 0 || col == 0) {
					ob.board[row][col] = P2;
				}
			}
		}
		System.out.println(ob.toString());

		// Should all be P2 (O) except drow=0,dcol=0
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		// Can't move to (4,4) since the square is not empty
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));

		ob.board[4][4] = EMPTY;
		ob.board[2][4] = EMPTY;

		System.out.println(ob.toString());

		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
			}
		}
		System.out.println("who has a move=" + ob.hasMove());
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
		System.out.println(ob.toString());

	}
}
