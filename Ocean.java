import javax.management.BadAttributeValueExpException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * This class manages the game state by keeping track of what entity is
 * contained in each position on the game board.
 * 
 * @author harry
 *
 */
public class Ocean implements OceanInterface {

	/**
	 * A 10x10 2D array of Ships, which can be used to quickly determine which ship
	 * is in any given location.
	 */
	protected Ship[][] ships;

	/**
	 * The total number of shots fired by the user
	 */
	protected int shotsFired;

	/**
	 * The number of times a shot hit a ship. If the user shoots the same part of a
	 * ship more than once, every hit is counted, even though the additional "hits"
	 * don't do the user any good.
	 */
	protected int hitCount;

	/**
	 * The number of ships totally sunk.
	 */
	protected int shipsSunk;

	private final boolean[][] hasFired;

	/**
	 * Creates an "empty" ocean, filling every space in the <code>ships</code> array
	 * with EmptySea objects. Should also initialize the other instance variables
	 * appropriately.
	 */
	public Ocean() {
		ships = new Ship[10][10];
		emptyMap();

		hasFired = new boolean[10][10];

		shotsFired = 0;
		hitCount = 0;
		shipsSunk = 0;

	}

	/**
	 * Place all ten ships randomly on the (initially empty) ocean. Larger ships
	 * must be placed before smaller ones to avoid cases where it may be impossible
	 * to place the larger ships.
	 *
	 * @see java.util.Random
	 */
	public void placeAllShipsRandomly() {
		ArrayList<Ship> fleet = createFleet();

//		current position of the fleet list
		int position = 0;
//		fail count of placing the current ship
		int currentFailCnt = 0;
//		create random object
		Random rand = new Random();

//		when there's still ship to be placed in the fleet
		while (position < fleet.size()) {

//			set a breakout point at 30 -> if tried to place a ship for >30 times and still cannot place,
//			empty the whole map and redo the placement.
			if (currentFailCnt >= 30) {
				emptyMap();
				position = 0;
				currentFailCnt = 0;
				continue;
			}

//			select a random coordinate and horizontal to place the next ship in the fleet
			int row = rand.nextInt(10);
			int col = rand.nextInt(10);
			boolean horizontal = rand.nextBoolean();


			if (fleet.get(position).okToPlaceShipAt(row, col, horizontal, this)) {
				fleet.get(position).placeShipAt(row, col, horizontal, this);
				position++;
				currentFailCnt = 0;
				continue;
			}
//			if not ok to place ship at this position, regenerate a position
			currentFailCnt++;
		}
	}

	private void emptyMap() {
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				ships[i][j] = new EmptySea();
			}
		}
	}

	private void addShipsToFleet(ArrayList<Ship> fleet, Class<? extends Ship> shipType, int number) {
		try {
			for (int i = 0; i < number; i++) {
				fleet.add(shipType.getDeclaredConstructor().newInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Ship> createFleet() {
		ArrayList<Ship> fleet = new ArrayList<>();
		fleet.add(new Battleship());
		addShipsToFleet(fleet, Cruiser.class, 2);
		addShipsToFleet(fleet, Destroyer.class, 3);
		addShipsToFleet(fleet, Submarine.class, 4);
		return fleet;
	}


	/**
	 * Checks if this coordinate is not empty; that is, if this coordinate does not
	 * contain an EmptySea reference.
	 *
	 * @param row    the row (0 to 9) in which to check for a floating ship
	 * @param column the column (0 to 9) in which to check for a floating ship
	 * @return {@literal true} if the given location contains a ship, and
	 * {@literal false} otherwise.
	 */
	public boolean isOccupied(int row, int column) {
		return !(ships[row][column] instanceof EmptySea);
	}

	/**
	 * Fires a shot at this coordinate. This will update the number of shots that
	 * have been fired (and potentially the number of hits, as well). If a location
	 * contains a real, not sunk ship, this method should return {@literal true}
	 * every time the user shoots at that location. If the ship has been sunk,
	 * additional shots at this location should return {@literal false}.
	 *
	 * @param row    the row (0 to 9) in which to shoot
	 * @param column the column (0 to 9) in which to shoot
	 * @return {@literal true} if the given location contains an afloat ship (not an
	 * EmptySea), {@literal false} if it does not.
	 */
	public boolean shootAt(int row, int column) {
		shotsFired++;
		hasFired[row][column] = true;
		Ship target = ships[row][column];

		if (target instanceof EmptySea) {
			return false;
		} else {
			boolean isSunkBefore = target.isSunk();
			boolean hit = target.shootAt(row, column);
			if (hit) {
				hitCount++;
				if(!isSunkBefore && target.isSunk()){
					shipsSunk++;
				}
			}
			return hit;
		}
	}

	/**
	 * @return the number of shots fired in this game.
	 */
	public int getShotsFired() {
		return this.shotsFired;
	}

	/**
	 * @return the number of hits recorded in this game.
	 */
	public int getHitCount() {
		return this.hitCount;
	}

	/**
	 * @return the number of ships sunk in this game.
	 */
	public int getShipsSunk() { return shipsSunk; }

	/**
	 * @return {@literal true} if all ships have been sunk, otherwise
	 * {@literal false}.
	 */
	public boolean isGameOver() {
		return getShipsSunk() == 10;
	}

	/**
	 * Provides access to the grid of ships in this Ocean. The methods in the Ship
	 * class that take an Ocean parameter must be able to read and even modify the
	 * contents of this array. While it is generally undesirable to allow methods in
	 * one class to directly access instance variables in another class, in this
	 * case there is no clear and elegant alternatives.
	 *
	 * @return the 10x10 array of ships.
	 */
	public Ship[][] getShipArray() {
		return ships;
	}

	/**
	 * Prints the ocean. To aid the user, row numbers should be displayed along the
	 * left edge of the array, and column numbers should be displayed along the top.
	 * Numbers should be 0 to 9, not 1 to 10. The top left corner square should be
	 * 0, 0.
	 * <ul>
	 * <li>Use 'S' to indicate a location that you have fired upon and hit a (real)
	 * ship</li>
	 * <li>'-' to indicate a location that you have fired upon and found nothing
	 * there</li>
	 * <li>'x' to indicate a location containing a sunken ship</li>
	 * <li>'.' (a period) to indicate a location that you have never fired
	 * upon.</li>
	 * </ul>
	 * <p>
	 * This is the only method in Ocean that has any printing capability, and it
	 * should never be called from within the Ocean class except for the purposes of
	 * debugging.
	 */
	public void print() {
//		print column numbers
		System.out.print("   ");

		for (int i = 0; i < ships[0].length; i++) {
			System.out.print(i + "  ");
		}

		System.out.println();

//		print row numbers and rows
		for (int row = 0; row < ships.length; row++) {
			System.out.print(row + "  ");
			for (int col = 0; col < ships[row].length; col++) {
				if (ships[row][col].isSunk()) {
					System.out.print("x  ");
				} else {
					// Calculate the index for the hit array
					int index = ships[row][col].isHorizontal() ? col - ships[row][col].getBowColumn() : row - ships[row][col].getBowRow();

					// Check if the index is within the bounds and the part has been hit
					if (index >= 0 && index < ships[row][col].getLength() && ships[row][col].getHit()[index]) {
						System.out.print("S  ");
					} else if (hasFired[row][col]) {
						System.out.print("-  ");
					} else {
						System.out.print(".  ");
					}
					}
				}
			System.out.println();
			}

	}

}
