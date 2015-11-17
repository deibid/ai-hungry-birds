package ca.concordia.davidazar;


import java.util.ArrayList;

/**
 * Class that represents the A.I entity
 * <p>
 * It implements the Player interface so that it can generate its move
 */
public class AIPlayer implements Player {


    /* In case i end up implementatng the possibility of changing the A.I's name.. */
    private String mPlayerName;


    private static Yard mYard = Yard.getInstance();


    /**
     * Depth of the tree
     */
    private static final int MAX_LEVELS = 3;

    private static final int MAX_POSSIBLE_LARVA_MOVES = 4;
    private static final int MAX_POSSIBLE_BIRD_MOVES = 8;
    private static final int MAX_POSSIBLE_MOVES_PER_BIRD = 2;

    private static final int TO_UPPER_RIGHT_OFFSET = 11;
    private static final int TO_LOWER_RIGHT_OFFSET = -9;
    private static final int TO_LOWER_LEFT_OFFSET = -11;
    private static final int TO_UPPER_LEFT_OFFSET = 9;

    /**
     * For validation purposes, all spots in grid go from 11 to 88
     */
    private static final int BIGGEST_POSITION_POSSIBLE = 88;
    private static final int SMALLEST_POSITION_POSSIBLE = 11;


    public String getPlayerName() {
        return mPlayerName;
    }

    public void setPlayerName(String mPlayerName) {
        this.mPlayerName = mPlayerName;
    }


    /**
     * This is the method where the herusitic, etc are going to be implemented
     *
     * @return The generated Move object by the AI
     */
    @Override
    public Move makeMove() {

//        System.out.println("ESTOY EN AI MAKEMOVE");

        State root = generateStateSpace();
        int rootMinMaxValue = evaluateMinimax(root, 1);

        System.out.println("Heuristic Value for root node: " + rootMinMaxValue);

        root.setGivenHeuristicValue(rootMinMaxValue);

        Move move = generateMove(root);

        return move;

    }

    /**
     * Method that calls gerenateChildren() and triggers the creation of the state-space
     *
     * @return the root node
     */
    private State generateStateSpace() {
        State root = mYard.getCurrentState();

//        System.out.println("Root isLarvaNode: "+root.isIsLarvaNode());


        root = generateChildren(root, 1);
//        System.out.println("termine state space");
        return root;
    }


    /**
     * This method recursively traverses the state-space and adds the values to the nodes
     *
     * @param state state to evaluate
     * @param level depth in state-space
     * @return heuristic evaluated from childs
     */
    private static int evaluateMinimax(State state, int level) {

//        System.out.println("\n\nEvaluateMinimax2");

        int result = 0;
        int heuristicValueFromChilds = 0;

        /** When reached a leaf node */
        if (level == MAX_LEVELS) {
//            System.out.println("Soy Leaf.. State: " + state);

            int leafHeuristic = evaluateHeuristic(state);
            System.out.println("Leaf Heuristic = " + leafHeuristic);
            return leafHeuristic;

        } else {
//            System.out.println("No soy Leaf.. State: " + state);

            /**When it isnt a leaf node*/


            /** For loop that iterates on all child state for a specific state */
            for (int i = 0; i < state.getChildCount(); i++) {
                /** Recursive call to a child state */
                result = evaluateMinimax(state.getChildState(i), level + 1);
                System.out.println("\nNO leaf, FROM CHILD: " + i + " RESULT: " + result);

                /** First child*/
                if (i == 0) heuristicValueFromChilds = result;
                else {
                     /* if MAX */
                    if (state.isIsLarvaNode()) {
                        /** get the biggest number from childs when it is a max node*/
                        if (result >= heuristicValueFromChilds) {
                            heuristicValueFromChilds = result;
                        }
                    }
                     /* if MIN */
                    else {
                        /** get the smallest number from childs when it is a min node*/
                        if (result <= heuristicValueFromChilds) {
                            heuristicValueFromChilds = result;
                        }
                    }

                }

            }


        }

        /** set the appropriate value from the evaluated childs to this parent state */
        state.setGivenHeuristicValue(heuristicValueFromChilds);
        System.out.println("\n------result Heuristic From children = " + heuristicValueFromChilds);

        /** This return statement is directly returned to the initial caller of this recursive method */
        return state.getGivenHeuristicValue();


    }

