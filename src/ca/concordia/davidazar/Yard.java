package ca.concordia.davidazar;


import java.util.ArrayList;

/**
 * This class, designed as a Singleton, contains, manages and operates all stuff regarding
 * the yard. It is exposed as a singleton so that it can be accessed from anywhere and only
 * exists as a single entity
 * (i.e The AI needs to know the current state so it can generate new moves)
 *
 * @author David Azar Serur
 */

public class Yard {

    /* Constant declarations */
    private static final char EMPTY_SPACE = ' ';
    private static final char LARVA = 'L';
    private static final char BIRD = 'B';
    private static final int YARD_HEIGHT = 8;
    private static final int YARD_WIDTH = 8;
    private static final int UI_GRID_HEIGHT = 10;
    private static final int UI_GRID_WIDTH = 19;
    private static final String COMMAND_PATTERN = "[A-H][0-7]\\s[A-H][0-7]";


    private static final int TO_UPPER_RIGHT_OFFSET = 11;
    private static final int TO_LOWER_RIGHT_OFFSET = -9;
    private static final int TO_LOWER_LEFT_OFFSET = -11;
    private static final int TO_UPPER_LEFT_OFFSET = 9;

    private static final int MAX_POSSIBLE_LARVA_MOVES = 4;

    /*UI matrix */
    private char[][] mUIGrid = new char[UI_GRID_HEIGHT][UI_GRID_WIDTH];

