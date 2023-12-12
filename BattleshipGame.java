import java.sql.SQLOutput;
import java.util.Scanner;

public class BattleshipGame {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        do{
            Ocean ocean = new Ocean();
            ocean.placeAllShipsRandomly();
            ocean.print();
            play(ocean, scnr);
        }while(playAgain(scnr));
    }

    private static int[] getUserShot(Scanner scnr){
        while(true){
            System.out.println("Enter the row and column to shoot at (0-9), separated by a space: ");
            String input = scnr.nextLine().trim();
            String[] parts = input.split("\\s+");

            if (parts.length != 2 || !isInteger(parts[0]) || !isInteger(parts[1])) {
                System.out.println("Invalid format. Please enter two numbers separated by a space.");
                continue;
            }

            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);

            if (isValidCoordinate(row) && isValidCoordinate(col)) {
                return new int[]{row, col};
            } else {
                System.out.println("Invalid input. Numbers must be between 0 and 9.");
            }
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidCoordinate(int number) {
        return number >= 0 && number <= 9;
    }

    private static void play(Ocean ocean, Scanner scnr){
        System.out.println("GAME START");
        while(!ocean.isGameOver()){
            int[] shot = getUserShot(scnr);
            Ship target = ocean.getShipArray()[shot[0]][shot[1]];
            boolean sunkPreviously = target.isSunk();

            boolean hit = ocean.shootAt(shot[0], shot[1]);
            if(hit){
                System.out.println("You have HIT the ship. ");
                if(!sunkPreviously && target.isSunk()){
                    System.out.println("You just sunk a " + target.getShipType());
                }
            } else {
                System.out.println("You MISSED. ");
            }

            System.out.println("Shots fired: " + ocean.getShotsFired());
            System.out.println("Hit count: " + ocean.getHitCount());
            System.out.println("Ships sunk: " + ocean.getShipsSunk());
            ocean.print();

        }

        System.out.println("Game over! You have sunk all ships in " + ocean.getShotsFired() + " shots.");
    }

    private static boolean playAgain(Scanner scnr){
        System.out.println("Play again? (yes/no)");
        return scnr.next().equals("yes");
    }
}
