public class Destroyer extends Ship{
    Destroyer(){
        this.length = 2;
        this.hit = new boolean[this.length];
        for (int i = 0; i < this.length; i++) {
            this.hit[i] = false;
        }
    }

    String getShipType(){ return "Destroyer" ;}
}
