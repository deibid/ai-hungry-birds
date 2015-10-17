package ca.concordia.davidazar;


public class GameManager {

	
	public static final int VERSUS_HUMAN = 0x00;
	public static final int VERSUS_AI = 0x01;
	
	private int mGameType;
	
	private Player mPlayer1;
	private Player mPlayer2;
	
	//private AI mAI;
	
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
				
		}
		
		//Para el proximo deliverable
		else{
		
			System.out.println("Versus AI generated");
			mPlayer1 = new HumanPlayer();
			mPlayer2 = new AIPlayer();
			
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


            Move currentMove = new Move();

            currentMove.setMovingPlayer(currentPlayer);

			/*Retry Loop*/
			do{
				
				String playerName = (mTurn) ? "Player2":"Player1";
				System.out.println("On standby for "+playerName);
			
				currentMove = currentPlayer.makeMove();


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
			String winner = (mTurn) ? "Player2":"Player1";
			message = "Congratulations to "+ winner+", you are the winner !";
		}
		
		System.out.println(message);
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
