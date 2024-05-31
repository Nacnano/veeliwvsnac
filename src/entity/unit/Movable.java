package entity.unit;

import game.Position;

/**
 * This interface defines methods for {@link BaseUnit} that can move to another
 * {@link Position}.
 */
public interface Movable {

	/**
	 * This methods is called when {@link BaseUnit} is moving to specific
	 * {@link Position}.
	 * 
	 * @param p The position which entity will be moving to
	 * 
	 */
	public void move(Position p);
}
