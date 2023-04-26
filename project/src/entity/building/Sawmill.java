package entity.building;

import config.Confic;
import game.Terrain;

public class Sawmill extends Resource {

	public Sawmill() {
		super(Confic.SAWMILL_DURABILITY, Confic.SAWMILL_MAX_PEOPLE, Confic.SAWMILL_FATALITY_RATE);
	}

	public boolean canBuildOn(Terrain t) {
		return t == Terrain.PLAIN || t == Terrain.FOREST;
	}

}
