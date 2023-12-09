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
    boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean){
        if(horizontal){
            if((column + this.length-1)>9){
                return false;
            }
        } else{
            if((row + this.length-1)>9){
                return false;
            }
        }

        for(int i = 0; i < this.length; i++){
            int checkRow = horizontal ? row : row + i;
            int checkColumn = horizontal ? column + i : column;

            if (ocean.isOccupied(checkRow,checkColumn)){
                return false;
            }
            for (int dr = -1; dr <= 1; dr++){
                for(int dc = -1; dc <=1; dc++){
                    int adjacentRow = checkRow + dr;
                    int adjacentColumn = checkColumn + dc;

                    if (adjacentRow >= 0 && adjacentRow < 10 &&
                            adjacentColumn >= 0 && adjacentColumn < 10) {
                        if (ocean.isOccupied(adjacentRow, adjacentColumn)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){
        this.bowRow = row;
        this.bowColumn = column;
        this.horizontal = horizontal;

        for (int i = 0; i < length; i++){
            if (horizontal){
                ocean.ships[row][column+i] = this;
            } else{
                ocean.ships[row+i][column] = this;
            }
        }
    }
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



}
