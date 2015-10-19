package ca.concordia.davidazar;


/**
 * Class that represents the A.I entity
 *
 * It implements the Player interface so that it can generate its move
 *
 */
public class AIPlayer implements Player{


    /* In case i end up implementatng the possibility of changing the A.I's name.. */
	private String mPlayerName;


	public String getPlayerName() {
		return mPlayerName;
	}

	public void setPlayerName(String mPlayerName) {
		this.mPlayerName = mPlayerName;
	}


    /**
     *
     * This is the method where the herusitic, etc are going to be implemented
     *
     * @return The generated Move object by the AI
     */
	@Override
	public Move makeMove(){


//		todo: Create an unbeatable AI

		return null;
		
	}

}
