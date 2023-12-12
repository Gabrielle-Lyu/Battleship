public class Cruiser extends Ship{
    /**
     * Sets the inherited length variable and initializes the hit array, based on the size of this ship (3 tiles).
     */
    public Cruiser(){
        this.length=3;
        this.hit = new boolean[this.length];
        for (int i = 0; i < this.length; i++) {
            this.hit[i] = false;
        }
    }

    /**
     *
     * @return "Cruiser", indicating that this is a Cruiser.
     */
    public String getShipType(){ return "Cruiser"; }


}
