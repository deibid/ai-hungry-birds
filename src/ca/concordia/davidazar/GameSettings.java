package ca.concordia.davidazar;

/**
 *
 * This class behaves as a Java Bean containing all relevant information about the game
 * @author David Azar Serur
 */
public class GameSettings {

    /* Human VS Human or Human vs AI */
    private int mGameType;

    /* First player's name */
    private String mPlayer1Name;

    /* Second player's name */
    private String mPlayer2Name;


    public GameSettings(int mGameType, String mPlayer1Name, String mPlayer2Name) {
        this.mGameType = mGameType;
        this.mPlayer1Name = mPlayer1Name;
        this.mPlayer2Name = mPlayer2Name;
    }


    public int getGameType() {
        return mGameType;
    }

    public String getPlayer1Name() {
        return mPlayer1Name;
    }

    public String getPlayer2Name() {
        return mPlayer2Name;
    }
}
