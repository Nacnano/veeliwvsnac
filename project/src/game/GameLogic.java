package game;

import java.util.ArrayList;
import java.util.Map;

import config.Config;
import entity.building.BaseBuilding;
import entity.building.Buildable;
import entity.building.Field;
import entity.building.Mine;
import entity.building.Resource;
import entity.building.Sawmill;
import entity.building.Smelter;
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
	// private Map<BaseBuilding, Position> buildings;	
	private Map<Position, BaseBuilding> buildings;
	
	public void addBuilding(BaseBuilding b, Position p) {
		if (b.canBuildOn(map.get(p)) && !buildings.containsKey(p)) {
			buildings.put(p, b);
		}
	}
	
	public void removeBuilding(Position p) {
		buildings.remove(p);
	}
	
	public void updateResources() {
		for (BaseBuilding b : buildings.values()) {
			int currentPeople = ((Resource) b).getCurrentPeople();
			
			if (b instanceof Field) {
				food += Config.FIELD_WORK_RATE * currentPeople;
			}
			else if (b instanceof Mine) {
				stone += Config.MINE_WORK_RATE * currentPeople;
				ironOre += Config.MINE_WORK_RATE * currentPeople;
			}
			else if (b instanceof Sawmill) {
				wood += Config.SAWMILL_WORK_RATE * currentPeople;
			}
			else if (b instanceof Smelter) {
				iron += Config.SMELTER_WORK_RATE * currentPeople;
			}
		}
	}
	
	public void QuitJob(Position p, int amount) {
		int currentPeople = ((Resource) buildings.get(p)).getCurrentPeople();
		if (currentPeople >= amount) {
			((Resource) buildings.get(p)).setCurrentPeople(currentPeople - amount);
		}
	}
	
	public void EnrollJob(Position p, int amount) {
		int currentPeople = ((Resource) buildings.get(p)).getCurrentPeople();
		int maxPeople = ((Resource) buildings.get(p)).getMaxPeople();
		if (currentPeople + amount <= maxPeople) {
			((Resource) buildings.get(p)).setCurrentPeople(currentPeople + amount);
		}
	}
	
	public void buildBuilding(Position p) {
		// How to make parameter which building to build
	}
	
	public void moveBuilding(Position p1, Position p2) {
		BaseBuilding b = buildings.get(p1);
		if (b.canBuildOn(map.get(p2)) && !buildings.containsKey(p2)) {
			buildings.remove(p1);
			buildings.put(p2, b);
		}
	}
	
	public void attackUnit(BaseUnit attacker, BaseUnit attacked) {
		attacker.attack(attacked);
	}
	
	public void moveUnit(BaseUnit unit, Position destination) {
		if(ourUnits.containsKey(unit)) {
			ourUnits.put(unit, destination);
		}
		else if(enemyUnits.containsKey(unit)) {
			enemyUnits.put(unit, destination);
		}
	}
}
