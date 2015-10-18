package ca.concordia.davidazar;

import java.util.Scanner;

public class HumanPlayer implements Player {

	

	private String mPlayerName;


	public String getPlayerName() {
		return mPlayerName;
	}

	public void setPlayerName(String mPlayerName) {
		this.mPlayerName = mPlayerName;
	}




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
