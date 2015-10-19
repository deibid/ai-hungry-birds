package ca.concordia.davidazar;

import java.util.Scanner;


/**
 *
 * This class represenets the Human Player entity.
 *
 * It contains the necesary methods for a human to input its move through the terminal,
 * implementing the Player interface
 *
 * @author David Azar Serur
 */
public class HumanPlayer implements Player {



    /* Player name */
	private String mPlayerName;


	public String getPlayerName() {
		return mPlayerName;
	}

	public void setPlayerName(String mPlayerName) {
		this.mPlayerName = mPlayerName;
	}


    /**
     * This method prompts the user for an input move
     *
     * @return Move object containing the inputted vsalues from the terminal
     */
	@Override
	public Move makeMove() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input your move: [A-H1-8] [A-H1-8]");
        String command = scanner.nextLine();

        Move move = new Move();
        move.setCommand(command);
        move.setMovingPlayer(this);

		return move;
	}

	
	
	
	
	
	
	
	
	
	
	
}
