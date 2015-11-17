package ca.concordia.davidazar;

/**
 * Created by David on 15/11/15.
 */
public class Utils {



    private static Yard mYard = Yard.getInstance();

    public static String getStringCoordinates(int coordinate) {


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


    public static String[] getStringCoordinates(int[] coordinates) {


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


    public static int getNumericCoordinate(String position) {

//        System.out.println("getNumericCoordinate   position " + position);

        int letter = mYard.getLetterIndex(position.charAt(0)) + 1;
//        System.out.println("getNumericCoordinate   letterIndex " + letter);

        int result = Character.getNumericValue(position.charAt(1)) * 10 + letter;
//        System.out.println("getNumericCoordinate   result " + result);


        return result;


    }

    public static int[] getNumericCoordinate(String[] positions) {


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


    public static int getCoordinatesGridWise(int[] coordinates){

        int row = coordinates[0];
        int column = coordinates[1];

        int result = row * 8 + (column+1);

        System.out.println("Heuristic value for individual spot: "+result);


        return result;

    }


    /*This method converts full coordinates (D3, etc) to numeric coordinates in the grid array ([3][2])*/
    public static int[] getNumericCoordinates(String textCoordinates) {


        String letters = "ABCDEFGH";

        char letter = textCoordinates.charAt(0);
        int number = Character.getNumericValue(textCoordinates.charAt(1));


        int rowCoordinate = 8 - number;
        int columnCoordinate = letters.indexOf(letter);


        int[] coordinates = new int[2];
        coordinates[0] = rowCoordinate;
        coordinates[1] = columnCoordinate;

        System.out.println("----"+coordinates[0]+" "+coordinates[1]);

        return coordinates;


    }


}
