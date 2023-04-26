package entity.building;

import game.Terrain;

public class House extends BaseBuilding {

	public House(int durability) {
		super(durability);
	}

	public boolean canBuildOn(Terrain t) {
		return t == Terrain.PLAIN;
	}

}
