package ca.utoronto.utm.assignment1.othello;

import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 * @author arnold
 *
 */
public class Othello {
	public static final int DIMENSION = 8; // This is an 8x8 game
	private char whosTurn = OthelloBoard.P1; // P1 moves first!
	private int numMoves = 0;
    public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
    public OthelloBoard board = new OthelloBoard(DIMENSION);

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
        if(numMoves>60){
            return EMPTY;
        }
        return whosTurn;
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
        boolean result = board.move(row,col, whosTurn);
        if (result){
            if(whosTurn==P1){
                whosTurn = P2;
            }else{
                whosTurn = P1;
            }
            numMoves++;
        }
        return result;
	}

    public void forfeitMove(char player){
        if(player==P1){
            whosTurn = P2;
        } else{
            whosTurn = P1;}

    }

    public OthelloBoard getBoard() {
        OthelloBoard newBoard = new OthelloBoard(DIMENSION);
        for(int r=0;r<DIMENSION;r++){
            for(int  c=0;c<DIMENSION;c++){
                newBoard.getBoard()[r][c] = this.board.get(r,c);
            }
        }
        return newBoard;
    }


	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return board.getCount(player);
	}

	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		if ((board.hasMove() != EMPTY)){
            return EMPTY;
        }
        int P1Count = board.getCount(P1);
        int P2Count = board.getCount(P2);
        if (P1Count > P2Count) {
            return P1;
        } else if(P2Count > P1Count){
            return P2;
        }
        return EMPTY;
	}

	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
        int totalTokens = getCount(P1)+getCount(P2);
        return (totalTokens==64 || board.hasMove()==EMPTY);

	}

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString();
	}



	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random rand = new Random();

		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if (o.move(row, col)) {
				System.out.println("makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}

	}
}