package ca.concordia.davidazar;

/**
 * This class acts as a Java Bean, containing all necessary data regarding a
 * given move such as the player that made it, wheather its valid or not and
 * the actual move
 */
public class Move {




    private Player mMovingPlayer;

    private String mCommand;
    private String mFrom;
    private String mTo;
    private boolean mIsValid;

    /* false for player1, true for player2 */
    private boolean mTurn;


    private boolean isTurn() {
        return mTurn;
    }

    public void setTurn(boolean mTurn) {
        this.mTurn = mTurn;
    }


    public boolean isPlayer2Turn(){

        return isTurn();
    }

    public boolean isPlayer1Turn(){

        return !isTurn();

    }

    public Player getMovingPlayer() {
        return mMovingPlayer;
    }

    public void setMovingPlayer(Player mMovingPlayer) {
        this.mMovingPlayer = mMovingPlayer;
    }

    public String getCommand() {
        return mCommand;
    }

    public void setCommand(String mCommand) {
        this.mCommand = mCommand;
    }

    public String getFrom() {
        return mFrom;
    }

    public void setFrom(String mFrom) {
        this.mFrom = mFrom;
    }

    public String getTo() {
        return mTo;
    }

    public void setTo(String mTo) {
        this.mTo = mTo;
    }

    public boolean isIsValid() {
        return mIsValid;
    }

    public void setIsValid(boolean mIsValid) {
        this.mIsValid = mIsValid;
    }
}
