package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.GameController;
import entity.building.BaseBuilding;
import entity.building.Field;
import entity.building.House;
import entity.building.MilitaryCamp;
import entity.building.Mine;
import entity.building.Resource;
import entity.building.Sawmill;
import entity.building.Smelter;
import entity.unit.Archer;
import entity.unit.BaseUnit;
import entity.unit.FieldSwordMan;
import entity.unit.ForestSwordMan;
import entity.unit.MountainSwordMan;
import entity.unit.SwordMan;
import utils.GameConfig;

public class GameLogic {
	private static int day, boardWidth, boardHeight, wave, period;
	private static int wood, stone, ironOre, iron, money, food;
	
//  Turn these into functions instead
//	private int jumberjack, miner, farmer, maxPopulation;
	
//	Add a new variable
	private static int currentPopulation;
	
	private static Map<BaseUnit, Position> ourUnits = new HashMap<>();
	private static Map<BaseUnit, Position> enemyUnits = new HashMap<>();
	private static ArrayList<Position> unemployed;
	// private Map<Terrain, Position> map;
	private static Map<Position, Terrain> map = new HashMap<>();
	private static int[][] territory = new int[GameConfig.getMapSize()][GameConfig.getMapSize()];
	private static int territoryCount = 0;
	// private Map<BaseBuilding, Position> buildings;	
	private static Map<Position, BaseBuilding> buildings = new HashMap<>();
	
	
	// Functions for buildings
	
//  Maybe this one is not necessary since there is BuildBuilding below
//	public void addBuilding(BaseBuilding b, Position p) {
//		if (b.canBuildOn(map.get(p)) && !buildings.containsKey(p)) {
//			buildings.put(p, b);
//		}
//	}
	
	
	public void removeBuilding(Position p) {
		BaseBuilding b = buildings.get(p);
		updateTerritory(b, p, -1);
		buildings.remove(p);
		GameController.getGameMap().get(p).setBuilding(null);

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
	
//	public static void QuitJob(Position p, int amount) {
//		if (amount < 0) return;
//		int currentPeople = ((Resource) buildings.get(p)).getCurrentPeople();
//		if (currentPeople >= amount) {
//			((Resource) buildings.get(p)).setCurrentPeople(currentPeople - amount);
//		}
//	}
//	
//	public static void EnrollJob(Position p, int amount) {
//		if (amount > getUnemployed() || amount < 0) return;
//		int currentPeople = ((Resource) buildings.get(p)).getCurrentPeople();
//		int maxPeople = ((Resource) buildings.get(p)).getMaxPeople();
//		if (currentPeople + amount <= maxPeople) {
//			((Resource) buildings.get(p)).setCurrentPeople(currentPeople + amount);
//		}
//	}
	
	public static void setNumberOfWorkers(BaseBuilding building, int newNumber) {
		int currentPeople = ((Resource) building).getCurrentPeople();
		int maxPeople = ((Resource) building).getMaxPeople();
		if (newNumber < 0 || newNumber > maxPeople) return;
		
		if (newNumber > currentPeople) {
			// Enroll Job more than unemployed
			if (newNumber - currentPeople > getUnemployed()) return;
		}
		
		for (BaseBuilding b : buildings.values()) {
			if (b.equals(building))
				((Resource) b).setCurrentPeople(newNumber);
		}
	} 
	
	public static int getMaxPopulation() {
		int countHouse = 0;
		for (BaseBuilding b : buildings.values()) {
			if (b instanceof House) 
				countHouse++;
		}
		return GameConfig.HOUSE_MAX_PEOPLE * countHouse;
	}
	
	public static int getUnemployed() {
		int sumEmployed = 0;
		for (BaseBuilding b : buildings.values()) {
			if (b instanceof Resource) 
				sumEmployed += ((Resource) b).getCurrentPeople();
		}
		for (BaseUnit u : ourUnits.keySet()) {
			sumEmployed += u.getPeople();
		}
		return currentPopulation - sumEmployed;
	}
	
	// Functions for game flow
	
	public static void updateResources() {
		for (BaseBuilding b : buildings.values()) {
			int currentPeople = 0;
			try {
				currentPeople = ((Resource) b).getCurrentPeople();
			}catch (ClassCastException e){
				continue;
			}
			
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
				int tmp = Math.min(ironOre, (int) GameConfig.SMELTER_WORK_RATE * currentPeople);
				iron += tmp;
				ironOre -= tmp;
			}
		}
	}
	
