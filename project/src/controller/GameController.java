package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle.Control;

import entity.building.Field;
import entity.building.House;
import entity.building.Mine;
import entity.building.Sawmill;
import entity.building.Smelter;
import entity.unit.BaseUnit;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import game.Camera;
import game.Cell;
import game.ControlAction;
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
import utils.AnimationUtil;
import utils.AudioUtil;
import utils.GameConfig;
import utils.MessageTextUtil;
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
	 * The variable that stores the next action of the player for the purpose of
	 * delay optimization.
	 */
	private static Runnable nextAction = null;

	/**
	 * Setter of next action.
	 * 
	 * @param nextAction The action to be set
	 */
	public static void setNextAction(Runnable nextAction) {
		GameController.nextAction = nextAction;
	}

	/**
	 * Does next action if there is no {@link InterruptController interrupt}.
	 */
	public static void doNextAction() {
		if (InterruptController.isInterruptPlayerMovingInput()) {
			return;
		}
		if (nextAction != null) {
			nextAction.run();
		}
		nextAction = null;
	}

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
		Position field_pos = new Position(10, 11);
		gameMap.get(10, 11).setBuilding(field);
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
	
	public static void gameUpdate(ControlAction action) {
		Position cameraPosition= camera.getPosition();
		boolean isMoved = true;
		switch (action) {
		case CAMERA_MOVE_UP:
			camera.move(cameraPosition.moveUp());
			break;
		case CAMERA_MOVE_DOWN:
			camera.move(cameraPosition.moveDown());
			break;
		case CAMERA_MOVE_LEFT:
			camera.move(cameraPosition.moveLeft());
			break;
		case CAMERA_MOVE_RIGHT:
			camera.move(cameraPosition.moveRight());
			break;
		case CAMERA_STAY_STILL:
			isMoved = false;
		default:
			break;
		}
			InterruptController.setStillAnimation(true);
			new Thread(() -> {
				try {
					AnimationUtil.playAnimation(2).join();
				} catch (InterruptedException e) {
					System.out.println("Move animation interrupted");
				}
				Platform.runLater(() -> {
//					if (isMoved) {
//						postMoveUpdate(false);
//					} else {
//						postMoveUpdate(true);
//					}
					postGameUpdate();
				});
			}).start();
	}
	

	/**
	 * Updates item and checks cell type after the move or stay still action. If
	 * there is an item on the same cell as the player, collect it. If the player is
	 * standing on the ladder cell, move up or down one floor.
	 * 
	 * @param isMove Tell whether the move is a success or not
	 */
	public static void postMoveUpdate(boolean isMove) {
		GameMap thisGameMap = GameController.getGameMap();
		Camera camera = GameController.getCamera();
		
		// TODO: Add logic for post move action
//		Cell currentCell = thisGameMap.get(player.getPosY(), player.getPosX());
//		Item cellItem = currentCell.getItem();
//
//		// Checks item on the cell
//		if ((cellItem != null)) {
//			if (player.getItemList().size() == GameConfig.MAX_ITEM) {
//				MessageTextUtil.textWhenCannotPickedItem(cellItem);
//				return;
//			}
//			player.getItemList().add(cellItem);
//			currentCell.setItem(null);
//			MessageTextUtil.textWhenPickUpItem(cellItem);
//
//			// Checks the cell type
//		} else if ((currentCell.getType() == Cell.LADDER_UP) && isMove) {
//			boolean isAscending = GameController.ascending();
//			int level = GameController.getLevel();
//			if (!isAscending) {
//				level = 0;
//			}
//			MessageTextUtil.textWhenAscending(level);
//		} else if ((currentCell.getType() == Cell.LADDER_DOWN) && isMove) {
//			GameController.descending();
//			MessageTextUtil.textWhenDescending(GameController.getLevel());
//		}
	}
	

	/**
	 * Dispatch attack action.
	 * 
	 * @param action  The {@link DispatchAction action} to be dispatch
	 * @param monster The target entity
	 */
	public static void gameUpdate(ControlAction action, BaseUnit from, BaseUnit to) {
		if (InterruptController.isStillAnimation()) {
			setNextAction(() -> {
				gameUpdate(action);
			});
			return;
		}

		InterruptController.setStillAnimation(true);
		// Dispatches action
		if (!from.isMoved()) {
			to.setMoved(true);
			to.setAttacked(true);
		} else {
			MessageTextUtil.textWhenCannotAttack();
			InterruptController.setStillAnimation(false);
			return;
		}

		// Plays attack animation
		new Thread() {
			@Override
			public void run() {
				try {
					AnimationUtil.playAnimation(2).join();
				} catch (InterruptedException e) {
					System.out.println("Attack animation interrupted");
				}
				Platform.runLater(() -> {
					postGameUpdate();
				});
			}
		}.start();

	}
	
	
	/**
	 * Updates monsters, potion effects, and user interface after player's turn.
	 */
	public static void postGameUpdate() {
		// Updates monsters and potions
//		TODO: update entity details
//		GameLogic.

		// Updates user interface
		GameScene.initScene();

		// Play monster animations
		new Thread(() -> {
			try {
				AnimationUtil.playAnimation(0).join();
			} catch (InterruptedException e) {
				System.out.println("Post game animation interrupted");
			}
			Platform.runLater(() -> {
				if (GameController.isGameOver()) {
					return;
				}
				InterruptController.setStillAnimation(false);
				doNextAction();
			});
		}).start();

	}

	
	/**
	 * Create new {@link FadeTransition} then play transition along with background
	 * music.
	 */
	private static void initialTransition() {
		GameScene.getGamePane().setOpacity(0.0);

		InterruptController.setTransition(true);
		FadeTransition fadeIn = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 0.0, 1.0);

		MapRenderer.render();

		fadeIn.play();
		fadeIn.setOnFinished((event) -> InterruptController.setTransition(false));

		bgm.play();
	}
}