import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
public class ShipTests {
    private Cruiser cruiser;
    private Destroyer destroyer;
    private Submarine submarine;
    private Battleship battleship;

    private Ocean ocean;

    @BeforeEach
    public void init(){
        battleship = new Battleship();
        battleship.setBowRow(3);
        battleship.setBowColumn(4);
        battleship.setHorizontal(true);
        cruiser = new Cruiser();
        cruiser.setBowRow(3);
        cruiser.setBowColumn(4);
        cruiser.setHorizontal(true);
        submarine = new Submarine();
        submarine.setBowRow(3);
        submarine.setBowColumn(4);
        submarine.setHorizontal(true);
        destroyer = new Destroyer();
        destroyer.setBowRow(3);
        destroyer.setBowColumn(4);
        destroyer.setHorizontal(true);
        ocean = new Ocean();

    }

    @Test
    void testConstructors(){
        assertEquals(4,battleship.getLength(),"Battleship length should be 4");
        assertEquals(3,cruiser.getLength(),"Cruiser length should be 3");
        assertEquals(2,destroyer.getLength(),"Destroyer length should be 2");
        assertEquals(1,submarine.getLength(),"Submarine length should be 1");

    }

    @Test
    void testGetShipType(){
        assertEquals("Battleship", battleship.getShipType(), "getShipType should return 'Battleship'");
        assertEquals("Cruiser", cruiser.getShipType(), "getShipType should return 'Cruiser'");
        assertEquals("Destroyer", destroyer.getShipType(), "getShipType should return 'Destroyer'");
        assertEquals("Submarine", submarine.getShipType(), "getShipType should return 'Submarine'");
    }

    @Test
    void testGettersAndSetters(){

        assertEquals(3, battleship.getBowRow(), "Bow row should be 3");
        assertEquals(4, battleship.getBowColumn(), "Bow column should be 4");
        assertTrue(battleship.isHorizontal(), "Ship should be horizontal");
    }

    @Test
    void testOkToPlaceShipAtEmptyOcean(){
        assertTrue(battleship.okToPlaceShipAt(0, 0, true, ocean),
                "Should be able to place the ship at the beginning of an empty ocean.");
        assertTrue(cruiser.okToPlaceShipAt(0, 0, true, ocean),
                "Should be able to place the ship at the beginning of an empty ocean.");
        assertTrue(destroyer.okToPlaceShipAt(0, 0, true, ocean),
                "Should be able to place the ship at the beginning of an empty ocean.");
        assertTrue(submarine.okToPlaceShipAt(0, 0, true, ocean),
                "Should be able to place the ship at the beginning of an empty ocean.");
        // when horizontal = false
        assertTrue(battleship.okToPlaceShipAt(0, 0, false, ocean),
                "Should be able to place the ship at the beginning of an empty ocean.");
        assertTrue(cruiser.okToPlaceShipAt(0, 0, false, ocean),
                "Should be able to place the ship at the beginning of an empty ocean.");
        assertTrue(destroyer.okToPlaceShipAt(0, 0, false, ocean),
                "Should be able to place the ship at the beginning of an empty ocean.");
        assertTrue(submarine.okToPlaceShipAt(0, 0, false, ocean),
                "Should be able to place the ship at the beginning of an empty ocean.");

    }

    @Test
    void testOkToPlaceShipAtBoundary() {
        assertFalse(battleship.okToPlaceShipAt(0, 8, true, ocean),
                "Should not be able to place a battleship horizontally at column 8 of an empty ocean.");
        assertFalse(cruiser.okToPlaceShipAt(0, 8, true, ocean),
                "Should not be able to place a cruiser horizontally at column 8 of an empty ocean.");
        assertTrue(destroyer.okToPlaceShipAt(0, 8, true, ocean),
                "Should be able to place a destroyer horizontally at column 8 of an empty ocean.");
        assertTrue(submarine.okToPlaceShipAt(0, 8, true, ocean),
                "Should be able to place a submarine horizontally at column 8 of an empty ocean.");

        assertFalse(battleship.okToPlaceShipAt(8, 0, false, ocean),
                "Should not be able to place a battleship vertically at column 8 of an empty ocean.");
        assertFalse(cruiser.okToPlaceShipAt(8, 0, false, ocean),
                "Should not be able to place a cruiser vertically at column 8 of an empty ocean.");
        assertTrue(destroyer.okToPlaceShipAt(8, 0, false, ocean),
                "Should be able to place a destroyer vertically at column 8 of an empty ocean.");
        assertTrue(submarine.okToPlaceShipAt(9, 0, false, ocean),
                "Should be able to place a submarine vertically at column 8 of an empty ocean.");
    }

    @Test
    void testOkToPlaceShipNearOtherShip() {
        Ship anotherShip = new Destroyer(); // Assuming Destroyer is another subclass of Ship
        anotherShip.placeShipAt(5, 5, true, ocean); // Place another ship at (5, 5)

        assertFalse(destroyer.okToPlaceShipAt(5, 4, true, ocean),
                "Should not be able to place the ship horizontally next to another ship.");
        assertFalse(destroyer.okToPlaceShipAt(6, 6, true, ocean),
                "Should not be able to place the ship diagonally next to another ship.");
    }

