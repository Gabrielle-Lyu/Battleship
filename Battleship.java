public class Battleship extends Ship {
//    Constructor
    Battleship(){
        this.length = 4;
        this.hit = new boolean[this.length];
        for (int i = 0; i < this.length; i++) {
            this.hit[i] = false;
        }
    }

    String getShipType(){ return "Battleship"; }
}
