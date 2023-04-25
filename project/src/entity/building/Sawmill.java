package entity.building;

import game.Terrain;

public class Sawmill extends Resource {

	public Sawmill(int durability) {
		super(durability, 0, 0);
	}

	public boolean canBuildOn(Terrain t) {
		return t == Terrain.PLAIN || t == Terrain.FOREST;
	}

}
