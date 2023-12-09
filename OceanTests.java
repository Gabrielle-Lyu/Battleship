import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OceanTests {
    private Ocean ocean;

    @Before
    public void setUp(){
        ocean = new Ocean();
        ocean.placeAllShipsRandomly();
    }

    @Test
    public void testCorrectNumberOfShips() {
        int battleships = countShipsOfType(Battleship.class);
        int cruisers = countShipsOfType(Cruiser.class);
        int destroyers = countShipsOfType(Destroyer.class);
        int submarines = countShipsOfType(Submarine.class);

        assertEquals("There should be 1 battleship", 1, battleships);
        assertEquals("There should be 2 cruisers", 2, cruisers);
        assertEquals("There should be 3 destroyers", 3, destroyers);
        assertEquals("There should be 4 submarines", 4, submarines);

    }

    @Test
    public void testIsOccupied() {
        boolean foundShip = false;
        int shipRow = -1;
        int shipCol = -1;

        for (int row = 0; row < 10 && !foundShip; row++) {
            for (int col = 0; col < 10 && !foundShip; col++) {
                if (!(ocean.getShipArray()[row][col] instanceof EmptySea)) {
                    shipRow = row;
                    shipCol = col;
                    foundShip = true;
                }
            }
        }

        assertTrue("A ship should be found for testing", foundShip);
        assertTrue("Expected occupied cell", ocean.isOccupied(shipRow, shipCol));
    }

    @Test
    public void testShootAt() {
        int[] shipLocation = findShipLocation();
        assertTrue("Shooting at a ship should return true", ocean.shootAt(shipLocation[0], shipLocation[1]));

        int[] emptySeaLocation = findEmptySeaLocation();
        assertFalse("Shooting at empty sea should return false", ocean.shootAt(emptySeaLocation[0], emptySeaLocation[1]));

        assertEquals("Two shots should have been fired", 2, ocean.getShotsFired());
    }

    @Test
    public void testGetShotsFired() {
        ocean.shootAt(0, 0);
        assertEquals("Shots fired count should be 1", 1, ocean.getShotsFired());

        ocean.shootAt(1, 1);
        assertEquals("Shots fired count should be 2", 2, ocean.getShotsFired());
    }

    @Test
    public void testGetHitCount() {
        int[] shipLocation = findShipLocation();
        ocean.shootAt(shipLocation[0], shipLocation[1]);
        assertEquals("Hit count should be 1", 1, ocean.getHitCount());
    }

    @Test
    public void testGetShipsSunk() {
        // Sink a ship
        sinkAShip(ocean);
        assertEquals("Ships sunk count should be 1", 1, ocean.getShipsSunk());
    }

    @Test
    public void testIsGameOver() {
        sinkAllShips(ocean);
        assertTrue("Game should be over after sinking all ships", ocean.isGameOver());
    }

    @Test
    public void testGetShipArray() {
        Ship[][] ships = ocean.getShipArray();
        assertNotNull("Ship array should not be null", ships);
        assertEquals("Ship array should have 10 rows", 10, ships.length);
        assertEquals("Ship array should have 10 columns", 10, ships[0].length);
    }

    @Test
    public void testPrint() {
        try {
            ocean.print();
        } catch (Exception e) {
            fail("Print method should not throw any exceptions");
        }
    }

    private int[] findShipLocation() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (!(ocean.getShipArray()[row][col] instanceof EmptySea)) {
                    return new int[] {row, col};
                }
            }
        }
        throw new IllegalStateException("No ships found in the ocean");
    }

    private int[] findEmptySeaLocation() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (ocean.getShipArray()[row][col] instanceof EmptySea) {
                    return new int[] {row, col};
                }
            }
        }
        throw new IllegalStateException("No empty sea found in the ocean");
    }

    private int countShipsOfType(Class<? extends Ship> shipType) {
        int count = 0;
        Ship[][] ships = ocean.getShipArray();
        for (int i = 0; i < ships.length; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                if (shipType.isInstance(ships[i][j]) &&
                        i == ships[i][j].getBowRow() &&
                        j == ships[i][j].getBowColumn()) {
                    count++;
                }
            }
        }
        return count;
    }

    private void sinkAShip(Ocean ocean) {
        boolean shipFound = false;
        for (int row = 0; row < 10 && !shipFound; row++) {
            for (int col = 0; col < 10 && !shipFound; col++) {
                Ship ship = ocean.getShipArray()[row][col];
                if (!(ship instanceof EmptySea)) {
                    for (int part = 0; part < ship.getLength(); part++) {
                        int shootRow = ship.isHorizontal() ? row : row + part;
                        int shootCol = ship.isHorizontal() ? col + part : col;
                        ocean.shootAt(shootRow, shootCol);
                    }
                    shipFound = true;
                }
            }
        }

        if (!shipFound) {
            throw new IllegalStateException("No ships found to sink in the ocean");
        }
    }

    private void sinkAllShips(Ocean ocean) {
        boolean[][] hitRecord = new boolean[10][10];

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (!(ocean.getShipArray()[row][col] instanceof EmptySea) && !hitRecord[row][col]) {
                    for (int part = 0; part < ocean.getShipArray()[row][col].getLength(); part++) {
                        int shootRow = ocean.getShipArray()[row][col].isHorizontal() ? row : row + part;
                        int shootCol = ocean.getShipArray()[row][col].isHorizontal() ? col + part : col;

                        if (!hitRecord[shootRow][shootCol]) {
                            ocean.shootAt(shootRow, shootCol);
                            hitRecord[shootRow][shootCol] = true;
                        }
                    }
                }
            }
        }
    }

}


