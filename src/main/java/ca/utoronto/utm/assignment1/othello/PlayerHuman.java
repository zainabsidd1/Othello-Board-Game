package ca.utoronto.utm.assignment1.othello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * TODO: Document this class and make minimal changes as necessary.
 * 
 * @author arnold
 *
 */
public class PlayerHuman extends Player {
	
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	private static final String IO_ERROR_MESSAGE = "I/O Error";
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));


    /** Initialize an Othello game for this player
    */
	public PlayerHuman(Othello othello, char player) {
		super(othello, player);
    }

    @Override
	public Move getMove() {
		
		int row = getInput("row: ");
		int col = getInput("col: ");
		return new Move(row, col);
	}

    // Gets a valid move from the user.
    // If the move is invalid, print the error message

    /**
     *
     * @param message receives the row/col input from the user
     * @return the row/col entered by the user
     */
	private int getInput(String message) {
		int move, lower = 0, upper = 7;
		while (true) {
			try {
				System.out.print(message);
				String line = PlayerHuman.stdin.readLine();

                if (line==null) return -1;
				move = Integer.parseInt(line);
				if (lower <= move && move <= upper) {
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (IOException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
				return -1;
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
                return -1;
			}
		}

	}
}
