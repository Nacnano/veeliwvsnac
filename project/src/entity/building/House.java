package entity.building;

import game.Terrain;

public class House extends Resource {

	public House(int durability) {
		super(durability, 0, 0);
	}

	public boolean canBuildOn(Terrain t) {
		return t == Terrain.PLAIN;
	}

}
