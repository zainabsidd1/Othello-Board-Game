package ca.utoronto.utm.assignment1.othello;


/**
 * Abstract class
 */
public abstract class Player {
    protected Othello othello;
    protected char player;

    /**
     * Create a new player for the current game
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