    /**
     * This method generates a Move object from the State we want to move to
     *
     * @param root state to get child from
     * @return move corresponding a playable move on the grid
     */
    private Move generateMove(State root) {

        boolean isMax = root.isIsLarvaNode();

//        System.out.println("en generateMove, isMax: "+isMax);

        State stateToPlay = root.getMinMaxChild(isMax);
        Move move = convertStateIntoMove(stateToPlay);


        return move;


    }

    /**
     * Method that takes the State the AI wants to move to and converts it to a Move object
     *
     * @param nextState
     * @return playable Move object
     */
    private Move convertStateIntoMove(State nextState) {

        Move move = new Move();


        State currentState = mYard.getCurrentState();
        String command = getCommandStringFromState(currentState, nextState);

        move.setCommand(command);
        move.setMovingPlayer(this);


        return move;
    }


    /**
     * Gets the state AI wants to move to and compares it to the current state, so it can return a command
     * that is going to be executed on the actual grid
     *
     * @param from state AI is currently in
     * @param to   state AI wants to move to
     * @return command that gets executed into the grid
     */
    private String getCommandStringFromState(State from, State to) {


        /** This means the larva was moved*/
        if (!from.getLarva().equals(to.getLarva())) {

            String command = from.getLarva() + " " + to.getLarva();
//            System.out.println("Command devueto. Movi larva: "+command);
            return command;

        } else {

            for (int i = 0; i < from.getBirds().length; i++) {

                if (!from.getBirds()[i].equals(to.getBirds()[i])) {
                    String command = from.getBirds()[i] + " " + to.getBirds()[i];
//                    System.out.println("Command devueto. Movi birds: "+command);
                    return command;

                }


            }


        }


        System.out.println("Algo salio mal generando command");
        return null;
    }


    private static int evaluateHeuristic(State state) {
        int heuristicValue = state.getEvaluatedHeuristicValue();
//        System.out.println("AIPlayer.. State: " + state);
//        System.out.println("AIPlayer.. Evaluate heuristic -- " + heuristicValue);

        return heuristicValue;
    }


    /**
     * Recursive method that generates a state-space for any given root node
     *
     * @param state state to generate kids to
     * @param level depth of the current call
     * @return the root node
     */
    private static State generateChildren(State state, int level) {

//        System.out.println("Entrando a generateChildren");

        if (level >= MAX_LEVELS) return null;

        State[] children;
        if (state.isIsLarvaNode()) {
            children = getLarvaChildNodes(state);
        } else {
            children = getBirdChildNodes(state);
        }

        if (children == null) return null;
        state.setChildStates(children);

        for (int j = 0; j < state.getChildCount(); j++) {
            generateChildren(state.getChildState(j), level + 1);
        }

        return state;
    }


    /**
     * This method generates all possible child states for a larva
     *
     * @param state the current state to analyse
     * @return array of all possible states
     */
    private static State[] getLarvaChildNodes(State state) {

        String larva = state.getLarva();
        String[] birds = state.getBirds();
//        System.out.println("getLarvaChildNodes larva: " + larva);

        int numericLarvaCoordinate = getNumericCoordinate(larva);
        int[] numericBirdCoordinates = getNumericCoordinate(birds);

        ArrayList<Integer> validCoordinates = new ArrayList<>();

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

//            System.out.println("tempCoordinate : " + tempCoordinate);

            if (isCoordinateValid(tempCoordinate)) {
                if (isSpaceAvailable(state, tempCoordinate)) {
                    validCoordinates.add(tempCoordinate);
//                    System.out.println("Coordenada fue valida");
                } else {
//                    System.out.println("Coordenada NO valida");
                }
            } else {
//                System.out.println("Coordenada NO valida");
            }

        }


