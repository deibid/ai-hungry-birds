package ca.concordia.davidazar;

import java.util.Scanner;

public class Main {


    private static final String PLAYER_NAME_PATTERN = "\\w+";
	public static void main(String[] args) {

        GameSettings gameSettings = showMenu();
        GameManager mGameManager = new GameManager(gameSettings);
		mGameManager.startGame();

    }

	
	private static GameSettings showMenu(){


        System.out.print(String.format("\033[2J"));
		System.out.println("Welcome to Hungry Birds!");
        System.out.println("This version only supports Human VS Human gameplay\n\n");


        Scanner sc = new Scanner(System.in);
        String player1Name;
        String player2Name;

        do {
            System.out.println("What is the player1's name ? ([name]+enter)");
            player1Name = sc.nextLine();

            System.out.println("input: "+player1Name);


            if(!player1Name.matches(PLAYER_NAME_PATTERN))
                System.out.println("Can't be empty name!");

        }while(!player1Name.matches(PLAYER_NAME_PATTERN));


        do {
            System.out.println("What is the player2's name ? ([name]+enter)");
            player2Name = sc.nextLine();
            System.out.println("input: "+player2Name);

            if(!player2Name.matches(PLAYER_NAME_PATTERN))
                System.out.println("Can't be empty name!");

        }while(!player2Name.matches(PLAYER_NAME_PATTERN));


        System.out.println("Press Enter to continue");

        GameSettings settings = new GameSettings(GameManager.VERSUS_HUMAN,player1Name,player2Name);

		return settings;
	}
}