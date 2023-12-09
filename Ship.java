public abstract class Ship {
    protected int bowColumn;
    protected int bowRow;
    protected boolean[] hit;
    protected boolean horizontal;
    protected int length;

    int getBowColumn(){ return bowColumn; };

    int getBowRow(){ return bowRow; }
    int getLength(){ return length; }

    abstract String getShipType();
    boolean isHorizontal(){ return horizontal; }
    boolean isSunk(){ return false; }
    boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean){ return false; }
    void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){ }
    void setBowColumn(int bowColumn){
        this.bowColumn = bowColumn;
    }
    void setBowRow(int bowRow){
        this.bowRow = bowRow;
    }
    void setHorizontal(boolean horizontal){
        this.horizontal = horizontal;
    }
    boolean shootAt(int row, int column){
        if (isSunk()){
            return false;
        }

        if (isHorizontal()){
            if (row == this.bowRow && (column >= this.bowColumn && column <( this.bowColumn+length))){
                hit[column - this.bowColumn] = true;
                return true;
            }
        } else if (column == this.bowColumn && (row >= this.bowRow && row <( this.bowRow+length))){
            hit[row-this.bowRow] = true;
            return true;
        }
        return false;
    }

//    String toString(){return ""}

}
