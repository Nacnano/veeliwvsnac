package entity.building;

import config.Confic;
import game.Terrain;

public class Mine extends Resource {

	public Mine() {
		super(Confic.MINE_DURABILITY, Confic.MINE_MAX_PEOPLE, Confic.MINE_FATALITY_RATE);
	}

	public boolean canBuildOn(Terrain t) {
		return t == Terrain.PLAIN || t == Terrain.MOUNTAIN;
	}

}
