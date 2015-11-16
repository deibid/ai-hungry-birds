package ca.concordia.davidazar;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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


//		todo: Create an unbeatable AI

        System.out.println("ESTOY EN AI MAKEMOVE");

        generateStateSpace();
        evaluateMinimax();
        generateMove();


        //todo agregar creacion de move.
        return null;

    }

    /**
     * Method that calls gerenateChildren() and triggers the creation of the state-space
     *
     * @return the root node
     */
    private State generateStateSpace() {


        State root = mYard.getCurrentState();


        root = generateChildren(root, 1);


        System.out.println("termine state space");


        return root;


    }

    private void evaluateMinimax() {

    }


    private void generateMove() {


    }


    private static State generateChildren(State state, int level) {

        System.out.println("Entrando a generateChildren");

        if (level >= MAX_LEVELS) return null;

        State[] children;
        if (state.isIsLarvaNode()) {

            children = getLarvaChildNodes(state);

        } else {

            children = getBirdChildNodes(state);
        }

        if (children == null) return null;

        for (int i = 0; i < children.length; i++) {

            System.out.println(children[i]);

        }


        state.setChildStates(children);

        for (int j = 0; j < state.getChildCount(); j++) {
            generateChildren(state.getChildState(j), level + 1);
        }

        return null;
    }


    private static State[] getLarvaChildNodes(State state) {

        String larva = state.getLarva();
        String[] birds = state.getBirds();
        System.out.println("getLarvaChildNodes larva: " + larva);

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

            System.out.println("tempCoordinate : " + tempCoordinate);

            if (isCoordinateValid(tempCoordinate)) {
                if (isSpaceAvailable(state, tempCoordinate)) {
                    validCoordinates.add(tempCoordinate);
                    System.out.println("Coordenada fue valida");
                } else {
                    System.out.println("Coordenada NO valida");
                }
            } else {
                System.out.println("Coordenada NO valida");
            }

        }


        int length = validCoordinates.size();
        System.out.println("Valid coordinates size: " + length);
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

    private static State[] getBirdChildNodes(State state) {


        int[] numericBirdCoordinates = getNumericCoordinate(state.getBirds());


        System.out.println("getBirdChildNodes");


        int[][] validCoordinates = new int[8][2];
        int amountOfValidCoordinates = 0;

        for (int i = 0; i < MAX_POSSIBLE_MOVES_PER_BIRD; i++) {

            int tempCoordinate = 0;
            switch (i) {

                case 0:

                    tempCoordinate = 0;
                    for (int j = 0; j < state.getBirds().length; j++) {

                        int bird = numericBirdCoordinates[j];

                        tempCoordinate = bird + TO_UPPER_RIGHT_OFFSET;
                        System.out.println("tempCoordinate : " + tempCoordinate);

                        if (isCoordinateValid(tempCoordinate)) {
                            if (isSpaceAvailable(state, tempCoordinate)) {
                                validCoordinates[amountOfValidCoordinates][0] = bird;
                                validCoordinates[amountOfValidCoordinates][1] = tempCoordinate;
                                amountOfValidCoordinates++;
                                System.out.println("Coordenada fue valida");
                            }
                        }
                    }

                    break;
                case 1:

                    tempCoordinate = 0;
                    for (int j = 0; j < state.getBirds().length; j++) {

                        int bird = numericBirdCoordinates[j];

                        tempCoordinate = bird + TO_UPPER_LEFT_OFFSET;
                        System.out.println("tempCoordinate : " + tempCoordinate);

                        if (isCoordinateValid(tempCoordinate)) {
                            if (isSpaceAvailable(state, tempCoordinate)) {
                                validCoordinates[amountOfValidCoordinates][0] = bird;
                                validCoordinates[amountOfValidCoordinates][1] = tempCoordinate;
                                amountOfValidCoordinates++;
                                System.out.println("Coordenada fue valida");
                            }
                        }
                    }
                    break;

            }


        }


        State[] childStates = new State[validCoordinates.length];

        for (int k = 0; k < childStates.length; k++) {

            /** This line avoids the insertion of a repetetive state */
            if(validCoordinates[k][0]!= 0 && validCoordinates[k][1]!= 0) {
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


    private static String[] getChangedBirdPositions(int moveIndex, State state, HashMap<Integer, Integer> coordinates) {


        int[] numericBirdCoordinates = getNumericCoordinate(state.getBirds());


        Integer[] oldPositions = coordinates.keySet().toArray(new Integer[coordinates.size()]);


//        Integer[] oldPositions = (Integer[]) coordinates.entrySet().toArray();

        Integer oldPosition = oldPositions[moveIndex];

        for (int i = 0; i < numericBirdCoordinates.length; i++) {

            if (oldPosition == numericBirdCoordinates[i]) {
                numericBirdCoordinates[i] = coordinates.get(oldPosition);

                System.out.println("Movi : " + oldPosition + " hacia: " + coordinates.get(oldPosition));
            }

        }


        return getStringCoordinates(numericBirdCoordinates);

    }


    private static String[] getChangedBirdPositions(int moveIndex, State state, int[][] coordinates) {


        int[] numericBirdCoordinates = getNumericCoordinate(state.getBirds());


        int oldPosition = coordinates[moveIndex][0];


        for (int i = 0; i < numericBirdCoordinates.length; i++) {

            if (oldPosition == numericBirdCoordinates[i]) {
                numericBirdCoordinates[i] = coordinates[moveIndex][1];

                System.out.println("Movi : " + oldPosition + " hacia: " + numericBirdCoordinates[i]);
            }

        }




        return getStringCoordinates(numericBirdCoordinates);

    }

    private static boolean isCoordinateValid(int coordinate) {
//        System.out.println("isCoordinateValid: "+coordinate);
        boolean result = (coordinate <= BIGGEST_POSITION_POSSIBLE && coordinate >= SMALLEST_POSITION_POSSIBLE && coordinate%10 !=0);

//        System.out.println("isCoordinateValid: result  "+result);
        return result;
    }


    private static boolean isSpaceAvailable(State state, int coordinate) {


        int numericLarvaCoordinate = getNumericCoordinate(state.getLarva());
        int[] numericBirdCoordinates = getNumericCoordinate(state.getBirds());
//        System.out.println("numericLarva: "+numericLarvaCoordinate);
//        System.out.println("numericBirds: "+numericBirdCoordinates);
//        System.out.println("coordinate: "+coordinate);


        if (coordinate == numericLarvaCoordinate) return false;

        for (Integer i : numericBirdCoordinates) {

//            System.out.println("numericBird: "+i);
//            System.out.println("coordinate: "+coordinate);
            if (coordinate == i) return false;
        }


        return true;


    }


    private static String getStringCoordinates(int coordinate) {


        String letters = "ABCDEFGH";

//        System.out.println("getStringCoordinates   coordinate " + coordinate);

        /** this converts a 19 to a 1 for example */
        int number = (int) coordinate / 10;
//        System.out.println("getStringCoordinates   number " + number);

        /** this converts a 19 to a 9 for example */
        int letterNumber = (coordinate - number * 10)-1;
//        System.out.println("getStringCoordinates   letterNumber " + letterNumber);

        char letter = letters.charAt(letterNumber);
//        System.out.println("getStringCoordinates   letter " + letter);

        String result = letter + String.valueOf(number);
//        System.out.println("getStringCoordinates   result " + result);

        return result;

    }


    private static String[] getStringCoordinates(int[] coordinates) {


        String letters = "ABCDEFGH";


        String[] resultSet = new String[coordinates.length];

        for (int i = 0; i < coordinates.length; i++) {

            int coordinate = coordinates[i];
//            System.out.println("getStringCoordinates   coordinate " + coordinate);

            /** this converts a 19 to a 1 for example */
            int number = (int) coordinate / 10;
//            System.out.println("getStringCoordinates   number " + number);

            /** this converts a 19 to a 9 for example */
            int letterNumber = (coordinate - number * 10)-1;
//            System.out.println("getStringCoordinates   letterNumber " + letterNumber);

            char letter = letters.charAt(letterNumber);
//            System.out.println("getStringCoordinates   letter " + letter);


            String result = letter + String.valueOf(number);
//            System.out.println("getStringCoordinates   result " + result);

            resultSet[i] = result;

        }

        return resultSet;

    }


    private static int getNumericCoordinate(String position) {

//        System.out.println("getNumericCoordinate   position " + position);

        int letter = mYard.getLetterIndex(position.charAt(0)) + 1;
//        System.out.println("getNumericCoordinate   letterIndex " + letter);

        int result = Character.getNumericValue(position.charAt(1)) * 10 + letter;
//        System.out.println("getNumericCoordinate   result " + result);


        return result;


    }

    private static int[] getNumericCoordinate(String[] positions) {


        int[] resultSet = new int[4];
        int i = 0;
        for (String position : positions) {

//            System.out.println("getNumericCoordinate   position " + position);

            int letter = mYard.getLetterIndex(position.charAt(0)) + 1;
//            System.out.println("getNumericCoordinate   letterIndex " + letter);

            int result = Character.getNumericValue(position.charAt(1)) * 10 + letter;
//            System.out.println("getNumericCoordinate   result " + result);

            resultSet[i] = result;

            i++;


        }

        return resultSet;


    }


}
