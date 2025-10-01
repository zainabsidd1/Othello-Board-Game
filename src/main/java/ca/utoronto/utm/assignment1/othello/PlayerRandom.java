package ca.utoronto.utm.assignment1.othello;

import java.util.ArrayList;
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 * 
 * @author arnold
 *
 */
public class PlayerRandom {
	
	private Random rand = new Random();
    private Othello othello;
    private char player;
    private int dim = 8;

    public PlayerRandom(Othello othello, char player) {
        this.othello = othello;
        this.player = player;
    }

    /**
     *
     * @return a random move from the list of possible moves
     */

	public Move getMove() {
        ArrayList<Move> moves = new ArrayList<>();

        for(int row = 0; row < dim; row++) {
            for(int col = 0; col < dim; col++) {
                if(othello.board.get(row,col)!=OthelloBoard.EMPTY) continue;
                OthelloBoard thisBoard = othello.getBoard(); // new board copy
                if (thisBoard.move(row,col,player)) {
                    moves.add(new Move(row,col));
                }
            }
        }
        if (moves.isEmpty()) return null;
        return moves.get(rand.nextInt(moves.size()));
	}
}
