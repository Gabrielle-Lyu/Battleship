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
    }

    @Test
    void testBattleshipConstructor(){
        assertEquals(4,battleship.getLength(),"Battleship length should be 4");
    }

    @Test
    void testGetShipType(){
        assertEquals("Battleship", battleship.getShipType(), "getShipType should return 'Battleship'");
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