	public static void updateCurrentPopulation() {
		if (currentPopulation >= getMaxPopulation()) return;
		int newPopulation = currentPopulation + (int) (currentPopulation * GameConfig.HOUSE_BORN_RATE);
		currentPopulation = Math.min(getMaxPopulation(), newPopulation);
	}
	
	private static boolean hasEnoughMaterial(BaseBuilding b) {
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
	
	private static void deductMaterial(BaseBuilding b) {
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
	
	public static boolean canBuildBuilding(BaseBuilding b, Position p) {
		if (!b.canBuildOn(map.get(p))) return false;
		if (buildings.containsKey(p)) return false;
		if (!hasEnoughMaterial(b)) return false;
		if(territory[p.getRow()][p.getColumn()] == 0) return false;
		
		System.out.println("Accept " + b.getClass().getSimpleName() + " on " + map.get(p));
		return true;
	}
	
	public static void buildBuilding(BaseBuilding b, Position p) {
		if (!canBuildBuilding(b, p)) return;
		
		b.setPosition(p);
		GameController.getGameMap().get(p).setBuilding(b);
		updateTerritory(b, p, 1);
		deductMaterial(b);
		buildings.put(p, b);
	}
	
	public static void initBuilding(BaseBuilding b, Position p) {
		b.setPosition(p);
		GameController.getGameMap().get(p).setBuilding(b);
		updateTerritory(b, p, 1);
		buildings.put(p, b);
	}
	
	private static void updateTerritory(BaseBuilding b, Position p,int add) {
		int radius = GameConfig.TERRITORY_RADIUS;
		int size = GameConfig.getMapSize();
		for(int i = Math.max(0, p.getRow()-radius); i<=Math.min(p.getRow()+radius, size);i++) {
			for(int j = Math.max(0, p.getColumn()-radius); j<=Math.min(p.getColumn()+radius, size);j++) {
				if(territory[i][j] == 0 && add == 1) territoryCount += 1;
				if(territory[i][j] == 1 && add == -1) territoryCount -= 1;
				territory[i][j] += add;
				GameController.getGameMap().get(i, j).increaseTerritoryBy(add);
			}
		}
	}
	
	public static void sellMaterial(Material m, int amount) {
		System.out.println("Selling " + m + "  amount = " + amount);
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
	
	public static void attackUnit(BaseUnit from, BaseUnit to) {
		from.attack(to);
	}
	
	public static void destroyBuiding(BaseUnit from, BaseBuilding to) {
		from.destroy(to);
	}
	
	public static void moveUnit(BaseUnit unit, Position destination) {
		unit.move(destination);
	}
	
	public static boolean militaryIsInCamp(MilitaryCamp camp, BaseUnit unit) {
		if (!isOurUnit(unit)) return false;
		Position unitPos = unit.getPosition();
		return buildings.get(unitPos) == camp;		
	} 
	
	public static boolean payToUpgrateMilitary() {
		if (money < GameConfig.MILLITARY_UPGRADE_PRICE) return false;
		money -= GameConfig.MILLITARY_UPGRADE_PRICE;
		return true;
	}
	
	public static void changeMilitary(BaseUnit unit_old, BaseUnit unit_new) {
		if (!isOurUnit(unit_old)) return;
		Position pos = unit_old.getPosition();
		unit_new.setPeople(unit_old.getPeople());
		unit_new.setMoved(true);
		removeOurUnit(unit_old);
		addOurUnit(unit_new, pos);
	}
	
	public static Terrain getOurUnitTerrain(BaseUnit unit) {
		Position pos = unit.getPosition();
		return map.get(pos);
	}
	
	public static void upgradeSwordMan(BaseUnit unit) {
		Position pos = ourUnits.get(unit);
//		if (!(buildings.get(pos) instanceof MilitaryCamp)) return;
		if (!payToUpgrateMilitary()) return;
		
		Terrain terrain = map.get(pos);
		if (terrain == Terrain.FOREST) {
			BaseUnit new_unit = new ForestSwordMan();
			GameLogic.changeMilitary(unit, new_unit);
		}
		else if (terrain == Terrain.MOUNTAIN) {
			BaseUnit new_unit = new MountainSwordMan();
			GameLogic.changeMilitary(unit, new_unit);
		}
		else if (terrain == Terrain.PLAIN) {
			BaseUnit new_unit = new FieldSwordMan();
			GameLogic.changeMilitary(unit, new_unit);
		}
	}
	
	public static void buildMilitary(BaseBuilding building, String militaryType) {
		if (!(building instanceof MilitaryCamp)) return;
		if (getUnemployed() < GameConfig.MILITARY_SIZE) return;
		BaseUnit unit;
		if (militaryType == "SwordMan") 
			unit = new SwordMan();
		else
			unit = new Archer();
		
		for (Position pos : buildings.keySet()) {
			if (buildings.get(pos).equals(building))
				addOurUnit(unit, pos);	
		}
		System.out.println("Successfully build " + unit.getClass().getSimpleName());
	}
	
	public static void heal(BaseUnit unit) {
		Position pos = ourUnits.get(unit);
		if (!(buildings.get(pos) instanceof MilitaryCamp)) return;
		unit.setPeople(Math.min(getUnemployed(), GameConfig.MILITARY_SIZE));
	}
	
	public static void addUnit(BaseUnit unit, Position pos) {
		if(isOurUnit(unit)) {
			addOurUnit(unit, pos);
		}
		else {
			addEnemyUnit(unit, pos);
		}
	}
	
	public static void addOurUnit(BaseUnit unit, Position pos) {
		GameController.getGameMap().get(pos).setUnit(unit);
		unit.setPosition(pos);
		ourUnits.put(unit, pos);
	}
	
	public static void addEnemyUnit(BaseUnit unit, Position pos) {
		GameController.getGameMap().get(pos).setUnit(unit);
		unit.setPosition(pos);
		enemyUnits.put(unit, pos);
	}
	
	public static void removeUnit(BaseUnit unit) {
		if(isOurUnit(unit)) {
			removeOurUnit(unit);
		}
		else {
			removeEnemyUnit(unit);
		}
	}
	
	public static void removeOurUnit(BaseUnit unit) {
		GameController.getGameMap().get(unit.getPosition()).setUnit(null);
		ourUnits.remove(unit);
	}
	
	public static void removeEnemyUnit(BaseUnit unit) {
		GameController.getGameMap().get(unit.getPosition()).setUnit(null);
		enemyUnits.remove(unit);
	}
	
	public static boolean isOurUnit(BaseUnit unit) {
		return getOurUnits().containsKey(unit);
	}
	
	public static void updateAttackTerritory(BaseUnit unit, boolean isAttackTerritory) {
		
		Position p = unit.getPosition();
		int radius = GameConfig.getAttackRangebyUnit(unit);
		int size = GameConfig.getMapSize();
		for(int i = Math.max(0, p.getRow()-radius); i<=Math.min(p.getRow()+radius, size);i++) {
			for(int j = Math.max(0, p.getColumn()-radius); j<=Math.min(p.getColumn()+radius, size);j++) {
				GameController.getGameMap().get(i, j).setAttackTerritory(isAttackTerritory);
			}
		}
	}
	
	public static void updateMoveTerritory(BaseUnit unit, boolean isMoveTerritory) {
		
		Position p = unit.getPosition();
		int radius = GameConfig.getMoveRangebyUnit(unit);
		int size = GameConfig.getMapSize();
		for(int i = Math.max(0, p.getRow()-radius); i<=Math.min(p.getRow()+radius, size);i++) {
			for(int j = Math.max(0, p.getColumn()-radius); j<=Math.min(p.getColumn()+radius, size);j++) {
				GameController.getGameMap().get(i, j).setMoveTerritory(isMoveTerritory);
			}
		}
	}
	
	public static void updateDay() {
		enemyMove();
		resetUnitMove();
		updateResources();
		updateCurrentPopulation();
	}
	
	public static void resetUnitMove() {
		for (BaseUnit unit : ourUnits.keySet()) {
			unit.setMoved(false);
		}
		for (BaseUnit unit : enemyUnits.keySet()) {
			unit.setMoved(false);
		}
	}
	
	public static boolean isInAttackRange(BaseUnit from, Position pos) {
		return from.getPosition().getMaxPerpendicularDistance(pos) <= GameConfig.getAttackRangebyUnit(from);
	}
	
	public static void enemyMove() {
		for (BaseUnit enemy : new ArrayList<BaseUnit>(getEnemyUnits().keySet()) ) {
			BaseUnit targetUnit = closestOurUnitFrom(enemy);
			BaseBuilding targetBuilding = closestOurBuildingFrom(enemy);
			
			int distanceFromUnit = enemy.getPosition().getDistanceFrom(targetUnit);
			int distanceFromBuilding= enemy.getPosition().getDistanceFrom(targetBuilding);
			
			
			if( distanceFromUnit <= distanceFromBuilding) {
				if(isInAttackRange(enemy, targetUnit.getPosition())) {
					attackUnit(enemy, targetUnit);
				}
				else {
					
					moveUnitusingShortestPath(enemy, targetUnit.getPosition());
				}
				
			}
			else {
				if(isInAttackRange(enemy, targetBuilding.getPosition())) {
					destroyBuiding(enemy, targetBuilding);
				}
				else {
					moveUnitusingShortestPath(enemy, targetBuilding.getPosition());
				}
			}
		}
	}
	
	public static void moveUnitusingShortestPath(BaseUnit unit, Position pos) {
		int moveRange = GameConfig.getMoveRangebyUnit(unit);
		int bestRow = getBestPointfromMove(unit.getPosition().getRow(), pos.getRow(), moveRange);
		int bestColumn = getBestPointfromMove(unit.getPosition().getColumn(), pos.getColumn(), moveRange);
		moveUnit(unit, new Position(bestRow, bestColumn));
	}
	
	// TODO: proof that this is true (not sure)
	public static int getBestPointfromMove(int from, int to, int range) {
		if(to > from) {
			return to-Math.max(0, -from-range+to);
		}
		else {
			return to+Math.max(0, from+range-to);
		}
	}
	
	public static BaseUnit closestOurUnitFrom(BaseUnit enemy) {
		BaseUnit targetUnit = null;
		int min = 2*GameConfig.getMapSize();
		for (BaseUnit ourUnit : getOurUnits().keySet()) {
			int distance = ourUnit.getPosition().getDistanceFrom(enemy.getPosition());
			if(distance < min) {
				min = distance;
				targetUnit = ourUnit;
			}
		}
		return targetUnit;
	}
	
	public static BaseBuilding closestOurBuildingFrom(BaseUnit enemy) {
		BaseBuilding targetBuilding = null;
		int min = 2*GameConfig.getMapSize();
		for (BaseBuilding building: getBuildings().values()) {
			int distance = building.getPosition().getDistanceFrom(enemy.getPosition());
			if(distance < min) {
				min = distance;
				targetBuilding = building;
			}
		}
		return targetBuilding;
	}
	
	public static boolean isGameOver() {
		return buildings.isEmpty() && (day >=GameConfig.getPreparationWaveNumber()*GameConfig.getDayPerWave());
	}
	
	public static boolean isGameClear() {
		boolean isCoverAllMap = territoryCount == (GameConfig.getMapSize() * GameConfig.getMapSize());
		return isCoverAllMap || day >= (GameConfig.getPreparationWaveNumber()+GameConfig.getEnemyWaveNumber())*GameConfig.getDayPerWave();
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
			if (b.getClass().getSimpleName().equals(resourceName))
				cnt += ((Resource) b).getCurrentPeople();
		}
		return cnt;
	}
	
	public static Map<Position, BaseBuilding> getBuildings() {
		return buildings;
	}
	
	public static Map<Position, Terrain> getMap() {
		return map;
	}
	
	public static Map<BaseUnit, Position> getOurUnits() {
		return ourUnits;
	}
	
	public static void SetCurrentPopulation(int amount) {
		currentPopulation = amount;
	}

	public static Map<BaseUnit, Position> getEnemyUnits() {
		return enemyUnits;
	}

	public static void setEnemyUnits(Map<BaseUnit, Position> enemyUnits) {
		GameLogic.enemyUnits = enemyUnits;
	}

	public static int getTerritoryCount() {
		return territoryCount;
	}

	public static void setTerritoryCount(int territoryCount) {
		GameLogic.territoryCount = territoryCount;
	}
	
}
