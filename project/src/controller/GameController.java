package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import logic.GameMap;
import logic.MapGenerator;
import logic.MapRenderer;
import logic.Sprites;
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
	 * Represent the current {@link Player} instance.
	 */
	private static Player player;

	/**
	 * Create new {@link GameMap} and add to {@link #levelMapList}.
	 * 
	 * @return {@link GameMap} new floor which added to {@link #levelMapList}
	 */
	private static GameMap addNewFloor() {
		GameMap newFloor = MapGenerator.generateMap();
		levelMapList.add(newFloor);
		return newFloor;
	}

	/**
	 * If level reaches {@link GameConfig#LEVEL_BOUND level_bound} and disable the
	 * endless mode then change to the {@link CongratulationScene} otherwise change
	 * {@link #gameMap} to lower level and making fade transition if able to do.
	 * 
	 * @return return true if {@link #player} can go to lower level otherwise false
	 */
	public static boolean descending() {
		level += 1;
		GameMap newMap = null;

		if (level == GameConfig.LEVEL_BOUND) {
			bgm.stop();
			FadeTransition fadeOut = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 1.0, 0.0);
			
			InterruptController.setTransition(true);
			fadeOut.play();

			fadeOut.setOnFinished((event) -> {
				InterruptController.setTransition(false);
				SceneController.setSceneToStage(CongratulationScene.getScene());
			});

			return false;
		}

		try {
			newMap = getFloor(level);
		} catch (InvalidFloorException e) {
			newMap = addNewFloor();
		}

		FadeTransition fadeOut = makeFadingScene(GameScene.getGamePane(), 1.0, 0.0, newMap, false);

		fadeOut.play();
		InterruptController.setTransition(true);

		return true;
	}

	/**
	 * Change {@link #gameMap} to upper level and making {@link FadeTransition} if
	 * able to do.
	 * 
	 * @return return true if {@link #player} can go to upper level otherwise false
	 */
	public static boolean ascending() {
		try {
			GameMap newMap = getFloor(level - 1);

			FadeTransition fadeOut = makeFadingScene(GameScene.getGamePane(), 1.0, 0.0, newMap, true);

			fadeOut.play();
			InterruptController.setTransition(true);
		} catch (InvalidFloorException e) {
			return false;
		}

		level -= 1;

		return true;
	}

	/**
	 * Initialize new game.
	 */
	public static void start() {
		RandomUtil.resetFilterIndex();
		levelMapList.clear();
		level = 1;

		GameMap newFloor = addNewFloor();
		setGameMap(newFloor);

		player = makeNewPlayer();

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
		if (player.getHealth() <= 0) {
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
	 * Get roomList from {@link #gameMap}.
	 * 
	 * @return Room list of {@link #gameMap}
	 */
	public static List<Pair<Integer, Integer>> getRoomList() {
		return getGameMap().getRoomList();
	}

	/**
	 * Getter for {@link #player}.
	 * 
	 * @return {@link #player}
	 */
	public static Player getPlayer() {
		return player;
	}

	/**
	 * Setter for {@link #player}.
	 * 
	 * @param newPlayer the new {@link #player}
	 */
	public static void setPlayer(Player newPlayer) {
		player = newPlayer;
	}

	/**
	 * Getter for {@link #level}.
	 * 
	 * @return {@link #level}
	 */
	public static int getLevel() {
		return level;
	}

	/**
	 * Utility method that creating {@link FadeTransition} for switching floor.
	 * 
	 * @param node        the target node that we want to make a fade
	 * @param from        starting opacity
	 * @param to          ending opacity
	 * @param newMap      the map that we want to render
	 * @param isAscending true if the type of switching floor is ascending otherwise
	 *                    false
	 * @return {@link FadeTransition} instance which used for making transition
	 *         between floor
	 */
	private static FadeTransition makeFadingScene(Node node, double from, double to, GameMap newMap,
			boolean isAscending) {
		// Fade in, Fade out setup
		FadeTransition fadeFirst = TransitionUtil.makeFadingNode(GameScene.getGamePane(), from, to);
		FadeTransition fadeSecond = TransitionUtil.makeFadingNode(GameScene.getGamePane(), to, from);

		// Fade out when finished
		fadeSecond.setOnFinished((event) -> InterruptController.setTransition(false));

		// Fade in when finished
		fadeFirst.setOnFinished((event) -> {
			gameMap.get(player.getPosY(), player.getPosX()).setEntity(null);

			setGameMap(newMap);
			List<Pair<Integer, Integer>> roomList = newMap.getRoomList();

			int idxPos = 0;

			if (isAscending) {
				idxPos = roomList.size() - 1;
			}

			int posX = roomList.get(idxPos).getValue();
			int posY = roomList.get(idxPos).getKey();

			player.setPositionOnMap(posY, posX);
			newMap.get(posY, posX).setEntity(player);
			MapRenderer.render();
			fadeSecond.play();
		});

		return fadeFirst;
	}

	/**
	 * Setup {@link GameScene} when start or restart game.
	 */
	private static void sceneSetup() {
		InterruptController.resetInterruptState();
		GameScene.getMessagePane().resetMessage();
//		GameScene.getEffectPane().update();
//		GameScene.getStatusPane().update();
//		GameScene.getInventoryPane().update();
		SceneController.setSceneToStage(GameScene.getScene());
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