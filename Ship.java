public abstract class Ship {
    protected int bowColumn;
    protected int bowRow;
    protected boolean[] hit;
    protected boolean horizontal;
    protected int length;

//    Constructor
    Ship(){
    }

    int getBowColumn(){ return bowColumn; };

    int getBowRow(){ return bowRow; }
    int getLength(){ return length; }

    abstract String getShipType();
    boolean isHorizontal(){ return false; }
    boolean isSunk(){ return false; }
    boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean){ return false; }
    void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){ }
    void setBowColumn(int bowColumn){ }
    void setBowRow(int bowRow){ }
    void setHorizontal(boolean horizontal){ }
    boolean shootAt(int row, int column){ return false; }

//    String toString(){return ""}

}
