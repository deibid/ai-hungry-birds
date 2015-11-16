package ca.concordia.davidazar;

/**
 * Created by David on 15/11/15.
 */
public class State {


    private String mLarva;
    private String[] mBirds;
    private boolean mIsLarvaNode;

    private State[] mChildStates;





    public State(){}

    public State(String larva, String[] birds, boolean isLarvaNode,State[] children){
        mLarva = larva;
        mBirds = birds;
        mIsLarvaNode = isLarvaNode;
        mChildStates = children;
    }



    @Override
    public String toString(){




        String childrenDescription = "";

//
//        for(int i = 0; i< mChildStates.length){
//
//            childrenDescription = childrenDescription +
//
//        }

        String birds = "";

        for (int i = 0; i<mBirds.length; i++){
            birds = birds + mBirds[i]+"  ";
        }

        String description = "State object: \nLarva: "+mLarva+
                "\nBirds: "+birds+
                "\n isLarvaNode: "+mIsLarvaNode+
                "Children: "+mChildStates;








        return description;


    }

    public State getChildState(int i){

        return mChildStates[i];

    }


    public int getChildCount(){
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
