import java.util.Scanner;

public class BattleshipGame {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        boolean playAgain;
        do{
            Ocean ocean = new Ocean();
            ocean.placeAllShipsRandomly();
//            delete the following section after testing
            System.out.println("The answer is: ");
            ocean.printAnswer();
//            ------------------------------
            play(ocean, scnr);
            playAgain = playAgain(scnr);
        }while(playAgain);
    }

    private static int[] getUserShot(Scanner scnr){
        while(true){
            System.out.println("Please select a place to shoot at, separate with space, in the form of 'row col' ");
            int row = scnr.nextInt();
            int col = scnr.nextInt();
            if(row > 9 || row < 0 || col > 9 || col < 0){
                System.out.println("Invalid input.");
                continue;
            }
            return new int[]{row, col};
        }
    }

    private static void play(Ocean ocean, Scanner scnr){
        System.out.println("GAME START");
        while(!ocean.isGameOver()){
            ocean.print();
            int[] shot = getUserShot(scnr);
            boolean hit = ocean.shootAt(shot[0], shot[1]);
            if(hit){
                System.out.println("You have hit the ship. ");
            } else {
                System.out.println("You missed. ");
            }

            System.out.println("Shots fired: " + ocean.getShotsFired());
            System.out.println("Hit count: " + ocean.getHitCount());
            System.out.println("Ships sunk: " + ocean.getShipsSunk());

        }

        System.out.println("Game over! You have sunk all ships in " + ocean.getShotsFired() + " shots.");
    }

    private static boolean playAgain(Scanner scnr){
        System.out.println("Play again? (Y/N)");
        return scnr.next().equals("Y");
    }
}
