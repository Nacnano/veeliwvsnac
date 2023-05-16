package game;

import java.util.ArrayList;
import java.util.Map;

import config.Config;
import entity.building.BaseBuilding;
import entity.building.Buildable;
import entity.building.Field;
import entity.building.House;
import entity.building.MilitaryCamp;
import entity.building.Mine;
import entity.building.Resource;
import entity.building.Sawmill;
import entity.building.Smelter;
import entity.unit.BaseUnit;

public class GameLogic {
	private int day, boardWidth, boardHeight, wave, period;
	private int wood, stone, ironOre, iron, money, food;
	
//  Turn these into functions instead
//	private int jumberjack, miner, farmer, maxPopulation;
	
//	Add a new variable
	private int currentPopulation;
	
	private Map<BaseUnit, Position> ourUnits, enemyUnits;
	private ArrayList<Position> unemployed;
	// private Map<Terrain, Position> map;
	private Map<Position, Terrain> map;
	private boolean[][] territory;
	// private Map<BaseBuilding, Position> buildings;	
	private Map<Position, BaseBuilding> buildings;
	
	// Functions for buildings
	
//  Maybe this one is not necessary since there is BuildBuilding below
//	public void addBuilding(BaseBuilding b, Position p) {
//		if (b.canBuildOn(map.get(p)) && !buildings.containsKey(p)) {
//			buildings.put(p, b);
//		}
//	}
	
