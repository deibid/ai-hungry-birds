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
    private static final String COMMAND_PATTERN = "[A-H][0-7]\\s[A-H][0-7]";


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

//
//        Move move = new Move();
//        move.setCommand("A3 D5");
//        isMoveValid(move);
//
//
//        Move move2 = new Move();
////
//        move2.setTurn(false);
//        move2.setFrom("D2");
//        move2.setTo("D3");
//        isMoveValidForCharacter(move2);
//
//        move2.setTurn(true);
//        move2.setFrom("E1");
//        move2.setTo("D3");
//        isMoveValidForCharacter(move2);
//
//        move2.setTurn(true);
//        move2.setFrom("F1");
//        move2.setTo("D3");
//        isMoveValidForCharacter(move2);
//
//        move2.setTurn(true);
//        move2.setFrom("D2");
//        move2.setTo("C3");
//        isMoveValidForCharacter(move2);
//
//        move2.setTurn(true);
//        move2.setFrom("G1");
//        move2.setTo("H2");
//        isMoveValidForCharacter(move2);
//

//        Move move2 = new Move();
//
//        move2.setTurn(false);
//        move2.setFrom("D2");
//        move2.setTo("D3");
//        isMoveValidForPlayer(move2);
//
//        move2.setTurn(false);
//        move2.setFrom("D1");
//        move2.setTo("D3");
//        isMoveValidForPlayer(move2);
//
//
//        move2.setTurn(true);
//        move2.setFrom("F2");
//        move2.setTo("D3");
//        isMoveValidForPlayer(move2);
//
//
//        move2.setTurn(true);
//        move2.setFrom("G1");
//        move2.setTo("D3");
//        isMoveValidForPlayer(move2);



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
        return (isWinningMoveForBird() || isWinningMoveForLarva());
    }


    public boolean didLarvaWin(){

        return isWinningMoveForLarva();

    }

    public boolean didBirdWin(){
        return isWinningMoveForBird();
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


        generateUIGrid();

//        System.out.println("-----------------------------");
//
//
//        System.out.println(getNumericCoordinates(mLarva));
//        System.out.println(normalizeCoordinatesForUIGrid(getNumericCoordinates(mLarva)));
//
//
//        System.out.println("-----------------------------");


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


//        System.out.println("Letra - "+letter);
//        System.out.println("Numero - "+number);

        int rowCoordinate = 8-number;
        int columnCoordinate = getLetterIndex(letter);


        int[] coordinates = new int[2];
        coordinates[0] = rowCoordinate;
        coordinates[1] = columnCoordinate;

//        System.out.println("row - "+rowCoordinate);
//        System.out.println("column - "+columnCoordinate);

        return coordinates;



    }

    private int[] normalizeCoordinatesForUIGrid(int[] coordinates){

        int[] normalizedCoordinates = {
                coordinates[0]+1,
                coordinates[1]*2+2
        };


//        System.out.println("-----------------");
//        System.out.println("ROW UI = "+coordinates[0]);
//        System.out.println("COLUMN UI = "+coordinates[1]);
//
//        System.out.println("ROW UI = "+normalizedCoordinates[0]);
//        System.out.println("COLUMN UI = "+normalizedCoordinates[1]);

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
//        printUIGrid();

    }


    public void printUIGrid(){



        clearTerminal();

        for(int i = 0 ; i<UI_GRID_HEIGHT; i++){
            for(int j = 0;j<UI_GRID_WIDTH; j++){
                System.out.print(mUIGrid[i][j]);
            }
            System.out.print('\n');
        }
    }

    private void clearTerminal(){
        System.out.print(String.format("\033[2J"));
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

        /**We will run the command through 3 periods of validations.
         *
         * 1 - format
         * 2 - wheather it isnt an empty space and if that selected user is allowed to move a specific character
         * 3 - if the selected move is valid for that character
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



        /* For readibilty purposes, i wrote the flow of the validations explicitly */
        /* First validation */
        if (!commandCompliesToFormat(command))
            return false;

        else{
            /*Second validation*/
            move = parseMove(move);
            if(!isMoveValidForPlayer(move)) return false;
            else{
                /*Third validation*/
                if(!isMoveValidForCharacter(move)){
                    return false;
                }
                else{
                    /* When every test was passed succesfully */
                    return true;
                }
            }
        }

    }


    /* This method evaluates if the selected move is valid for the character ( larva or bird )
    i.e - ensures that you dont try to move a bird backwards, etc
     */
    private boolean isMoveValidForCharacter(Move move){

        /*Analize the coordinates as the actual gro [1-8][A-H] .. (not zero index)*/
//        System.out.println("isMoveValidForCharacter\n\n\n");

        String from = move.getFrom();
        String to = move.getTo();


        int fromRow = Character.getNumericValue(from.charAt(1));
        int fromColumn = getLetterIndex(from.charAt(0))+1;

        int toRow = Character.getNumericValue(to.charAt(1));
        int toColumn = getLetterIndex(to.charAt(0))+1;

//
//        System.out.println("fromRow "+fromRow);
//        System.out.println("fromColumn "+fromColumn);
//        System.out.println("toRow "+toRow);
//        System.out.println("toColumn "+toColumn);
//
//

//        return true;

        /* Rules for the larva */
        if(isLarvaAtPosition(from)){

            int rowDifference = fromRow - toRow;
            int columnDifference = fromColumn - toColumn;
            /* moving one column and one row diagonally in any directions*/
            if( Math.abs(rowDifference)==1 && Math.abs(columnDifference)==1){
//                System.out.println("Valido para Larva");
                return true;
            }
            else {
//                System.out.println("Invalido para Larva");
                return false;
            }

        }

        /* Rules for the birds */
        else if (isBirdAtPosition(from)){

            int rowDifference = fromRow - toRow;
            int columnDifference = fromColumn - toColumn;
            /* moving one column and one row diagonally going forward */
            if( rowDifference == -1 && Math.abs(columnDifference)==1){
//                System.out.println("Valido para Bird");
                return true;
            }
            else{
//                System.out.println("Invalido para Bird");
                return false;
            }


        }

//        System.out.println("Tratando de mover espacio vacio");
        return false;


    }


    /* This method evaluates if a specific move is valid for the current player's turn
    i.e - player1 can only move the Larva, and not any of the birds
     */
    private boolean isMoveValidForPlayer(Move move){


//        System.out.println("Is move valid for player");

        Player movingPlayer = move.getMovingPlayer();

        String playerName = " ";

        if(movingPlayer instanceof HumanPlayer) playerName = ((HumanPlayer)movingPlayer).getPlayerName();
        else playerName = GameManager.AI_NAME_PREFIX;

        String from = move.getFrom();
        String to = move.getTo();

        if(move.isPlayer1Turn()) {


//            System.out.println("Player 1 turn");
//            System.out.println("From "+from);
//            System.out.println("Valid? "+isLarvaAtPosition(from));

            return (isLarvaAtPosition(from));


        }
        else{

//            System.out.println("Player 2 turn");
//            System.out.println("From "+from);
//            System.out.println("Valid? "+isBirdAtPosition(from));

            return (isBirdAtPosition(from));
        }


    }


    /*This method parses a valid command into the two coordinates. From and To*/
    private Move parseMove(Move move){


//        System.out.println("Parsing move");

        String command = move.getCommand();


//        System.out.println("Command "+command);

        /* Split the string at the space character */
        String[] parts = command.split(" ");

        String from = parts[0];
        String to = parts[1];

//        System.out.println("From "+from);
//        System.out.println("To "+to);


        move.setFrom(from);
        move.setTo(to);

        return move;

    }


    /* This method ensures  that the input is presented in the form we want it.
    *
    * Uppercase letter from A to H followed by a number from 1 to 8
    * space
    * Uppercase letter from A to H followed by a number from 1 to 8
    *
    * This also ensures that the move is inside the bounds of the field
    * */

    private boolean commandCompliesToFormat(String command){

 //        String test = "H7 H7";
//        System.out.println(test+ test.matches(COMMAND_PATTERN));
//
//        test = "A3 D6";
//        System.out.println(test+ test.matches(COMMAND_PATTERN));
//
//        test = "G9    D2";
//        System.out.println(test+ test.matches(COMMAND_PATTERN));
//
//        test = "AAA3 D6";
//        System.out.println(test+ test.matches(COMMAND_PATTERN));
//
//        test = " A3 D6";
//        System.out.println(test+ test.matches(COMMAND_PATTERN));
//
//        test = ".AD6";
//        System.out.println(test+ test.matches(COMMAND_PATTERN));
//
//        test = "3J6";
//        System.out.println(test+ test.matches(COMMAND_PATTERN));
//
//        test = " ";
//        System.out.println(test+ test.matches(COMMAND_PATTERN));
//
//        test = "%";
//        System.out.println(test+ test.matches(COMMAND_PATTERN));
//
//
//
//        for ( int i = 0 ; i<8;i++){
//            for ( int j = 0; j<8;j++){
//                test = String.valueOf(mLetters[i]+String.valueOf(j) + " "+ mLetters[7-i]+String.valueOf(7-j));
//                System.out.println(test+ test.matches(COMMAND_PATTERN));
//
//            }
//
//
//        }

        return command.matches(COMMAND_PATTERN);

    }

    public void playMove(Move move){



        /* Wheather we move the larva or the birds */
        if (move.isPlayer1Turn()){
            mLarva = move.getTo();
        }

        else{

            for (int i=0;i<mBirds.length;i++){
                if(mBirds[i].equals(move.getFrom())){
                    mBirds[i] = move.getTo();
                }
            }
        }

        refreshUI();

    }


    /* Checks weather a bird is at the same spot as the larva
      i.e it eats it
       */

    private boolean isWinningMoveForBird(){


        for (int i = 0; i < mBirds.length ; i++){
            if (mBirds[i].equals(mLarva)) return true;
        }

        return false;

    }




    /*Checks weather the larva is at the fence ( row 1 ) */
    private boolean isWinningMoveForLarva(){



        for(String s: mBirds){
            if(s.equals(mLarva)) return false;
        }

        int larvaRow = Character.getNumericValue(mLarva.charAt(1));
        return larvaRow == 1;

    }
    /* HELPER METHODS */


    private boolean isPositionEmpty(String position){

        if( position.equals(mLarva)) return false;

        else{

            for(int i = 0 ; i<mBirds.length; i++){
                if (position.equals(mBirds[i])) return false;
            }
        }

        return true;


    }


    private boolean isLarvaAtPosition(String position){

        return position.equals(mLarva);


    }


    private boolean isBirdAtPosition(String position){


        boolean isBird = false;

        for(int i = 0; i<mBirds.length; i++){

            if( position.equals(mBirds[i])) {
                isBird = true;
                break;
            }
        }
        return isBird;

    }


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
