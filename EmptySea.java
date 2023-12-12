public class EmptySea extends Ship{

    EmptySea(){
        this.length = 1;
        this.hit = new boolean[this.length];
        for (int i = 0; i < this.length; i++) {
            this.hit[i] = false;
        }
    }

    /**
     * Specified by:
     * getShipType in class Ship
     * @return "empty", indicating that this is an Empty sea tile.
     */
    public String getShipType(){ return "EmptySea"; }

    /**
     * Since an EmptySea is empty by definition, it is not possible that one can be sunk.
     * Overrides:
     * isSunk in class Ship
     * @return false always, since nothing will be hit
     */
    public boolean isSunk(){ return false; }

    /**
     * Since an EmptySea is empty by definition, shooting at one will always be a miss.
     * Overrides shootAt in ship class
     * @param row
     * @param column
     * @return false always, since nothing will be hit
     */
    public boolean shootAt(int row, int column){ return false; }

}
