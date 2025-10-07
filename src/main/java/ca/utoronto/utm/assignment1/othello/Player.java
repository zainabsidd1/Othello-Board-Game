package ca.utoronto.utm.assignment1.othello;

public abstract class Player {
    protected Othello othello;
    protected char player;

    /**
     *
     * @param othello game being played
     * @param player P1 or P2
     */
    public Player(Othello othello, char player) {
        this.othello = othello;
        this.player = player;
    }

    /**
     * To be implemented by each subclass
     * @return the Move this player can play
     */
    public abstract Move getMove();


}
