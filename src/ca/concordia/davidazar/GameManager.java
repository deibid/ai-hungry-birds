package ca.concordia.davidazar;


import sun.util.resources.cldr.ms.CalendarData_ms_MY;

public class GameManager {

	
	public static final int VERSUS_HUMAN = 0x00;
	public static final int VERSUS_AI = 0x01;
	
	private int mGameType;
	
	private Player mPlayer1;
	private Player mPlayer2;
	
	//private AI mAI;


    private String mPlayer1Name = "Player1";
    private String mPlayer2Name = "Player2";

    public static final String AI_NAME_PREFIX = "AI Force";


	
	private Yard mYard;
	
	/** Variable to distinguish the current turn. False is for player1's turn, false for player2's */
	private boolean mTurn = false; 
	
	
	public GameManager(int type){
		
		mGameType = type;
		mYard = Yard.getInstance();
		
		if (mGameType == VERSUS_HUMAN){
			System.out.println("Versus HUMAN generated");	
			mPlayer1 = new HumanPlayer();
			mPlayer2 = new HumanPlayer();

            ((HumanPlayer)mPlayer1).setPlayerName(mPlayer1Name);
            ((HumanPlayer)mPlayer2).setPlayerName(mPlayer2Name);
				
		}
		
		//Para el proximo deliverable
		else{
		
			System.out.println("Versus AI generated");
			mPlayer1 = new HumanPlayer();
			mPlayer2 = new AIPlayer();

            ((HumanPlayer)mPlayer1).setPlayerName(mPlayer1Name);
            ((AIPlayer)mPlayer2).setPlayerName(AI_NAME_PREFIX);

			
		}
		
	}
	
	
	
	
	public void startGame(){
		
		
		Player currentPlayer;
//		String currentMove;
		boolean isMoveValid;
		boolean aiMistake = false;
		
		
		/*This is the game loop. It repeats until somebody wins or AI makes a mistake*/
		while(!mYard.isWon()){
			
			currentPlayer = (mTurn) ? mPlayer2:mPlayer1;


//            Move currentMove = new Move();
//            currentMove.setMovingPlayer(currentPlayer);
//            currentMove.setTurn(mTurn);

            Move currentMove;


			/*Retry Loop*/
			do{
				
				String playerName = (mTurn) ? "Player2":"Player1";
				System.out.println("On standby for "+playerName);

                mYard.printUIGrid();
				currentMove = currentPlayer.makeMove();

                currentMove.setTurn(mTurn);
				isMoveValid = mYard.isMoveValid(currentMove);
				currentMove.setIsValid(isMoveValid);


				/* Check weather the AI made a mistake and exits retry loop */
				if (currentPlayer instanceof AIPlayer && !isMoveValid){
					aiMistake = true;
					break;
				}
				
				if (!isMoveValid) System.out.println("Unvalid move. Please, try again");


				}while(!isMoveValid);
			
			
			
			/* Check weather the AI made a mistake and exits the game loop */
			if (aiMistake) break;


			mYard.playMove(currentMove);
				
			mTurn = !mTurn;
			
		}
		
		displayFinalMessage(aiMistake);
		
	}
	
	
	private void displayFinalMessage(boolean aiMistake){
		
		String message = "";
		
		if (aiMistake)
			message = "Seems like the A.I messed up. Congratulations, you win!";
			
		else{
			String winner = (!mTurn) ? "Player2":"Player1";
			message = "Congratulations to "+ winner+", you are the winner !";
		}
		
		System.out.println(message);
	}

    public String getPlayer1Name() {
        return mPlayer1Name;
    }

    public String getPlayer2Name() {
        return mPlayer2Name;
    }


    /**
	 * ciclo de game loop
	 * 
	 * 
	 * 1 - alguien ya gano?
	 * si- sal y muestra
	 * no-
	 * 
	 * 2- jugar turno correspondiente
	 * 
	 * 3 - el move es valido?
	 * si - sigue
	 * no - 
	 * 
	 * 4 - es humano ?
	 * 
	 * si - repite tiro
	 * no - pierde
	 * 
	 * 5 - mostrar cambios en tablero
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	
}
