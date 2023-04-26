package entity.building;

import game.Terrain;

import config.Confic;

public class Field extends Resource {
	
	public Field() {
		super(Confic.FIELD_DURABILITY, Confic.FIELD_MAX_PEOPLE, Confic.FIELD_FATALITY_RATE);
	}

	public boolean canBuildOn(Terrain t) {
		return t == Terrain.PLAIN;
	}
}
