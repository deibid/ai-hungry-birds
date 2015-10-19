package ca.concordia.davidazar;

import java.util.Scanner;

/**
 * Main class.
 *
 * This class contains the main() method.
 * It is resposible of instantiating every necessary element and starting the game
 *
 *
 * @author David Azar Serur
 */
public class Main {

    /* Ensures that the player name contains at least one non-whitespace character */
    private static final String PLAYER_NAME_PATTERN = "\\w+";


	public static void main(String[] args) {

        GameSettings gameSettings = showMenu();
        GameManager mGameManager = new GameManager(gameSettings);
		mGameManager.startGame();

    }


    /**
     *
     * @return GameSettings object containing all of the metadata for the game such as
     * player names and type of game
     */
	private static GameSettings showMenu(){


        /* Clears window */
        System.out.print(String.format("\033[2J"));

		System.out.println("Welcome to Hungry Birds!");
        System.out.println("This version only supports Human VS Human gameplay\n\n");


        Scanner sc = new Scanner(System.in);
        String player1Name;
        String player2Name;

        /* Loop that ensures that a valid player name is given by the user for player 1*/
        do {
            System.out.println("What is the player1's name ? ([name]+enter)");
            player1Name = sc.nextLine();

            if(!player1Name.matches(PLAYER_NAME_PATTERN))
                System.out.println("Can't be empty name!");

        }while(!player1Name.matches(PLAYER_NAME_PATTERN));

        /* Loop that ensures that a valid player name is given by the user for player 2*/
        do {
            System.out.println("What is the player2's name ? ([name]+enter)");
            player2Name = sc.nextLine();

            if(!player2Name.matches(PLAYER_NAME_PATTERN))
                System.out.println("Can't be empty name!");

        }while(!player2Name.matches(PLAYER_NAME_PATTERN));


        System.out.println("Press Enter to continue");

        /* Creates the GameSettings object with the inputted values from the terminal */
        GameSettings settings = new GameSettings(GameManager.VERSUS_HUMAN,player1Name,player2Name);

		return settings;
	}
}