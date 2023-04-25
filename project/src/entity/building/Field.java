package entity.building;

import game.Terrain;

public class Field extends Resource {
	
	public Field(int durability) {
		super(durability, 0, 0);
	}

	public boolean canBuildOn(Terrain t) {
		return t == Terrain.PLAIN;
	}
}
