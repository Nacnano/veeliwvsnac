package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import controller.GameController;
import entity.unit.BaseUnit;
import javafx.util.Pair;
import utils.GameConfig;
import utils.RandomUtil;

/**
 * The MapGenerator class is used to generate {@link GameMap}.
 * 
 */
public class MapGenerator {

	/**
	 * The Array of integer that represents a terrain of each cell.
	 */
	private static int map[][];

	/**
	 * Generates new {@link GameMap}.
	 * 
	 * @return Randomly generated {@link GameMap}
	 */
	public static GameMap generateMap() {
		GameMap gameMap = buildNewEmptyMap();

		generateEnemyOnMap(gameMap);

		return gameMap;
	}

	/**
	 * A class to represent position and direction. This class is used for path
	 * creating only.
	 *
	 */
	

	/**
	 * Generates new empty map.
	 * 
	 * @return Randomly generated empty map
	 */
	private static GameMap buildNewEmptyMap() {
		GameMap gameMap;
		do {
			gameMap = new GameMap();
			map = new int[GameConfig.getMapSize() + 10][GameConfig.getMapSize() + 10];

			// Generates rooms
			int roomCnt = 1;
			while (roomCnt <= MAX_ROOM) {
				int y = RandomUtil.random(1, GameConfig.getMapSize() - 1);
				int x = RandomUtil.random(1, GameConfig.getMapSize() - 1);
				if (makeRoom(y, x, roomCnt)) {
					gameMap.getRoomList().add(new Pair<>(y, x));
					roomCnt++;
				}
			}

			// Generates paths
			int pathCnt = 1;
			while (pathCnt <= MAX_PATH) {
				int x, y;
				do {
					y = RandomUtil.random(1, GameConfig.getMapSize() - 1);
					x = RandomUtil.random(1, GameConfig.getMapSize() - 1);

					// Random until the cell type is room's wall
				} while (map[y][x] < 1);

				State state = new State(y, x, RandomUtil.random(0, 3));
				int tmp = state.getCellType();

				// Changes cell type from room's wall to void
				state.setCellType(VOID);
				if (makePath(state, tmp, 0)) {

					// If path generated successfully, changes cell type to PATH
					state.setCellType(PATH);
					pathCnt++;
				} else {
					// otherwise changes cell type back to room's wall
					state.setCellType(tmp);
				}
			}
			makeMap(gameMap.getGameMap());

			// If the map is not valid, generates again
		} while (!isValid(gameMap));

		return gameMap;
	}


	/**
	 * Creates {@link Cell} array from array of cell type
	 * 
	 * @param gameMap The array of {@link Cell} to store the result in
	 */
	private static void makeMap(Cell[][] gameMap) {

		// Sets PATH
		for (int i = 0; i <= GameConfig.getMapSize(); i++) {
			for (int j = 0; j <= GameConfig.getMapSize(); j++) {
				gameMap[i][j] = new Cell(Cell.VOID);
				if ((map[i][j] == ROOM) || (map[i][j] == PATH)) {
					gameMap[i][j].setType(Cell.PATH);
				}
			}
		}

		// Sets WALL
		for (int i = 0; i <= GameConfig.getMapSize(); i++) {
			for (int j = 0; j <= GameConfig.getMapSize(); j++) {
				int pathCount = 0;
				for (int k = i - 1; k <= i + 1; k++) {
					for (int l = j - 1; l <= j + 1; l++) {
						if ((k < 0) || (l < 0) || (k > GameConfig.getMapSize()) || (l > GameConfig.getMapSize())) {
							continue;
						}
						if (gameMap[k][l].getType() == Cell.PATH) {
							pathCount += 1;
						}
					}
				}
				if ((pathCount > 0) && (gameMap[i][j].getType() != Cell.PATH)) {
					gameMap[i][j].setType(Cell.WALL);
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
		int level = GameController.getLevel();

		ArrayList<BaseUnit> enemyList = RandomUtil.randomEnemyList(level);
		gameMap.getEnemyList().addAll(enemyList);

		for (BaseUnit enemy : enemyList) {
			boolean isAdd = false;
			do {
				int randomX = RandomUtil.random(0, GameConfig.getMapSize() - 1);
				int randomY = RandomUtil.random(0, GameConfig.getMapSize() - 1);

				Cell currentCell = gameMap.get(randomY, randomX);

				// TODO: Add logic to check not in our territory
				if (currentCell.getTerrain() == Terrain.WATER && currentCell.getUnit() == null) {
					enemy.setPosition(new Position(randomY, randomX));
					gameMap.get(randomY, randomX).setUnit(enemy);
					isAdd = true;
				}
			} while (!isAdd);
		}
	}

}