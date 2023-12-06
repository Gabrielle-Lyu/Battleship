public class EmptySea extends Ship{
//    Constructor
    EmptySea(){

    }

    String getShipType(){ return "EmptySea"; }

    boolean isSunk(){ return false; }

    boolean shootAt(int row, int column){ return false; }

//    String toString(){}
}
