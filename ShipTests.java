import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ShipTests {
    private Cruiser cruiser;
    private Destroyer destroyer;
    private Submarine submarine;
    private Battleship battleship;

    @BeforeEach
    public void init(){
        battleship = new Battleship();
        cruiser = new Cruiser();
        submarine = new Submarine();
        destroyer = new Destroyer();
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
}


