package entity.unit;

import entity.building.BaseBuilding;

/**
 * This interface defines methods for {@link BaseUnit} that can attack another
 * unit.
 */
public interface Attackable {

	/**
	 * This method is called when this unit attack another unit.
	 * 
	 * @param u the unit which this entity attack to
	 * 
	 */
	public void attack(BaseUnit u);
	
	/**
	 * This method is called when this unit attack another building.
	 * 
	 * @param b the building which this entity attack to
	 * 
	 */
	public void destroy(BaseBuilding b);
}
