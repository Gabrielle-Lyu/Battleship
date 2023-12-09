import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        cruiser = new Cruiser();
        submarine = new Submarine();
        destroyer = new Destroyer();
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
        battleship.setBowRow(3);
        battleship.setBowColumn(4);
        battleship.setHorizontal(true);

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

}


