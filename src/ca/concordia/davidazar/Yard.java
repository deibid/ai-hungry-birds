package ca.concordia.davidazar;


/**
 *
 * This class, designed as a Singleton, contains, manages and operates all stuff regarding
 * the yard. It is exposed as a singleton so that it can be accessed from anywhere.
 * (i.e The AI needs to know the current state so it can generate new moves)
 *
 *
 * @author David Azar
 *
 */

public class Yard {

	/*
	 * 
	 * Flujo
	 * 
	 * 
	 * usuario pone coordenada
	 * se hace validacion
	 * se convierte la coordenada a [0-7][0-7]
	 * se actualizan los valores de birds y larva
	 * se normalizan las [0-7][0-7] para UI
	 * se refresca UI
	 *
	 */



    private static final char EMPTY_SPACE = ' ';
    private static final char LARVA = 'L';
    private static final char BIRD = 'B';
    private static final int YARD_HEIGHT = 8;
    private static final int YARD_WIDTH = 8;
    private static final int UI_GRID_HEIGHT = 10;
    private static final int UI_GRID_WIDTH = 19;


    private boolean mIsWon;
    private char[][] mUIGrid = new char[UI_GRID_HEIGHT][UI_GRID_WIDTH];
    private char[][] mActualPositions = new char[YARD_HEIGHT][YARD_WIDTH];
    private char[] mLetters = {'A','B','C','D','E','F','G','H'};


    private String mLarva;
    private String[] mBirds;



    private static Yard instance = new Yard();

    private Yard(){
//		generateActualPositions();
        generateReducedPositions();
        generateUIGrid();
        refreshUI();


        isMoveValid(new Move());
//		getNumericCoordinates("A8");
//		getNumericCoordinates("H1");
//		getNumericCoordinates("A6");


//		int[] coordinates = {0,5};
//		normalizeCoordinatesForUIGrid(coordinates);



    }


    public static Yard getInstance(){
        return instance;
    }


    public boolean isWon(){


        return mIsWon;
    }



    public String getLarva() {
        return mLarva;
    }

    public String[] getBirds() {
        return mBirds;
    }


    private void generateReducedPositions(){

        mLarva = "D2";

        mBirds = new String[4];
        mBirds[0] = "A1";
        mBirds[1] = "C1";
        mBirds[2] = "E1";
        mBirds[3] = "G1";


    }

    private void refreshUI(){


        System.out.println("-----------------------------");


        System.out.println(getNumericCoordinates(mLarva));
        System.out.println(normalizeCoordinatesForUIGrid(getNumericCoordinates(mLarva)));


        System.out.println("-----------------------------");


        int[] normalizedLarva = normalizeCoordinatesForUIGrid(getNumericCoordinates(mLarva));
//
//
        mUIGrid[normalizedLarva[0]][normalizedLarva[1]] = LARVA;

        int[] normalizedBird;

        for( int i = 0;i<mBirds.length; i ++){

            normalizedBird = normalizeCoordinatesForUIGrid(getNumericCoordinates(mBirds[i]));
            mUIGrid[normalizedBird[0]][normalizedBird[1]] = BIRD;

        }


        printUIGrid();




    }











    /*This method converts full coordinates (D3, etc) to numeric coordinates in the grid array ([3][2])*/
    private int[] getNumericCoordinates(String textCoordinates){

        char letter = textCoordinates.charAt(0);
        int number = Character.getNumericValue(textCoordinates.charAt(1));


        System.out.println("Letra - "+letter);
        System.out.println("Numero - "+number);

        int rowCoordinate = 8-number;
        int columnCoordinate = getLetterIndex(letter);


        int[] coordinates = new int[2];
        coordinates[0] = rowCoordinate;
        coordinates[1] = columnCoordinate;

        System.out.println("row - "+rowCoordinate);
        System.out.println("column - "+columnCoordinate);

        return coordinates;



    }

    private int[] normalizeCoordinatesForUIGrid(int[] coordinates){

        int[] normalizedCoordinates = {
                coordinates[0]+1,
                coordinates[1]*2+2
        };


        System.out.println("-----------------");
        System.out.println("ROW UI = "+coordinates[0]);
        System.out.println("COLUMN UI = "+coordinates[1]);

        System.out.println("ROW UI = "+normalizedCoordinates[0]);
        System.out.println("COLUMN UI = "+normalizedCoordinates[1]);

        return normalizedCoordinates;
    }

