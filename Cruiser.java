public class Cruiser extends Ship{
//    Constructor
    Cruiser(){
        this.length=3;
        this.hit = new boolean[this.length];
        for (int i = 0; i < this.length; i++) {
            this.hit[i] = false;
        }
    }

    String getShipType(){ return "Cruiser"; }


}
