package entity.building;

import game.Terrain;

public class MilitaryCamp extends BaseBuilding {
	
	public MilitaryCamp(int durability) {
		super(durability);
	}
	
	public boolean canBuildOn(Terrain t) {
		return t != Terrain.WATER;
	}

	public void upgrade() {
		// change role of military
	}
	
	public void build() {
		// ?
	}
	
	public void heal() {
		// full pack?
	}
}
