public class Submarine extends Ship{
//    Constructor
    Submarine(){
        this.length = 1;
        this.hit = new boolean[this.length];
        for (int i = 0; i < this.length; i++) {
            this.hit[i] = false;
        }
    }
    String getShipType(){ return "Submarine"; }
}