    /* List of allowed characters */
    private char[] mLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};


    /* Always contains the current position for the larva */
    private String mLarva;

    /* Always contains the current position for the birds */
    private String[] mBirds;


    /**
     * Variable to distinguish the current turn. False is for player1's turn, false for player2's
     */
    private boolean mTurn = false;


    private static Yard instance = new Yard();

    private Yard() {


//        System.out.println(getNumericCoordinates("D3"));
//        System.out.println(getNumericCoordinates("A1"));
//        System.out.println(getNumericCoordinates("H8"));

        generateInitialPositions();
        generateUIGrid();
        refreshUI();
    }


    public static Yard getInstance() {
        return instance;
    }


    /* Checks whether the game is finished */
    public boolean isWon() {
        return (isWinningMoveForBird() || isWinningMoveForLarva());
    }


    public boolean didLarvaWin() {
        return isWinningMoveForLarva();
    }

    public boolean didBirdWin() {
        return isWinningMoveForBird();
    }

    private void generateInitialPositions() {
        mLarva = "D2";
        mBirds = new String[4];
        mBirds[0] = "A1";
        mBirds[1] = "C1";
        mBirds[2] = "E1";
        mBirds[3] = "G1";

//        mLarva = "C3";
//        mBirds = new String[4];
//        mBirds[0] = "B4";
//        mBirds[1] = "D4";
//        mBirds[2] = "D2";
//        mBirds[3] = "B2";

    }

    /* Updates the UI Grid to show the modified positions for the larva and birds */
    private void refreshUI() {


        /* Restarts the grid */
        generateUIGrid();

        int[] normalizedLarva = normalizeCoordinatesForUIGrid(getNumericCoordinates(mLarva));
        mUIGrid[normalizedLarva[0]][normalizedLarva[1]] = LARVA;

        int[] normalizedBird;

        /* Fills it */
        for (int i = 0; i < mBirds.length; i++) {

            normalizedBird = normalizeCoordinatesForUIGrid(getNumericCoordinates(mBirds[i]));
            mUIGrid[normalizedBird[0]][normalizedBird[1]] = BIRD;

        }

        printUIGrid();

    }


    /*This method converts full coordinates (D3, etc) to numeric coordinates in the grid array ([3][2])*/
    public int[] getNumericCoordinates(String textCoordinates) {

        char letter = textCoordinates.charAt(0);
        int number = Character.getNumericValue(textCoordinates.charAt(1));


        int rowCoordinate = 8 - number;
        int columnCoordinate = getLetterIndex(letter);


        int[] coordinates = new int[2];
        coordinates[0] = rowCoordinate;
        coordinates[1] = columnCoordinate;

//        System.out.println("----"+coordinates[0]+" "+coordinates[1]);

        return coordinates;


    }

    /* This method returns the actual array indexes needed to display the Larva and Birds properly
     * The UI grid contains several margins and extra characters, this method takes all that into account
     */
    private int[] normalizeCoordinatesForUIGrid(int[] coordinates) {

        int[] normalizedCoordinates = {
                coordinates[0] + 1,
                coordinates[1] * 2 + 2
        };

        return normalizedCoordinates;
    }


    /* This method generates a fresh UIGrid with the letters and numbers at the appropiate positions */
    private void generateUIGrid() {


        int letterIndex = 0;
        int numberIndex = -1;

        for (int i = 0; i < UI_GRID_HEIGHT; i++) {
            for (int j = 0; j < UI_GRID_WIDTH; j++) {

                mUIGrid[i][j] = EMPTY_SPACE;

                if (i == 0 || i == UI_GRID_HEIGHT - 1) {
                    if (j != 0 && j % 2 == 0 && j != UI_GRID_WIDTH - 1) {
                        mUIGrid[i][j] = mLetters[letterIndex];
                        letterIndex++;
                    }
                } else {
                    if (j == 0 || j == UI_GRID_WIDTH - 1) {
                        mUIGrid[i][j] = Character.forDigit(8 - numberIndex, 10);
                    } else if (j % 2 != 0) {
                        mUIGrid[i][j] = '|';
                    }
                }
            }
            letterIndex = 0;
            numberIndex++;
        }

    }


    /* Displays the UIGrid on the terminal window */
    public void printUIGrid() {


        clearTerminal();

        for (int i = 0; i < UI_GRID_HEIGHT; i++) {
            for (int j = 0; j < UI_GRID_WIDTH; j++) {
                System.out.print(mUIGrid[i][j]);
            }
            System.out.print('\n');
        }
    }


    /**
     * We will run the command through 3 periods of validations.
     * <p>
     * 1 - format
     * 2 - whether it isn't an empty space and if that selected user is allowed to move a specific character
     * 3 - if the selected move is valid for that character
     */
    public boolean isMoveValid(Move move) {

        String command = move.getCommand();

        /* For readibilty purposes, i wrote the flow of the validations explicitly */
        /* First validation */
        if (!commandCompliesToFormat(command))
            return false;

        else {
            /*Second validation*/
            move = parseMove(move);
            if (!isMoveValidForPlayer(move)) return false;
            else {
                /*Third validation*/
                if (!isMoveValidForCharacter(move)) {
                    return false;
                } else {

                    if (!isPositionEmpty(move.getTo())) {
                        return false;
                    } else {
                    /* When every test was passed succesfully */
                        mTurn = move.isPlayer2Turn();
                        return true;
                    }
                }
            }
        }

    }


    /* This method evaluates if the selected move is valid for the character ( larva or bird )
    i.e - ensures that you don't try to move a bird backwards, etc
     */
    private boolean isMoveValidForCharacter(Move move) {

        /*Analyzes the coordinates as the actual  grid [1-8][A-H] .. (not zero index)*/

        String from = move.getFrom();
        String to = move.getTo();


        int fromRow = Character.getNumericValue(from.charAt(1));
        int fromColumn = getLetterIndex(from.charAt(0)) + 1;

        int toRow = Character.getNumericValue(to.charAt(1));
        int toColumn = getLetterIndex(to.charAt(0)) + 1;



        /* Rules for the larva */
        if (isLarvaAtPosition(from)) {

            int rowDifference = fromRow - toRow;
            int columnDifference = fromColumn - toColumn;

            /* moving one column and one row diagonally in any directions*/
            if (Math.abs(rowDifference) == 1 && Math.abs(columnDifference) == 1) {
                return true;
            } else {
                return false;
            }

        }

        /* Rules for the birds */
        else if (isBirdAtPosition(from)) {

            int rowDifference = fromRow - toRow;
            int columnDifference = fromColumn - toColumn;

            /* moving one column and one row diagonally going forward */
            if (rowDifference == -1 && Math.abs(columnDifference) == 1) {
                return true;
            } else {
                return false;
            }


        }

        //When trying to move an empty parcel..
        return false;


    }


    /* This method evaluates if a specific move is valid for the current player's turn
    i.e - player1 can only move the Larva, and not any of the birds
     */
    private boolean isMoveValidForPlayer(Move move) {

        String from = move.getFrom();

        if (move.isPlayer1Turn()) {
            return (isLarvaAtPosition(from));
        } else {
            return (isBirdAtPosition(from));
        }


    }


    /*This method parses a valid command into the two coordinates. From and To*/
    private Move parseMove(Move move) {

        String command = move.getCommand();
        /* Split the string at the space character */
        String[] parts = command.split(" ");

        String from = parts[0];
        String to = parts[1];

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
    private boolean commandCompliesToFormat(String command) {
        return command.matches(COMMAND_PATTERN);
    }

    /*This method executes the change in the characters position */
    public void playMove(Move move) {

        /* Wheather we move the larva or the birds */
        if (move.isPlayer1Turn()) {
            mLarva = move.getTo();
        } else {

            /*Find the actual bird selected to move */
            for (int i = 0; i < mBirds.length; i++) {
                if (mBirds[i].equals(move.getFrom())) {
                    mBirds[i] = move.getTo();
                }
            }
        }

        mTurn = !mTurn;

        refreshUI();

    }


    /* This method checks whether a bird is at the same spot as the larva
      i.e it eats it
       */

    private boolean isWinningMoveForBird() {

//
//
//
//
//        for (int i = 0; i < mBirds.length; i++) {
//            if (mBirds[i].equals(mLarva)) return true;
//        }
//
//        return false;


        int numericLarvaCoordinate = Utils.getNumericCoordinate(mLarva);
        int[] numericBirdCoordinates = Utils.getNumericCoordinate(mBirds);


        int hits = 0;
        for (int i = 0; i < MAX_POSSIBLE_LARVA_MOVES; i++) {

            int tempCoordinate = 0;

            switch (i) {

                case 0:
                    tempCoordinate = numericLarvaCoordinate + TO_UPPER_RIGHT_OFFSET;
                    break;
                case 1:
                    tempCoordinate = numericLarvaCoordinate + TO_LOWER_RIGHT_OFFSET;
                    break;
                case 2:
                    tempCoordinate = numericLarvaCoordinate + TO_LOWER_LEFT_OFFSET;
                    break;
                case 3:
                    tempCoordinate = numericLarvaCoordinate + TO_UPPER_LEFT_OFFSET;
                    break;
            }

            for(int j = 0;j < numericBirdCoordinates.length; j++){
                if(tempCoordinate == numericBirdCoordinates[j]) {
                    hits++;
                }
            }
        }
        return hits==4;
    }


    /*This method checks weather the larva is at the fence ( row 1 ) */
    private boolean isWinningMoveForLarva() {

        for (String s : mBirds) {
            if (s.equals(mLarva)) return false;
        }

        int larvaRow = Character.getNumericValue(mLarva.charAt(1));
        return larvaRow == 1;

    }



    /* HELPER METHODS */


    private void clearTerminal() {
        System.out.print(String.format("\033[2J"));
    }

    /*This method checks if there are any empty spaces at the inputted position*/
    private boolean isPositionEmpty(String position) {

        if (position.equals(mLarva)) return false;
        else {

            for (int i = 0; i < mBirds.length; i++) {
                if (position.equals(mBirds[i])) return false;
            }
        }
        return true;
    }

    /*This method checks if the larva is at the inputted position*/
    private boolean isLarvaAtPosition(String position) {
        return position.equals(mLarva);
    }


    /*This method checks if there are any birds at the inputted position*/
    private boolean isBirdAtPosition(String position) {

        boolean isBird = false;
        for (int i = 0; i < mBirds.length; i++) {

            if (position.equals(mBirds[i])) {
                isBird = true;
                break;
            }
        }
        return isBird;
    }

    /*This method returns the alphabetic index of a character in the allowed list (A-H)*/
    public int getLetterIndex(char c) {

        for (int i = 0; i < mLetters.length; i++) {
            if (mLetters[i] == c) return i;
        }

		/* Caused when a invalid letter is introduced */
        return -1;

    }


    /**
     * Method called by AI.
     * Delivers a current overview of the grid in a State object
     *
     * @return Root State representing the current positions
     */
    public State getCurrentState() {

        State state = new State();

        state.setLarva(mLarva);
        state.setBirds(mBirds);
        state.setIsLarvaNode(!mTurn);

        return state;

    }


}
