package game;

import java.util.ArrayList;
import java.util.Map;

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
import utils.GameConfig;

public class GameLogic {
	private static int day, boardWidth, boardHeight, wave, period;
	private static int wood, stone, ironOre, iron, money, food;
	
//  Turn these into functions instead
//	private int jumberjack, miner, farmer, maxPopulation;
	
//	Add a new variable
	private static int currentPopulation;
	
	private static Map<BaseUnit, Position> ourUnits, enemyUnits;
	private static ArrayList<Position> unemployed;
	// private Map<Terrain, Position> map;
	private static Map<Position, Terrain> map;
	private static boolean[][] territory;
	// private Map<BaseBuilding, Position> buildings;	
	private static Map<Position, BaseBuilding> buildings;
	
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
		return GameConfig.HOUSE_MAX_PEOPLE * countHouse;
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
				food += GameConfig.FIELD_WORK_RATE * currentPeople;
			}
			else if (b instanceof Mine) {
				stone += GameConfig.MINE_WORK_RATE * currentPeople;
				ironOre += GameConfig.MINE_WORK_RATE * currentPeople;
			}
			else if (b instanceof Sawmill) {
				wood += GameConfig.SAWMILL_WORK_RATE * currentPeople;
			}
			else if (b instanceof Smelter) {
				iron += GameConfig.SMELTER_WORK_RATE * currentPeople;
			}
		}
	}
	
	public void updateCurrentPopulation() {
		if (currentPopulation >= getMaxPopulation()) return;
		int newPopulation = currentPopulation + (int) (currentPopulation * GameConfig.HOUSE_BORN_RATE);
		currentPopulation = Math.min(getMaxPopulation(), newPopulation);
	}
	
	private boolean hasEnoughMaterial(BaseBuilding b) {
		if (b instanceof Field) {
			return money >= GameConfig.FIELD_REQUIRE_MONEY &&
				   wood >= GameConfig.FIELD_REQUIRE_WOOD &&
				   stone >= GameConfig.FILED_REQUIRE_STONE &&
				   iron >= GameConfig.FIELD_REQUIRE_IRON;
		}
		else if (b instanceof Mine) {
			return money >= GameConfig.MINE_REQUIRE_MONEY &&
					   wood >= GameConfig.MINE_REQUIRE_WOOD &&
					   stone >= GameConfig.MINE_REQUIRE_STONE &&
					   iron >= GameConfig.MINE_REQUIRE_IRON;
		}
		else if (b instanceof Sawmill) {
			return money >= GameConfig.SAWMILL_REQUIRE_MONEY &&
					   wood >= GameConfig.SAWMILL_REQUIRE_WOOD &&
					   stone >= GameConfig.SAWMILL_REQUIRE_STONE &&
					   iron >= GameConfig.SAWMILL_REQUIRE_IRON;
		}
		else if (b instanceof Smelter) {
			return money >= GameConfig.SMELTER_REQUIRE_MONEY &&
					   wood >= GameConfig.SMELTER_REQUIRE_WOOD &&
					   stone >= GameConfig.SMELTER_REQUIRE_STONE &&
					   iron >= GameConfig.SMELTER_REQUIRE_IRON;
		}
		else if (b instanceof House) {
			return money >= GameConfig.HOUSE_REQUIRE_MONEY &&
					   wood >= GameConfig.HOUSE_REQUIRE_WOOD &&
					   stone >= GameConfig.HOUSE_REQUIRE_STONE &&
					   iron >= GameConfig.HOUSE_REQUIRE_IRON;
		}
		else if (b instanceof MilitaryCamp) {
			return money >= GameConfig.MILITARYCAMP_REQUIRE_MONEY &&
					   wood >= GameConfig.MILITARYCAMP_REQUIRE_WOOD &&
					   stone >= GameConfig.MILITARYCAMP_REQUIRE_STONE &&
					   iron >= GameConfig.MILITARYCAMP_REQUIRE_IRON;
		}
		else return false;
	}
	
	private void deductMaterial(BaseBuilding b) {
		if (b instanceof Field) {
			money -= GameConfig.FIELD_REQUIRE_MONEY;
			wood -= GameConfig.FIELD_REQUIRE_WOOD;
			stone -= GameConfig.FILED_REQUIRE_STONE;
			iron -= GameConfig.FIELD_REQUIRE_IRON;
		}
		else if (b instanceof Mine) {
			money -= GameConfig.MINE_REQUIRE_MONEY;
			wood -= GameConfig.MINE_REQUIRE_WOOD;
			stone -= GameConfig.MINE_REQUIRE_STONE;
			iron -= GameConfig.MINE_REQUIRE_IRON;
		}
		else if (b instanceof Sawmill) {
			money -= GameConfig.SAWMILL_REQUIRE_MONEY;
			wood -= GameConfig.SAWMILL_REQUIRE_WOOD;
			stone -= GameConfig.SAWMILL_REQUIRE_STONE;
			iron -= GameConfig.SAWMILL_REQUIRE_IRON;
		}
		else if (b instanceof Smelter) {
			money -= GameConfig.SMELTER_REQUIRE_MONEY;
			wood -= GameConfig.SMELTER_REQUIRE_WOOD;
			stone -= GameConfig.SMELTER_REQUIRE_STONE;
			iron -= GameConfig.SMELTER_REQUIRE_IRON;
		}
		else if (b instanceof House) {
			money -= GameConfig.HOUSE_REQUIRE_MONEY;
			wood -= GameConfig.HOUSE_REQUIRE_WOOD;
			stone -= GameConfig.HOUSE_REQUIRE_STONE;
			iron -= GameConfig.HOUSE_REQUIRE_IRON;
		}
		else if (b instanceof MilitaryCamp) {
			money -= GameConfig.MILITARYCAMP_REQUIRE_MONEY;
			wood -= GameConfig.MILITARYCAMP_REQUIRE_WOOD;
			stone -= GameConfig.MILITARYCAMP_REQUIRE_STONE;
			iron -= GameConfig.MILITARYCAMP_REQUIRE_IRON;
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
			money += GameConfig.FOOD_PRICE * amount;
		}
		else if (m == Material.WOOD && wood >= amount) {
			wood -=  amount;
			money += GameConfig.WOOD_PRICE * amount;
		} 
		else if (m == Material.STONE && stone >= amount) {
			stone -= amount;
			money += GameConfig.STONE_PRICE * amount;
		}
		else if (m == Material.IRON && iron >= amount) {
			iron -= amount;
			money += GameConfig.IRON_PRICE * amount;
		}
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
	
	public static void moveUnit(BaseUnit unit, Position destination) {
		if(ourUnits.containsKey(unit)) {
			ourUnits.put(unit, destination);
		}
		else if(enemyUnits.containsKey(unit)) {
			enemyUnits.put(unit, destination);
		}
	}
	
	public static boolean militaryIsInCamp(MilitaryCamp camp, BaseUnit unit) {
		if (!ourUnits.containsKey(unit)) return false;
		Position unitPos = ourUnits.get(unit);
		return buildings.get(unitPos) == camp;		
	} 
	
	public static boolean payToUpgrateMilitary() {
		if (money < GameConfig.MILLITARY_UPGRADE_PRICE) return false;
		money -= GameConfig.MILLITARY_UPGRADE_PRICE;
		return true;
	}
	
	public static void changeMilitary(BaseUnit unit_old, BaseUnit unit_new) {
		if (!ourUnits.containsKey(unit_old)) return;
		Position pos = ourUnits.get(unit_old);
		ourUnits.remove(unit_old);
		ourUnits.put(unit_new, pos);
	}
	
	public static Terrain getOurUnitTerrain(BaseUnit unit) {
		Position pos = ourUnits.get(unit);
		return map.get(pos);
	}
	
	public static void addOurUnit(BaseUnit unit, Position pos) {
		ourUnits.put(unit, pos);
	}
	
	public static boolean isGameOver() {
		return buildings.isEmpty();
	}
	
	// Getters and Setters for materials

	public static int getWood() {
		return wood;
	}

	public static void setWood(int wood) {
		GameLogic.wood = wood;
	}

	public static int getStone() {
		return stone;
	}

	public static void setStone(int stone) {
		GameLogic.stone = stone;
	}

	public static int getIronOre() {
		return ironOre;
	}

	public static void setIronOre(int ironOre) {
		GameLogic.ironOre = ironOre;
	}

	public static int getIron() {
		return iron;
	}

	public static void setIron(int iron) {
		GameLogic.iron = iron;
	}

	public static int getMoney() {
		return money;
	}

	public static void setMoney(int money) {
		GameLogic.money = money;
	}

	public static int getFood() {
		return food;
	}

	public static void setFood(int food) {
		GameLogic.food = food;
	}
	
	// Get number of workers
	
	public static int getNumberOfWorkers(String resourceName) {
		int cnt = 0;
		for (BaseBuilding b : buildings.values()) {
			if (!(b instanceof Resource)) continue;
			if (b.getClass().getSimpleName() == resourceName)
				cnt += ((Resource) b).getCurrentPeople();
		}
		return cnt;
	}
}
