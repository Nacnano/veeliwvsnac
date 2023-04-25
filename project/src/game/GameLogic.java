package game;

import java.util.ArrayList;
import java.util.Map;

import entity.building.BaseBuilding;
import entity.unit.BaseUnit;

public class GameLogic {
	private int day, boardWidth, boardHeight, wave, period;
	private int wood, stone, ironOre, iron, money, food;
	private int jumberjack, miner, farmer, maxPopulation;
	private Map<BaseUnit, Position> ourUnits, enemyUnits;
	private ArrayList<Position> unemployed;
	// private Map<Terrain, Position> map;
	private Map<Position, Terrain> map;
	private boolean[][] territory;
	private Map<BaseBuilding, Position> buildings;	
	
	public void addBuilding(BaseBuilding building, Position position) {
		if (building.canBuildOn(map.get(position))) {
			buildings.put(building, position);
		}
	}
	
	public void removeBuilding(BaseBuilding building) {
		buildings.remove(building);
	}
}
