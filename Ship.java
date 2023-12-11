/**
 * Ship is the abstract class for all of the ships and sea tiles that will make up the game of Battleship. Ships of all kinds are always considered to be facing up or to the left, meaning that any portion of the ship that is not the bow will be at a higher numbered row or column than the bow.
 * Author: harry
 */
public abstract class Ship {
    /**
     * The column (0 to 9) which contains the bow (front) of the ship.
     * This field represents the horizontal position of the ship's bow on the grid.
     */
    protected int bowColumn;
    /**
     * The row (0 to 9) which contains the bow (front) of the ship.
     * This field represents the vertical position of the ship's bow on the grid.
     */
    protected int bowRow;
    /**
     * hit is an array of four booleans telling whether that part of the ship has been hit.
     */
    protected boolean[] hit;
    /**
     * true if the ship occupies a single row, false otherwise.
     */
    protected boolean horizontal;
    /**
     * The number of tiles occupied by the ship.
     */
    protected int length;
    /**
     * Returns the column of the bow (front) of the ship.
     *
     * @return the column number where the bow of the ship is located.
     */
    int getBowColumn(){ return bowColumn; };
    /**
     * Returns the row of the bow (front) of the ship.
     *
     * @return the row number where the bow of the ship is located.
     */
    int getBowRow(){ return bowRow; }
    /**
     * Returns the length of the ship.
     *
     * @return the length of the ship.
     */
    int getLength(){ return length; }

    abstract String getShipType();
    /**
     * Returns whether this ship is placed horizontally.
     *
     * @return true if the ship is horizontal (facing left), false otherwise.
     */
    boolean isHorizontal(){ return horizontal; }
    /**
     * Determines if the ship has been sunk. A ship is sunk if all its parts have been hit.
     *
     * @return true if every part of the ship has been hit, false otherwise.
     */
    boolean isSunk() {
        for (boolean partHit : this.hit) {
            if (!partHit) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return the array hit of four boolean values
     */
    boolean[] getHit() { return hit; }
    /**
     * Determines whether it is valid to place this ship at the specified location in the given ocean.
     * A ship placement is valid if it does not overlap or touch other ships and stays within the ocean boundaries.
     *
     * @param row        the row to place the ship's bow.
     * @param column     the column to place the ship's bow.
     * @param horizontal true if the ship is to be placed horizontally, false otherwise.
     * @param ocean      the ocean where the ship is to be placed.
     * @return true if the ship can be validly placed, false otherwise.
     */
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
            int checkRow;
            int checkColumn;
            if (horizontal){
                checkRow = row;
            } else{
                checkRow = row+i;
            }
            if (horizontal){
                checkColumn = column + i;
            } else{
                checkColumn = column;
            }

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
    /**
     * Places the ship in the specified location in the ocean. This assigns values to the ship's position and orientation
     * and marks the ship's location in the ocean grid.
     *
     * @param row        the row to place the ship's bow.
     * @param column     the column to place the ship's bow.
     * @param horizontal true if the ship is to be placed horizontally, false otherwise.
     * @param ocean      the ocean where the ship is to be placed.
     */
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
    /**
     * Sets the bow column of the ship.
     *
     * @param bowColumn the column to set as the bow of the ship.
     */
    void setBowColumn(int bowColumn){
        this.bowColumn = bowColumn;
    }
    /**
     * Sets the bow row of the ship.
     *
     * @param bowRow the row to set as the bow of the ship.
     */
    void setBowRow(int bowRow){
        this.bowRow = bowRow;
    }
    /**
     * Sets the orientation of the ship to horizontal.
     *
     * @param horizontal the orientation to set. If true, the ship is set to horizontal.
     */
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
