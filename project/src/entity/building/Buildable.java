package entity.building;

import entity.unit.BaseUnit;
import game.Position;
import game.Terrain;

/**
 * This interface defines methods for {@link BaseBuilding} that can 
 * build on a {@link Terrain}
 */
public interface Buildable {

	/**
	 * This methods is called when {@link BaseUnit} is moving to specific
	 * {@link Position}.
	 * 
	 * @param t The terrain which building will be building on
	 * @return true If this building can build on the terrain 
	 * otherwise false
	 */
	public boolean canBuildOn(Terrain t);
}
