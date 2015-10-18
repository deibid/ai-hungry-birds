package ca.concordia.davidazar;

/**
 * Created by David on 18/10/15.
 */
public class GameSettings {



    private int mGameType;
    private String mPlayer1Name;
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
