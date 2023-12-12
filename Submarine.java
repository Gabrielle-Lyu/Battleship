public class Submarine extends Ship{
    /**
     * Sets the inherited length variable and initializes the hit array, based on the size of this ship (1 tiles).
     */
    public Submarine(){
        this.length = 1;
        this.hit = new boolean[this.length];
        for (int i = 0; i < this.length; i++) {
            this.hit[i] = false;
        }
    }

    /**
     * Indicating that this is a submarine
     * @return "Submarine"
     */
    public String getShipType(){ return "Submarine"; }
}
