package controller;

import java.util.ArrayList;
import java.util.List;

import entity.building.Field;
import entity.building.House;
import entity.building.Mine;
import entity.building.Sawmill;
import entity.building.Smelter;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import game.Camera;
import game.Cell;
import game.GameLogic;
import game.GameMap;
import game.MapGenerator;
import game.MapRenderer;
import game.Position;
import game.Terrain;
import scene.CongratulationScene;
import scene.GameOverScene;
import scene.GameScene;
import scene.LandingScene;
import utils.AudioUtil;
import utils.GameConfig;
import utils.RandomUtil;
import utils.TransitionUtil;

/**
 * The GameController class is the class that control about the {@link #gameMap}
 * which currently render and the changing of {@link #level} inside the game.
 */
public class GameController {

	/**
	 * Represent the {@link GameMap} of current map.
	 */
	private static GameMap gameMap;

	/**
	 * The {@link MediaPlayer} represent the background music of GameScene.
	 */
	private static MediaPlayer bgm = AudioUtil.getGameSceneBGM();

	/**
	 * Represent the current {@link Camera} instance.
	 */
	private static Camera camera;
	
	/**
	 * Represent the current day.
	 */
	private static int day;

	/**
	 * Create new {@link GameMap} and add to {@link #levelMapList}.
	 * 
	 * @return {@link GameMap} new floor which added to {@link #levelMapList}
	 */
	private static GameMap initGameMap() {
		GameMap gameMap = MapGenerator.generateMap("default");
		
		GameLogic.getBuildings().clear();
		GameLogic.SetCurrentPopulation(50);
		
		House house = new House();
		gameMap.get(9, 8).setBuilding(house);
		GameLogic.getBuildings().put(new Position(9, 8), house);
		
		Field field = new Field();
		Position field_pos = new Position(10, 12);
		gameMap.get(10, 12).setBuilding(field);
		GameLogic.getBuildings().put(field_pos, field);
		GameLogic.setNumberOfWorkers(field_pos, 10);
		
		Mine mine = new Mine();
		Position mine_pos = new Position(9, 6);
		gameMap.get(9, 6).setBuilding(mine);
		GameLogic.getBuildings().put(mine_pos, mine);
		GameLogic.setNumberOfWorkers(mine_pos, 10);
		
		Sawmill sawmill = new Sawmill();
		Position sawmill_pos = new Position(10, 4);
		gameMap.get(10, 4).setBuilding(sawmill);
		GameLogic.getBuildings().put(sawmill_pos, sawmill);
		GameLogic.setNumberOfWorkers(sawmill_pos, 10);
		
		Smelter smelter = new Smelter();
		Position smelter_pos = new Position(11, 16);
		gameMap.get(11, 16).setBuilding(smelter);
		GameLogic.getBuildings().put(smelter_pos, smelter);
		GameLogic.setNumberOfWorkers(smelter_pos, 10);
		
		GameLogic.setFood(100);
		GameLogic.setWood(100);
		GameLogic.setStone(100);
		GameLogic.setIron(100);
		GameLogic.setMoney(1000);
		
		
		return gameMap;
	}


	/**
	 * Initialize new game.
	 */
	public static void start() {
		 day = 1;

		GameMap gameMap = initGameMap();
		setGameMap(gameMap);

		camera = makeNewCamera();

		sceneSetup();
		initialTransition();
	}

	/**
	 * Stop the game background music then making fade transition to
	 * {@link LandingScene}.
	 */
	public static void exitToMainMenu() {
		FadeTransition fadeOut = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 1.0, 0.0);

		bgm.stop();

		fadeOut.setOnFinished((event) -> SceneController.backToMainMenu());

		fadeOut.play();
	}

	/**
	 * Checking condition that {@link #player} is currently Game over or not by
	 * checking {@link #player} health.
	 * 
	 * @return true if {@link #player} health is less than or equals 0 otherwise
	 *         false
	 */
	public static boolean isGameOver() {
		if (GameLogic.isGameOver()) {
			bgm.stop();
			FadeTransition fadeOut = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 1.0, 0.0);

			InterruptController.setTransition(true);
			fadeOut.setOnFinished((event) -> {
				SceneController.setSceneToStage(GameOverScene.getScene());
			});

			fadeOut.play();
			return true;
		}
		return false;
	}

	/**
	 * Getter for {@link #gameMap}.
	 * 
	 * @return {@link #gameMap}
	 */
	public static GameMap getGameMap() {
		return gameMap;
	}

	/**
	 * Setter for {@link #gameMap}.
	 * 
	 * @param gameMap the new {@link #gameMap}
	 */
	public static void setGameMap(GameMap gameMap) {
		GameController.gameMap = gameMap;
	}

	/**
	 * Getter for {@link #camera}.
	 * 
	 * @return {@link #camera}
	 */
	public static Camera getCamera() {
		return camera;
	}

	/**
	 * Setter for {@link #camera}.
	 * 
	 * @param newCamera for the new {@link #camera}
	 */
	public static void setCamera(Camera newCamera) {
		camera = newCamera;
	}

	/**
	 * Getter for {@link #day}.
	 * 
	 * @return {@link #day}
	 */
	public static int getDay() {
		return day;
	}
	
	/**
	 * Setter for {@link #day}.
	 * 
	 * @param newDaythe new {@link #day}
	 */
	public static void setDay(int newDay) {
		day = newDay;
	}


	/**
	 * Setup {@link GameScene} when start or restart game.
	 */
	private static void sceneSetup() {
		InterruptController.resetInterruptState();
		SceneController.setSceneToStage(GameScene.getScene());
		GameScene.getWorkerStatus().update();
		GameScene.getMaterialStatus().update();
	}
	
	/**
	 * Create new {@link Player} instance and register to the {@link GameMap}.
	 * 
	 * @return {@link Player} new player instance
	 */
	private static Camera makeNewCamera() {
		Camera newCamera = new Camera();
		newCamera.setPosition(new Position(GameConfig.getMapSize()/2, GameConfig.getMapSize()/2));

		return newCamera;
	}
	
	/**
	 * Create new {@link FadeTransition} then play transition along with background
	 * music.
	 */
	private static void initialTransition() {
		GameScene.getGamePane().setOpacity(0.0);

		InterruptController.setTransition(true);
		FadeTransition fadeIn = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 0.0, 1.0);

//		MapRenderer.render();

		fadeIn.play();
		fadeIn.setOnFinished((event) -> InterruptController.setTransition(false));

		bgm.play();
	}
}