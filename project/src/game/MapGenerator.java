package game;

import java.util.ArrayList;

import controller.GameController;
import entity.unit.BaseUnit;
import utils.GameConfig;
import utils.RandomUtil;
import utils.TextFileUtil;

/**
 * The MapGenerator class is used to generate {@link GameMap}.
 * 
 */
public class MapGenerator {

	private static GameMap gameMap;


	/**
	 * Generates new {@link GameMap}.
	 * 
	 * @return Randomly generated {@link GameMap}
	 */
	public static GameMap generateMap(String mapName) {
		GameMap gameMap = buildNewEmptyMap();
		buildNewMap(gameMap, mapName);
		generateEnemyOnMap(gameMap);

		return gameMap;
	}
	
	/**
	 * Generates new empty map.
	 * 
	 * @return  generated empty map
	 */
	private static GameMap buildNewEmptyMap() {
		gameMap = new GameMap();
		for (int i = 0; i <= GameConfig.getMapSize(); i++) {
			for (int j = 0; j <= GameConfig.getMapSize(); j++) {
				gameMap.getGameMap()[i][j] = new Cell();
			}
		}
			
		return gameMap;
	}

	/**
	 * Creates {@link Cell} array from array of cell type
	 * 
	 * @param gameMap The array of {@link Cell} to store the result in
	 */
	private static void buildNewMap(GameMap gameMap, String mapName) {
		
		String[] mapContents = TextFileUtil.ReadTextFile("map/" + mapName + ".txt");
				
		for (int i = 0; i < GameConfig.getMapSize(); i++) {
			for (int j = 0; j < GameConfig.getMapSize(); j++) {

				switch (Character.toString(mapContents[i].charAt(j))) {
					case "P":
						gameMap.get(i, j).setTerrain(Terrain.PLAIN);
						break;
					case "M":
						gameMap.get(i, j).setTerrain(Terrain.MOUNTAIN);
						break;
					case "F":
						gameMap.get(i, j).setTerrain(Terrain.FOREST);
						break;
					case "W":
						gameMap.get(i, j).setTerrain(Terrain.WATER);
						break;
					default:
						System.out.println(mapContents[i].charAt(j));
						System.out.println("Text file invalid for map: " + mapName);
				}
				
			}
		}
	}


	/**
	 * Generate new set of {@link Monster} which will assign to {@link GameMap}
	 * 
	 * @param gameMap the {@link GameMap} that assign {@link Monster} to
	 */
	public static void generateEnemyOnMap(GameMap gameMap) {
		int day = GameController.getDay();

		ArrayList<BaseUnit> enemyList = RandomUtil.randomEnemyList(day);
		gameMap.getEnemyList().addAll(enemyList);

		for (BaseUnit enemy : enemyList) {
			boolean isAdd = false;
			do {
				int randomX = RandomUtil.random(0, GameConfig.getMapSize() - 1);
				int randomY = RandomUtil.random(0, GameConfig.getMapSize() - 1);

				Cell currentCell = gameMap.get(randomY, randomX);

				// TODO: Add logic to check not to add enemy in our territory
				if (currentCell.getTerrain() == Terrain.WATER && currentCell.getUnit() == null) {
					enemy.setPosition(new Position(randomY, randomX));
					gameMap.get(randomY, randomX).setUnit(enemy);
					isAdd = true;
				}
			} while (!isAdd);
		}
	}

}