    @Test
    void testPlaceShipHorizontally(){
        battleship.placeShipAt(3, 2, true, ocean);

        assertEquals(3, battleship.getBowRow(), "Bow row should be set to 3");
        assertEquals(2, battleship.getBowColumn(), "Bow column should be set to 2");
        assertTrue(battleship.isHorizontal(), "Ship should be horizontal");

        for (int i = 0; i < battleship.getLength(); i++) {
            assertSame(battleship, ocean.ships[3][2 + i],
                    "Ship should be placed in the ocean grid at the correct horizontal position.");
        }
    }

    @Test
    void testPlaceShipVertically() {
        battleship.placeShipAt(5, 4, false, ocean);

        assertEquals(5, battleship.getBowRow(), "Bow row should be set to 5");
        assertEquals(4, battleship.getBowColumn(), "Bow column should be set to 4");
        assertFalse(battleship.isHorizontal(), "Ship should be vertical");

        for (int i = 0; i < battleship.getLength(); i++) {
            assertSame(battleship, ocean.ships[5 + i][4],
                    "Ship should be placed in the ocean grid at the correct vertical position.");
        }
    }

    @Test
    void testIsSunkAllPartsHit(){
        Arrays.fill(battleship.hit, true);
        assertTrue( battleship.isSunk(),"Ship should be sunk when all parts are hit");

        Arrays.fill(cruiser.hit, true);
        assertTrue(cruiser.isSunk(),"Ship should be sunk when all parts are hit");

        Arrays.fill(destroyer.hit, true);
        assertTrue(destroyer.isSunk(),"Ship should be sunk when all parts are hit");

        Arrays.fill(submarine.hit, true);
        assertTrue(submarine.isSunk(),"Ship should be sunk when all parts are hit");

    }

    @Test
    void testIsSunkNotAllPartsHit(){
        Arrays.fill(battleship.hit, true);
        battleship.hit[0] = false;
        assertFalse(battleship.isSunk(),"Ship should not be sunk when some parts are not hit");

        Arrays.fill(cruiser.hit, true);
        cruiser.hit[0] = false;
        assertFalse(cruiser.isSunk(),"Ship should not be sunk when some parts are not hit");

        Arrays.fill(destroyer.hit, true);
        destroyer.hit[0] = false;
        assertFalse(destroyer.isSunk(),"Ship should not be sunk when some parts are not hit");

        Arrays.fill(submarine.hit, true);
        submarine.hit[0] = false;
        assertFalse(submarine.isSunk(),"Ship should not be sunk when some parts are not hit");
    }

    @Test

    void testShootAtHit(){
        assertTrue(battleship.shootAt(3, 4),"Shoot should be successful");
        assertTrue(battleship.getHit()[0],"Part of the ship should be hit");

        assertTrue(cruiser.shootAt(3, 4),"Shoot should be successful");
        assertTrue(cruiser.getHit()[0],"Part of the ship should be hit");

        assertTrue(destroyer.shootAt(3, 4),"Shoot should be successful");
        assertTrue(destroyer.getHit()[0],"Part of the ship should be hit");

        assertTrue(submarine.shootAt(3, 4),"Shoot should be successful");
        assertTrue(submarine.getHit()[0],"Part of the ship should be hit");
    }
    @Test
    void testShootAtMiss(){
        assertFalse(battleship.shootAt(9, 9),"Shoot should miss" );
        assertFalse(cruiser.shootAt(8, 8),"Shoot should miss" );
        assertFalse(destroyer.shootAt(7, 7),"Shoot should miss" );
        assertFalse(submarine.shootAt(6, 6),"Shoot should miss" );
    }

    @Test
    void testShootAtWhenSunk() {
        // Sink the ship first
        Arrays.fill(battleship.hit, true);
        assertFalse(battleship.shootAt(3, 4),"Should not be able to shoot at a sunk ship");

        Arrays.fill(cruiser.hit, true);
        assertFalse(cruiser.shootAt(3, 4),"Should not be able to shoot at a sunk ship");

        Arrays.fill(destroyer.hit, true);
        assertFalse(destroyer.shootAt(3, 4),"Should not be able to shoot at a sunk ship");

        Arrays.fill(submarine.hit, true);
        assertFalse(submarine.shootAt(3, 4),"Should not be able to shoot at a sunk ship");
    }

    @Test

    void testGetHitBefore(){
        battleship.hit = new boolean[battleship.getLength()];
        boolean[] hitArray = battleship.getHit();
        for (boolean hit : hitArray) {
            assertFalse(hit, "Initially, no part of the ship should be hit");
        }
    }

    @Test
    void testGetHitAfter(){
        battleship.shootAt(3, 4); // Adjust parameters based on your ship's setup
        boolean[] hitArray = battleship.getHit();
        assertTrue(hitArray[0],"The first part of the ship should be hit");
        // Check the rest of the parts are not hit
        for (int i = 1; i < hitArray.length; i++) {
            assertFalse(hitArray[i],"Other parts of the ship should not be hit");
        }
    }


}


