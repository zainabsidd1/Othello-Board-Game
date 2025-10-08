package ca.utoronto.utm.assignment1.othello;

public abstract class OthelloController {
    protected Othello othello;
    protected Player player1;
    protected Player player2;
    public OthelloController(){
        this.othello = new Othello();
    }

    /**
     * Plays the game for any game including a Human Player
     * Controllers with no Human Players will override this method
     */
    public void play(){

        while (!othello.isGameOver()) {
            this.report();

            Move move = null;
            char whosTurn = othello.getWhosTurn();

            if (whosTurn == OthelloBoard.P1)
                move = player1.getMove();
            if (whosTurn == OthelloBoard.P2)
                move = player2.getMove();

            if (move == null || move.getRow() < 0 || move.getCol() < 0) {
                break;
            }

            this.reportMove(whosTurn, move);
            othello.move(move.getRow(), move.getCol());
        }
        this.reportFinal();
    }

    /**
     *
     * @param whosTurn
     * @param move the current players move.
     */
    private void reportMove(char whosTurn, Move move) {
        System.out.println(whosTurn + " makes move " + move + "\n");
    }

    /**
     * Outputs the current state of the board, each players token count,
     * and who's turn it is next.
     */
    private void report() {

        String s = othello.getBoardString() + OthelloBoard.P1 + ":"
                + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
                + othello.getWhosTurn() + " moves next";
        System.out.println(s);
    }

    /**
     * Output the final state of the board, each player's token count, and
     * which player won.
     */
    private void reportFinal() {

        String s = othello.getBoardString() + OthelloBoard.P1 + ":"
                + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2)
                + "  " + othello.getWinner() + " won\n";
        System.out.println(s);
    }

}
