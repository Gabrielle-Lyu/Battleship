public class Destroyer extends Ship{
    /**
     * Sets the inherited length variable and initializes the hit array, based on the size of this ship (2 tiles).
     */
    public Destroyer(){
        this.length = 2;
        this.hit = new boolean[this.length];
        for (int i = 0; i < this.length; i++) {
            this.hit[i] = false;
        }
    }

    /**
     * Indicating that this is a Destroyer.
     * @return "Destroyer"
     */
    public String getShipType(){ return "Destroyer" ;}
}