    private void generateUIGrid(){


        int letterIndex = 0;
        int numberIndex = -1;

        for (int i = 0;i<UI_GRID_HEIGHT;i++){
            for (int j = 0;j<UI_GRID_WIDTH;j++){

                mUIGrid[i][j] = EMPTY_SPACE;

                if(i == 0 || i == UI_GRID_HEIGHT-1){
                    if(j !=0 && j%2==0 && j!= UI_GRID_WIDTH-1){
                        mUIGrid[i][j] = mLetters[letterIndex];
                        letterIndex++;
                    }
                }
                else{
                    if(j == 0 || j == UI_GRID_WIDTH-1){
                        mUIGrid[i][j] = Character.forDigit(8-numberIndex,10);
                    }
                    else if(j%2!=0){
                        mUIGrid[i][j] = '|';
                    }
                }
            }
            letterIndex = 0;
            numberIndex++;
        }
        printUIGrid();

    }


    private void printUIGrid(){
        for(int i = 0 ; i<UI_GRID_HEIGHT; i++){
            for(int j = 0;j<UI_GRID_WIDTH; j++){
                System.out.print(mUIGrid[i][j]);
            }
            System.out.print('\n');
        }
    }


    private void generateActualPositions(){

        for(int i = 0;i<YARD_HEIGHT;i++){

            for(int j = 0;j<YARD_WIDTH;j++){
                mActualPositions[i][j] = EMPTY_SPACE;
                if( i== 6 && j==3) mActualPositions[i][j] = LARVA;
                if( i== 7 && j%2==0 ) mActualPositions[i][j] = BIRD;
            }
        }

        for(int i = 0;i<YARD_HEIGHT;i++){
            for(int j = 0;j<YARD_WIDTH;j++){

                System.out.print(mActualPositions[i][j]);

            }
            System.out.print('\n');
        }
    }

    public boolean isMoveValid(Move move){

        /**We will run the command through 5 periods of validations.
         *
         * 1 - format
         * 2 - wheather it isnt an empty space and if that selected user is allowed to move a specific character
         * 3 - if the selected move is valid for that character
         * 4 - is it out of bounds
         * 5 - if the game is won
         *
         */

		/* When AI issues an unvalid move, the human player automatically wins */
		/*So..
		 * 
		 * if( unvalid AI Move)
		 * 
		 * mIsWon = true;
		 * 
		 */

        String command = move.getCommand();
        if (!commandCompliesToFormat(command)){
            return false;
        }

        else{

            return true;
        }











    }


    private boolean commandCompliesToFormat(String command){



        String pattern = "[A-H][0-7]\\s[A-H][0-7]";

        String test = "H7 H7";
        System.out.println(test+ test.matches(pattern));

        test = "A3 D6";
        System.out.println(test+ test.matches(pattern));

        test = "G9    D2";
        System.out.println(test+ test.matches(pattern));

        test = "AAA3 D6";
        System.out.println(test+ test.matches(pattern));

        test = " A3 D6";
        System.out.println(test+ test.matches(pattern));

        test = ".AD6";
        System.out.println(test+ test.matches(pattern));

        test = "3J6";
        System.out.println(test+ test.matches(pattern));

        test = " ";
        System.out.println(test+ test.matches(pattern));

        test = "%";
        System.out.println(test+ test.matches(pattern));

        return true;

    }

    public void playMove(Move move){






    }




    /* HELPER METHODS */


    private boolean isBirdAtPosition(int[] coordinates){

        return (mUIGrid[coordinates[0]][coordinates[1]] == BIRD) ? true : false;

    }
    private boolean isLarvaAtPosition(int[] coordinates){

        return (mUIGrid[coordinates[0]][coordinates[1]] == LARVA) ? true : false;

    }
    private boolean isPositionEmpty(int[] coordinates){

        return (mUIGrid[coordinates[0]][coordinates[1]] == EMPTY_SPACE) ? true : false;

    }
    private int getLetterIndex(char c){

        for( int i = 0; i< mLetters.length; i++){
            if(mLetters[i] == c) return i;
        }

		/* Caused when a invalid letter is introduced */
        return -1;

    }




}
