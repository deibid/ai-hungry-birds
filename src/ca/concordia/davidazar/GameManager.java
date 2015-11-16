package ca.concordia.davidazar;


/**
 * This class acts as the manager of the game.
 * It creates the appropiate Player objects, validates a faulty input and carries through
 * the overall flow of the game
 *
 * @author David Azar Serur
 */
public class GameManager {


    /* Constant declarations */
	public static final int VERSUS_HUMAN = 0x00;
	public static final int VERSUS_AI = 0x01;

    /* Wheter its Human vs Human or Human VS A.I */
	private int mGameType;

    private int mAICharacter;


    /*Both Player entities */
	private Player mPlayer1;
	private Player mPlayer2;


    /* Player names inputed from the terminal */
    private String mPlayer1Name;
    private String mPlayer2Name;

    /* Default AI name */
    public static final String AI_NAME_PREFIX = "AI Force";


	/* The Yard singleton reference */
	private Yard mYard;
	
	/** Variable to distinguish the current turn. False is for player1's turn, false for player2's */
	private boolean mTurn = false;


    /**
     * Constructor taking a GameSettings object as a single parameter
     * @param settings metadata about the game such as player names and game type
     */
    public GameManager(GameSettings settings){

        mGameType = settings.getGameType();
        mPlayer1Name = settings.getPlayer1Name();
        mPlayer2Name = settings.getPlayer2Name();
        mAICharacter = settings.getAICharacter();

        mYard = Yard.getInstance();

        if (mGameType == VERSUS_HUMAN){
            System.out.println("Versus HUMAN generated");
            mPlayer1 = new HumanPlayer();
            mPlayer2 = new HumanPlayer();

            ((HumanPlayer)mPlayer1).setPlayerName(mPlayer1Name);
            ((HumanPlayer)mPlayer2).setPlayerName(mPlayer2Name);

        }

        //For the next deliverable..
        else{

            System.out.println("Versus AI generated");

            if(mAICharacter == GameSettings.AI_PLAYS_LARVA){

                mPlayer1 = new AIPlayer();
                mPlayer2 = new HumanPlayer();

                ((HumanPlayer)mPlayer2).setPlayerName(mPlayer1Name);
                ((AIPlayer)mPlayer1).setPlayerName(AI_NAME_PREFIX);
            }

            else{
                mPlayer1 = new HumanPlayer();
                mPlayer2 = new AIPlayer();


                ((HumanPlayer)mPlayer1).setPlayerName(mPlayer1Name);
                ((AIPlayer)mPlayer2).setPlayerName(AI_NAME_PREFIX);
            }



        }

    }


    /**
     * This method contains the main game loop..
     * Here, the turns switch, validations occur and manipulations to the grid are made
     */
    public void startGame(){
		
        /* Reference to the player who is actually moving */
		Player currentPlayer;


		boolean isMoveValid;
		boolean aiMistake = false;
		
		
		/*This is the game loop. It repeats until somebody wins or AI makes a mistake*/
		while(!mYard.isWon()){
			
			currentPlayer = (mTurn) ? mPlayer2:mPlayer1;

            Move currentMove;


			/*Retry Loop*/
			do{

                String playerName = getPlayerName(currentPlayer);
                String character = (mTurn)? "(BIRD)":"(LARVA)";
				System.out.println("On standby for player "+playerName+" "+character);

                /* Here, we get back the generated move, wheter its a human input or AI generated move */
				currentMove = currentPlayer.makeMove();

                currentMove.setTurn(mTurn);

                /*All the validations occur in this call */
				isMoveValid = mYard.isMoveValid(currentMove);

				currentMove.setIsValid(isMoveValid);


				/* Check weather the AI made a mistake and exits retry loop */
				if (currentPlayer instanceof AIPlayer && !isMoveValid){
					aiMistake = true;
					break;
				}
				
				if (!isMoveValid) System.out.println("Unvalid move. Please, try again");

                /*Repeat if a human made an invalid move */
				}while(!isMoveValid);

			
			/* Check weather the AI made a mistake and exits the game loop */
			if (aiMistake) break;


            /* Execute the play after all forms of validations */
			mYard.playMove(currentMove);

            /* Toggle turn*/
			mTurn = !mTurn;
			
		}

        /* When the game ends.. */
		displayFinalMessage(aiMistake);
		
	}


    /**
     *
     * This method shows the final message and indicates the winner
     * @param aiMistake flag indicating the AI made a mistake
     */
	private void displayFinalMessage(boolean aiMistake){
		
		String message = "";

        /*If the AI made a mistake */
		if (aiMistake)
			message = "Seems like the A.I messed up. Congratulations, you win!";


		else{
            Player winningPlayer = (mYard.didLarvaWin()) ? mPlayer1:mPlayer2;
			String winner = getPlayerName(winningPlayer);
			message = "Congratulations "+ winner+", you are the winner !";
		}
		
		System.out.println(message);
	}



    private String getPlayerName(Player player){

        if(player instanceof HumanPlayer) return ((HumanPlayer)player).getPlayerName();
        else return AI_NAME_PREFIX;
    }

	
}
