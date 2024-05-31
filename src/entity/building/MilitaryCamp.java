package entity.building;

import game.Terrain;
import utils.GameConfig;

/**
 * The MilitaryCamp class represent Military Camp
 *
 */
public class MilitaryCamp extends BaseBuilding {
	
	/**
	 * The constructor for this class
	 */
	public MilitaryCamp() {
		super(GameConfig.MILITARYCAMP_DURABILITY);
	}
	
	/**
	 * Check cannot build on water
	 * 
	 * @param t The terrain that the player wants to build on
	 * @return true if terrain is not water otherwise
	 * false
	 */
	public boolean canBuildOn(Terrain t) {
		return t != Terrain.WATER;
	}
	
}
