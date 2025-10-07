package ca.utoronto.utm.assignment1.othello;

/**
 * PlayerGreedy makes a move by considering all possible moves that the player
 * can make. Each move leaves the player with a total number of tokens.
 * getMove() returns the first move which maximizes the number of
 * tokens owned by this player. In case of a tie, between two moves,
 * (row1,column1) and (row2,column2) the one with the smallest row wins. In case
 * both moves have the same row, then the smaller column wins.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * See the examples supplied in the assignment handout.
 * 
 * @author arnold
 *
 */

public class PlayerGreedy extends Player {


    private static final int dim = 8;

    public PlayerGreedy(Othello othello, char player) {
        super(othello, player);
    }

    /**
     *
     * @return Move object representing the move which flips the most tokens
     */
    @Override
	public Move getMove() {

        int maxTokens = 0;
        int maxRow = -1;
        int maxCol = -1;
        for (int row = 0; row < dim; row++) { // each row
            for (int col = 0; col < dim; col++) { // each column
                if (othello.board.get(row,col)!=OthelloBoard.EMPTY) continue;
                OthelloBoard thisBoard = othello.getBoard(); // new board copy
                if(thisBoard.move(row,col, player)){
                    int currTokens = thisBoard.getCount(player);
                    if (currTokens > maxTokens || (currTokens == maxTokens && row < maxRow) ||
                                (currTokens == maxTokens && row == maxRow && col < maxCol)
                        ) {
                            maxTokens = currTokens;
                            maxRow = row;
                            maxCol = col;
                        }
                }
            }
        }
        if(maxTokens == 0 ||maxRow<0 || maxCol<0) return null;
        return new Move(maxRow,maxCol);
    }


}