	public void removeBuilding(Position p) {
		BaseBuilding b = buildings.get(p);
		buildings.remove(p);

		if (b instanceof Field) {
			currentPopulation -= ((Field) b).getCurrentPeople() * ((Field) b).getFatalityRate();
		}
		else if (b instanceof Mine) {
			currentPopulation -= ((Mine) b).getCurrentPeople() * ((Mine) b).getFatalityRate();
		}
		else if (b instanceof Sawmill) {
			currentPopulation -= ((Sawmill) b).getCurrentPeople() * ((Sawmill) b).getFatalityRate();
		}
		else if (b instanceof Smelter) {
			currentPopulation -= ((Smelter) b).getCurrentPeople() * ((Smelter) b).getFatalityRate();
		}
		else if (b instanceof House)  {
//			Guess it does not have to do anything
//			currentPopulation = Math.min(currentPopulation, getMaxPopulation());
		}
		else if (b instanceof MilitaryCamp) {
//			Not sure about military unit yet
//			It also has fatality rate
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
	
	public int getMaxPopulation() {
		int countHouse = 0;
		for (BaseBuilding b : buildings.values()) {
			if (b instanceof House) 
				countHouse++;
		}
		return Config.HOUSE_MAX_PEOPLE * countHouse;
	}
	
	public int getUnemployed() {
		int sumEmployed = 0;
		for (BaseBuilding b : buildings.values()) {
			if (b instanceof Resource) 
				sumEmployed += ((Resource) b).getCurrentPeople();
		}
		return currentPopulation - sumEmployed;
	}
	
	// Functions for game flow
	
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
	
	public void updateCurrentPopulation() {
		if (currentPopulation >= getMaxPopulation()) return;
		int newPopulation = currentPopulation + (int) (currentPopulation * Config.HOUSE_BORN_RATE);
		currentPopulation = Math.min(getMaxPopulation(), newPopulation);
	}
	
	private boolean hasEnoughMaterial(BaseBuilding b) {
		if (b instanceof Field) {
			return money >= Config.FIELD_REQUIRE_MONEY &&
				   wood >= Config.FIELD_REQUIRE_WOOD &&
				   stone >= Config.FILED_REQUIRE_STONE &&
				   iron >= Config.FIELD_REQUIRE_IRON;
		}
		else if (b instanceof Mine) {
			return money >= Config.MINE_REQUIRE_MONEY &&
					   wood >= Config.MINE_REQUIRE_WOOD &&
					   stone >= Config.MINE_REQUIRE_STONE &&
					   iron >= Config.MINE_REQUIRE_IRON;
		}
		else if (b instanceof Sawmill) {
			return money >= Config.SAWMILL_REQUIRE_MONEY &&
					   wood >= Config.SAWMILL_REQUIRE_WOOD &&
					   stone >= Config.SAWMILL_REQUIRE_STONE &&
					   iron >= Config.SAWMILL_REQUIRE_IRON;
		}
		else if (b instanceof Smelter) {
			return money >= Config.SMELTER_REQUIRE_MONEY &&
					   wood >= Config.SMELTER_REQUIRE_WOOD &&
					   stone >= Config.SMELTER_REQUIRE_STONE &&
					   iron >= Config.SMELTER_REQUIRE_IRON;
		}
		else if (b instanceof House) {
			return money >= Config.HOUSE_REQUIRE_MONEY &&
					   wood >= Config.HOUSE_REQUIRE_WOOD &&
					   stone >= Config.HOUSE_REQUIRE_STONE &&
					   iron >= Config.HOUSE_REQUIRE_IRON;
		}
		else if (b instanceof MilitaryCamp) {
			return money >= Config.MILITARYCAMP_REQUIRE_MONEY &&
					   wood >= Config.MILITARYCAMP_REQUIRE_WOOD &&
					   stone >= Config.MILITARYCAMP_REQUIRE_STONE &&
					   iron >= Config.MILITARYCAMP_REQUIRE_IRON;
		}
		else return false;
	}
	
	private void deductMaterial(BaseBuilding b) {
		if (b instanceof Field) {
			money -= Config.FIELD_REQUIRE_MONEY;
			wood -= Config.FIELD_REQUIRE_WOOD;
			stone -= Config.FILED_REQUIRE_STONE;
			iron -= Config.FIELD_REQUIRE_IRON;
		}
		else if (b instanceof Mine) {
			money -= Config.MINE_REQUIRE_MONEY;
			wood -= Config.MINE_REQUIRE_WOOD;
			stone -= Config.MINE_REQUIRE_STONE;
			iron -= Config.MINE_REQUIRE_IRON;
		}
		else if (b instanceof Sawmill) {
			money -= Config.SAWMILL_REQUIRE_MONEY;
			wood -= Config.SAWMILL_REQUIRE_WOOD;
			stone -= Config.SAWMILL_REQUIRE_STONE;
			iron -= Config.SAWMILL_REQUIRE_IRON;
		}
		else if (b instanceof Smelter) {
			money -= Config.SMELTER_REQUIRE_MONEY;
			wood -= Config.SMELTER_REQUIRE_WOOD;
			stone -= Config.SMELTER_REQUIRE_STONE;
			iron -= Config.SMELTER_REQUIRE_IRON;
		}
		else if (b instanceof House) {
			money -= Config.HOUSE_REQUIRE_MONEY;
			wood -= Config.HOUSE_REQUIRE_WOOD;
			stone -= Config.HOUSE_REQUIRE_STONE;
			iron -= Config.HOUSE_REQUIRE_IRON;
		}
		else if (b instanceof MilitaryCamp) {
			money -= Config.MILITARYCAMP_REQUIRE_MONEY;
			wood -= Config.MILITARYCAMP_REQUIRE_WOOD;
			stone -= Config.MILITARYCAMP_REQUIRE_STONE;
			iron -= Config.MILITARYCAMP_REQUIRE_IRON;
		}
	}
	
	public void buildBuilding(BaseBuilding b, Position p) {
		if (!b.canBuildOn(map.get(p))) return;
		if (buildings.containsKey(p)) return;
		if (!hasEnoughMaterial(b)) return;
		
		deductMaterial(b);
		buildings.put(p, b);
	}
	
	public void saleMaterial(Material m, int amount) {
		if (m == Material.FOOD && food >= amount) {
			food -= amount;
			money += Config.FOOD_PRICE * amount;
		}
		else if (m == Material.WOOD && wood >= amount) {
			wood -=  amount;
			money += Config.WOOD_PRICE * amount;
		} 
		else if (m == Material.STONE && stone >= amount) {
			stone -= amount;
			money += Config.STONE_PRICE * amount;
		}
		else if (m == Material.IRON && iron >= amount) {
			iron -= amount;
			money += Config.IRON_PRICE * amount;
		}
	}
}
