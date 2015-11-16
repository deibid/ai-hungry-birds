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





}
