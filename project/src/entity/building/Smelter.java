package entity.building;

import config.Confic;
import game.Terrain;

public class Smelter extends Resource {

	public Smelter() {
		super(Confic.SMELTER_DURABILITY, Confic.SMELTER_MAX_PEOPLE, Confic.SMELTER_FATALITY_RATE);
	}

	public boolean canBuildOn(Terrain t) {
		return t == Terrain.PLAIN || t == Terrain.MOUNTAIN;
	}

}
