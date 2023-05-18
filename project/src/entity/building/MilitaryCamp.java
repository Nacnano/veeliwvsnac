package entity.building;

import entity.unit.Archer;
import entity.unit.BaseUnit;
import entity.unit.FieldSwordMan;
import entity.unit.ForestSwordMan;
import entity.unit.MountainSwordMan;
import entity.unit.SwordMan;
import game.GameLogic;
import game.Terrain;
import utils.GameConfig;

public class MilitaryCamp extends BaseBuilding {
	
	private BaseUnit military;
	
	public MilitaryCamp() {
		super(GameConfig.MILITARYCAMP_DURABILITY);
	}
	
	public boolean canBuildOn(Terrain t) {
		return t != Terrain.WATER;
	}

	public void upgradeSwordMan() {
		// change role of military
		// check if military is in it in GameLogic
		if (!GameLogic.payToUpgrateMilitary()) return;
		Terrain terrain = GameLogic.getOurUnitTerrain(military);
		BaseUnit new_military = new SwordMan();
		if (terrain == Terrain.FOREST) 
			 new_military = new ForestSwordMan();
		else if (terrain == Terrain.MOUNTAIN)
			new_military = new MountainSwordMan();
		else if (terrain == Terrain.PLAIN)
			new_military = new FieldSwordMan();
		GameLogic.changeMilitary(military, new_military);
		military = new_military;
	}
	
	public void build(String militaryType) {
		// build a pack of soldiers
		if (military != null) return;
		if (militaryType == "SwordMan")
			military = new SwordMan();
		else if (militaryType == "Archer")
			military = new Archer();
//		GameLogic.addOurUnit(military, Position?);
	}
	
	public void heal() {
		// full pack
		if (military == null) return;
		if (!GameLogic.militaryIsInCamp(this, military)) return;
		military.setPeople(GameConfig.MILITARY_SIZE);
	}
}