        int length = validCoordinates.size();
//        System.out.println("Valid coordinates size: " + length);
        State[] childStates = new State[length];

        for (int i = 0; i < length; i++) {

            State newState = new State();
            newState.setBirds(state.getBirds());
            String larvaCoordinate = getStringCoordinates(validCoordinates.get(i));
            newState.setLarva(larvaCoordinate);
            newState.setIsLarvaNode(false);
            childStates[i] = newState;


        }


        return childStates;
    }


    /**
     * This method generates all possible child states for the birds
     *
     * @param state the current state to analyse
     * @return array of all possible states
     */
    private static State[] getBirdChildNodes(State state) {


        int[] numericBirdCoordinates = getNumericCoordinate(state.getBirds());


//        System.out.println("getBirdChildNodes");


        int[][] validCoordinates = new int[8][2];
        int amountOfValidCoordinates = 0;

        for (int i = 0; i < MAX_POSSIBLE_MOVES_PER_BIRD; i++) {

            int tempCoordinate = 0;
//            switch (i) {
//
//                case 0:
//
//                    tempCoordinate = 0;
//                    for (int j = 0; j < state.getBirds().length; j++) {
//
//                        int bird = numericBirdCoordinates[j];
//
//                        tempCoordinate = bird + TO_UPPER_RIGHT_OFFSET;
//
//                        if (isCoordinateValid(tempCoordinate)) {
//                            if (isSpaceAvailable(state, tempCoordinate)) {
//                                validCoordinates[amountOfValidCoordinates][0] = bird;
//                                validCoordinates[amountOfValidCoordinates][1] = tempCoordinate;
//                                amountOfValidCoordinates++;
//                            }
//                        }
//                    }
//
//                    break;
//                case 1:
//
//                    tempCoordinate = 0;
//                    for (int j = 0; j < state.getBirds().length; j++) {
//
//                        int bird = numericBirdCoordinates[j];
//
//                        tempCoordinate = bird + TO_UPPER_LEFT_OFFSET;
//
//                        if (isCoordinateValid(tempCoordinate)) {
//                            if (isSpaceAvailable(state, tempCoordinate)) {
//                                validCoordinates[amountOfValidCoordinates][0] = bird;
//                                validCoordinates[amountOfValidCoordinates][1] = tempCoordinate;
//                                amountOfValidCoordinates++;
//                            }
//                        }
//                    }
//                    break;


            int offset = 0;
            if (i == 0) offset = TO_UPPER_RIGHT_OFFSET;
            if (i == 1) offset = TO_UPPER_LEFT_OFFSET;
            tempCoordinate = 0;
            for (int j = 0; j < state.getBirds().length; j++) {

                int bird = numericBirdCoordinates[j];

                tempCoordinate = bird + offset;

                if (isCoordinateValid(tempCoordinate)) {
                    if (isSpaceAvailable(state, tempCoordinate)) {
                        validCoordinates[amountOfValidCoordinates][0] = bird;
                        validCoordinates[amountOfValidCoordinates][1] = tempCoordinate;
                        amountOfValidCoordinates++;
                    }
                }
            }


        }


//        System.out.println("Antes de trimear");
        validCoordinates = trimBidimensionalArray(validCoordinates);

        State[] childStates = new State[validCoordinates.length];
        for (int k = 0; k < childStates.length; k++) {

            /** This line avoids the insertion of a repetetive state */
            if (validCoordinates[k][0] != 0 && validCoordinates[k][1] != 0) {
                State newState = new State();
                newState.setLarva(state.getLarva());
                String[] newBirds = getChangedBirdPositions(k, state, validCoordinates);
                newState.setBirds(newBirds);
                newState.setIsLarvaNode(true);
                childStates[k] = newState;
            }
        }

        return childStates;

    }


    /**
     * @param moveIndex
     * @param state
     * @param coordinates
     * @return
     */
    private static String[] getChangedBirdPositions(int moveIndex, State state, int[][] coordinates) {


        int[] numericBirdCoordinates = getNumericCoordinate(state.getBirds());


        int oldPosition = coordinates[moveIndex][0];


        for (int i = 0; i < numericBirdCoordinates.length; i++) {

            if (oldPosition == numericBirdCoordinates[i]) {
                numericBirdCoordinates[i] = coordinates[moveIndex][1];

//                System.out.println("Movi : " + oldPosition + " hacia: " + numericBirdCoordinates[i]);
            }

        }


        return getStringCoordinates(numericBirdCoordinates);

    }


    /**
     * This method takes a 2 dimension int array and eliminates any empty position it may have
     *
     * @param array to trim
     * @return trimed array of smaller or equal dimension as original array
     */
    private static int[][] trimBidimensionalArray(int[][] array) {


        int arrayLength = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i][0] != 0 && array[i][1] != 0)
                arrayLength++;

        }

        int[][] trimmedArray = new int[arrayLength][2];

        for (int i = 0; i < array.length; i++) {
            if (array[i][0] != 0 && array[i][1] != 0) {
                trimmedArray[i][0] = array[i][0];
                trimmedArray[i][1] = array[i][1];
            }
        }

        return trimmedArray;

    }


    private static boolean isCoordinateValid(int coordinate) {

        boolean result = (coordinate <= BIGGEST_POSITION_POSSIBLE &&
                coordinate >= SMALLEST_POSITION_POSSIBLE &&
                coordinate % 10 != 0);

        return result;
    }

    private static boolean isSpaceAvailable(State state, int coordinate) {


        int numericLarvaCoordinate = getNumericCoordinate(state.getLarva());
        int[] numericBirdCoordinates = getNumericCoordinate(state.getBirds());

        if (coordinate == numericLarvaCoordinate) return false;

        for (Integer i : numericBirdCoordinates) {
            if (coordinate == i) return false;
        }


        return true;


    }

    private static String getStringCoordinates(int coordinate) {


        String letters = "ABCDEFGH";


        /** this converts a 19 to a 1 for example */
        int number = (int) coordinate / 10;
        /** this converts a 19 to a 9 for example */
        int letterNumber = (coordinate - number * 10) - 1;
        char letter = letters.charAt(letterNumber);
        String result = letter + String.valueOf(number);

        return result;

    }

    private static String[] getStringCoordinates(int[] coordinates) {


        String letters = "ABCDEFGH";


        String[] resultSet = new String[coordinates.length];

        for (int i = 0; i < coordinates.length; i++) {

            int coordinate = coordinates[i];

            /** this converts a 19 to a 1 for example */
            int number = (int) coordinate / 10;

            /** this converts a 19 to a 9 for example */
            int letterNumber = (coordinate - number * 10) - 1;

            char letter = letters.charAt(letterNumber);
            String result = letter + String.valueOf(number);

            resultSet[i] = result;

        }

        return resultSet;

    }

    private static int getNumericCoordinate(String position) {

        int letter = mYard.getLetterIndex(position.charAt(0)) + 1;
        int result = Character.getNumericValue(position.charAt(1)) * 10 + letter;

        return result;


    }

    private static int[] getNumericCoordinate(String[] positions) {


        int[] resultSet = new int[4];
        int i = 0;
        for (String position : positions) {

            int letter = mYard.getLetterIndex(position.charAt(0)) + 1;
            int result = Character.getNumericValue(position.charAt(1)) * 10 + letter;
            resultSet[i] = result;
            i++;
        }

        return resultSet;


    }

}
