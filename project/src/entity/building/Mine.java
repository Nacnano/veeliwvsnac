package entity.building;

import game.Terrain;

public class Mine extends Resource {

	public Mine(int durability) {
		super(durability, 0, 0);
	}

	public boolean canBuildOn(Terrain t) {
		return t == Terrain.PLAIN || t == Terrain.MOUNTAIN;
	}

}
