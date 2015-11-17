package ca.concordia.davidazar;

import java.util.*;

/**
 * Created by David on 15/11/15.
 */
public class State {


    private String mLarva;
    private String[] mBirds;
    private boolean mIsLarvaNode;

    private State[] mChildStates;

    private int mEvaluatedHeuristicValue = 0;
    private int mGivenHeuristicValue = 0;

    public State() {
    }

    public State(String larva, String[] birds, boolean isLarvaNode, State[] children) {
        mLarva = larva;
        mBirds = birds;
        mIsLarvaNode = isLarvaNode;
        mChildStates = children;
    }


    @Override
    public String toString() {


        String childrenDescription = "";

//
//        for(int i = 0; i< mChildStates.length){
//
//            childrenDescription = childrenDescription +
//
//        }

        String birds = "";

        for (int i = 0; i < mBirds.length; i++) {
            birds = birds + mBirds[i] + "  ";
        }

        String description = "State object: " +
                "\nLarva: " + mLarva +
                "\nBirds: " + birds +
                "\nisLarvaNode: " + mIsLarvaNode +
                "\nChildren: " + mChildStates;


        return description;


    }


    /**
     * This method returns the node that should be chosen by the parent,
     * depending wether it is Min or Max
     *
     * @param max true when parent is max node, false when it is min node
     * @return node that should be played, or chosen
     */
    public State getMinMaxChild(boolean max) {

        State stateToChose = mChildStates[0];

        ArrayList<State> equalHeuristicStatePool = new ArrayList<>();

//        int amountOfEqualHeuristicStates = 0;

        int heursiticValue;
        if (max) heursiticValue = -1000;
        else heursiticValue = 1000;

        for (State s : mChildStates) {
            if (max) {

//                State s = mChildStates[i];
//                if(i == 0){
//                    equalHeuristicStatePool.add(s);
//                    heursiticValue = s.getGivenHeuristicValue();
//                }


                if (s.getGivenHeuristicValue() == stateToChose.getGivenHeuristicValue()) {
                    equalHeuristicStatePool.add(s);
                } else if (s.getGivenHeuristicValue() > stateToChose.getGivenHeuristicValue()) {
                    stateToChose = s;
                }


            } else {
                if (s.getGivenHeuristicValue() == stateToChose.getGivenHeuristicValue()) {
                    equalHeuristicStatePool.add(s);
                } else if (s.getGivenHeuristicValue() < stateToChose.getGivenHeuristicValue())
                    stateToChose = s;
            }
        }


        if (equalHeuristicStatePool.size() > 1) {

            Random random = new Random();
            int index = random.nextInt(equalHeuristicStatePool.size() - 1);
            stateToChose = equalHeuristicStatePool.get(index);
//            System.out.println("Hubo repeticiones, y escogi el nodo: "
//                    + index + " De un total de: "
//                    + equalHeuristicStatePool.size() + " repeticiones");
        }


        System.out.println("Chosen state to play: " + stateToChose);

        return stateToChose;

    }


    public int getEvaluatedHeuristicValue() {
        mEvaluatedHeuristicValue = applyNaiveHeuristic();

        /** nodes that evaluate heuristic ( leaf nodes ) wont get an assigned value by a child*/
        mGivenHeuristicValue = mEvaluatedHeuristicValue;

        return mEvaluatedHeuristicValue;
    }


    public void setGivenHeuristicValue(int h) {
        mGivenHeuristicValue = h;
    }

    public int getGivenHeuristicValue() {
        return mGivenHeuristicValue;
    }

    private int applyNaiveHeuristic() {

        int[] larvaCoordinates = Utils.getNumericCoordinates(mLarva);
        int larvaInGrid = Utils.getCoordinatesGridWise(larvaCoordinates);


        for (int i = 0; i < mBirds.length; i++) {

            int[] birdCoordinates = Utils.getNumericCoordinates(mBirds[i]);
            int birdInGrid = Utils.getCoordinatesGridWise(birdCoordinates);
//            System.out.println("birdInGrid: " + birdInGrid);

            larvaInGrid = larvaInGrid - birdInGrid;

        }
//        System.out.println("finalValue: " + larvaInGrid);
        return larvaInGrid;
    }

    public State getChildState(int i) {

        return mChildStates[i];

    }


    public int getChildCount() {
        return mChildStates.length;
    }


    public String getLarva() {
        return mLarva;
    }

    public void setLarva(String mLarva) {
        this.mLarva = mLarva;
    }

    public String[] getBirds() {
        return mBirds;
    }

    public void setBirds(String[] mBirds) {
        this.mBirds = mBirds;
    }

    public boolean isIsLarvaNode() {
        return mIsLarvaNode;
    }

    public void setIsLarvaNode(boolean mIsLarvaNode) {
        this.mIsLarvaNode = mIsLarvaNode;
    }

    public State[] getChildStates() {
        return mChildStates;
    }

    public void setChildStates(State[] mChildStates) {
        this.mChildStates = mChildStates;
    }
}
