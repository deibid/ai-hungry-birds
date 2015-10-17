package ca.concordia.davidazar;

public class Main {

		
	public static void main(String[] args) {

        int gameType = showMenu();
        GameManager mGameManager = new GameManager(gameType);
//		mGameManager.startGame();

    }

	
	private static int showMenu(){
		
		
		System.out.println("Welcome to Hungry Birds!");		
		
		
		
		return GameManager.VERSUS_HUMAN;
	}
}
