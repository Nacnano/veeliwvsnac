package entity.building;

import game.Terrain;

public class Smelter extends Resource {

	public Smelter(int durability) {
		super(durability, 0, 0);
	}

	public boolean canBuildOn(Terrain t) {
		return t == Terrain.PLAIN || t == Terrain.MOUNTAIN;
	}

}
