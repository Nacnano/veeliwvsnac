package entity.building;

import config.Config;
import entity.unit.BaseUnit;
import entity.unit.SwordMan;
import game.Terrain;

public class MilitaryCamp extends BaseBuilding {
	
	private BaseUnit military;
	
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
		if (military != null) return;
		military = new SwordMan();
	}
	
	public void heal() {
		// full pack
		if (military == null) return;
		military.setPeople(Config.MILITARY_SIZE);
	}
}
