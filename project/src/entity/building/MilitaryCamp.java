package entity.building;

import config.Config;
import game.Terrain;

public class MilitaryCamp extends BaseBuilding {
	
	public MilitaryCamp() {
		super(Config.MILITARYCAMP_DURABILITY);
	}
	
	public boolean canBuildOn(Terrain t) {
		return t != Terrain.WATER;
	}

	public void upgrade() {
		// change role of military
		// check if military is in it in GameLogic
		
	}
	
	public void build() {
		// build a pack of soldiers
	}
	
	public void heal() {
		// full pack
	}
}
