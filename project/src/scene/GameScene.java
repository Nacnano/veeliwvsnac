package scene;

import gui.CurrentDay;
import gui.MaterialStatus;
import gui.MessagePane;
import gui.NextDay;
import gui.PausePane;
import gui.ResourceStatus;
import gui.ShopPopUp;
import gui.WorkerStatus;
import controller.InterruptController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import game.GameLogic;
//import game.MapRenderer;
import utils.DrawUtil;
import utils.AudioUtil;
import utils.GameConfig;

/**
 * The GameScene class provides a method to initialize the game scene and store
 * the components in the game scene.
 *
 */
public class GameScene {

	/**
	 * The game scene.
	 */
	private static Scene scene = null;

	/**
	 * The {@link PausePane pause pane} that will display when the player click
	 * pause button.
	 */
	private static PausePane pausePane;
	
	/**
	 * The {@link MessagePane} that will display message.
	 */
	private static MessagePane messagePane;
	
	/**
	 * The {@link MaterialStatus} that will display material.
	 */
	private static MaterialStatus materialStatus;
	
	/**
	 * The {@link ShopPopUp} that will display shop.
	 */
	private static ShopPopUp shopPopUp;
	
	/**
	 * The {@link ResourceStatus} that will display resource detail.
	 */
	private static ResourceStatus resourceStatus;
	
	/**
	 * The {@link WorkerStatus} that will display number of workers.
	 */
	private static WorkerStatus workerStatus;
	
	/**
	 * The {@link CurrentDay} that will display current day.
	 */
	private static CurrentDay currentDay;
	
	/**
	 * The {@link NextDay} that will go to the next day.
	 */
	private static NextDay nextDay;

	/**
	 * The pane for entity button.
	 */
	private static AnchorPane buttonPane;

	/**
	 * {@link GraphicsContext graphic context} of the map canvas.
	 */
	private static GraphicsContext gc;

	/**
	 * Pause sprite for the pause button.
	 */
	private static WritableImage pauseSprite = DrawUtil.getWritableImage("icon/pause.png");

	/**
	 * The pane that contains all of the component in game scene.
	 */
	private static StackPane gamePane;

	/**
	 * The root pane.
	 */
	private static StackPane root;

	/**
	 * Initialize game scene.
	 */
	public static void initScene() {
		root = new StackPane();
		root.setPadding(new Insets(0));
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		root.setMinSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		root.setMaxSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		scene = new Scene(root, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		setupGamePane();
		setupGameUI();

//		StackPane.setAlignment(new Group(inventoryPane), Pos.CENTER);
		StackPane.setAlignment(new Group(pausePane), Pos.CENTER);
		StackPane.setAlignment(new Group(shopPopUp), Pos.CENTER);

//		MapRenderer.render();
	}

	/**
	 * Initialize game pane.
	 */
	private static void setupGamePane() {
		gamePane = new StackPane();
		root.getChildren().add(gamePane);

		Canvas canvas = new Canvas(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		gc = canvas.getGraphicsContext2D();
		gamePane.getChildren().add(canvas);

		buttonPane = new AnchorPane();
		gamePane.getChildren().add(buttonPane);
		addEventListener();
	}

	/**
	 * Initialize user interface.
	 */
	private static void setupGameUI() {
		AnchorPane ui = new AnchorPane();
		ui.setPickOnBounds(false);
		gamePane.getChildren().add(ui);

		addPauseButton(ui);

//		statusPane = new StatusPane();
//		messagePane = new MessagePane();
		pausePane = new PausePane();
		shopPopUp = new ShopPopUp();
		
		currentDay = new CurrentDay();
		AnchorPane.setTopAnchor(currentDay, 5.0 * GameConfig.getScale());
		AnchorPane.setLeftAnchor(currentDay, 5.0 * GameConfig.getScale());
		
		nextDay = new NextDay();
		AnchorPane.setTopAnchor(nextDay, 5.0 * GameConfig.getScale());
		AnchorPane.setRightAnchor(nextDay, 25.0 * GameConfig.getScale());
		
		workerStatus = new WorkerStatus();
		AnchorPane.setBottomAnchor(workerStatus, 0.0);
		
		resourceStatus = new ResourceStatus();
		AnchorPane.setBottomAnchor(resourceStatus, 0.0);
		AnchorPane.setLeftAnchor(resourceStatus, 50.0 * GameConfig.getScale());
		
		materialStatus = new MaterialStatus();
		AnchorPane.setBottomAnchor(materialStatus, 0.0);
		AnchorPane.setLeftAnchor(materialStatus, 100.0 * GameConfig.getScale());
		
		materialStatus.setOnMouseClicked((event) -> {
			if (InterruptController.isPauseOpen() || InterruptController.isTransition()) {
				return;
			}
			if (InterruptController.isShopOpen()) {
				shopPopUp.remove();
				return;
			}
			gamePane.getChildren().add(shopPopUp);
			shopPopUp.requestFocus();
			InterruptController.setIsShopOpen(true);
		});
		

//		ui.getChildren().addAll(statusPane, messagePane, effectPane);
		ui.getChildren().addAll(currentDay, nextDay, workerStatus, resourceStatus, materialStatus);
	}



	/**
	 * Add pause button to the user interface pane.
	 * 
	 * @param ui The user interface pane.
	 */
	private static void addPauseButton(AnchorPane ui) {
		Canvas pauseBtn = new Canvas(16 * GameConfig.getScale(), 16 * GameConfig.getScale());
		ui.getChildren().add(pauseBtn);

		AnchorPane.setTopAnchor(pauseBtn, 5.0 * GameConfig.getScale());
		AnchorPane.setRightAnchor(pauseBtn, 5.0 * GameConfig.getScale());

		pauseBtn.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(pauseSprite, GameConfig.getScale()), 0, 0);

		pauseBtn.setOnMouseClicked((event) -> {
			if (InterruptController.isShopOpen() || InterruptController.isTransition()) {
				return;
			}
			if (InterruptController.isPauseOpen()) {
				pausePane.remove();
				return;
			}
			gamePane.getChildren().add(pausePane);
			pausePane.requestFocus();
			InterruptController.setPauseOpen(true);
		});
	}

	/**
	 * Add keyboard listener.
	 */
	private static void addEventListener() {
		scene.setOnKeyPressed((event) -> {
			if (InterruptController.isInterruptPlayerMovingInput() && !InterruptController.isStillAnimation()) {
				return;
			}
			KeyCode keycode = event.getCode();

			switch (keycode) {
//			case A:
//				GameLogic.gameUpdate(DispatchAction.MOVE_LEFT);
//				break;
//			case D:
//				GameLogic.gameUpdate(DispatchAction.MOVE_RIGHT);
//				break;
//			case W:
//				GameLogic.gameUpdate(DispatchAction.MOVE_UP);
//				break;
//			case S:
//				GameLogic.gameUpdate(DispatchAction.MOVE_DOWN);
//				break;
//			case Q:
//				GameLogic.gameUpdate(DispatchAction.STAY_STILL);
//				break;
			default:
				System.out.println("Invalid key");
				break;
			}
		});
	}

	/**
	 * Getter for game scene.
	 * 
	 * @return Game scene
	 */
	public static Scene getScene() {
		if (scene == null) {
			initScene();
		}
		return scene;

	}


	/**
	 * Getter for message pane.
	 * 
	 * @return Message pane
	 */
	public static MessagePane getMessagePane() {
		if (messagePane == null) {
			initScene();
		}
		return messagePane;
	}


	/**
	 * Getter for pause pane.
	 * 
	 * @return Pause pane
	 */
	public static PausePane getPausePane() {
		if (pausePane == null) {
			initScene();
		}
		return pausePane;
	}
	
	/**
	 * Getter for Shop Pop Up.
	 * 
	 * @return Shop Pop Up
	 */
	public static ShopPopUp getShopPopUp() {
		if (shopPopUp == null) {
			initScene();
		}
		return shopPopUp;
	}

	/**
	 * Getter for button pane.
	 * 
	 * @return Button pane
	 */
	public static AnchorPane getButtonPane() {
		if (buttonPane == null) {
			initScene();
		}
		return buttonPane;
	}
	
	/**
	 * Getter for Material Status.
	 * 
	 * @return Material Status
	 */
	public static MaterialStatus getMaterialStatus() {
		if (materialStatus == null) {
			initScene();
		}
		return materialStatus;
	}
	
	/**
	 * Getter for Resource Status.
	 * 
	 * @return Resource Status
	 */
	public static ResourceStatus getResourceStatus() {
		if (resourceStatus == null) {
			initScene();
		}
		return resourceStatus;
	}

	/**
	 * Getter for Worker Status.
	 * 
	 * @return Worker Status
	 */
	public static WorkerStatus getWorkerStatus() {
		if (workerStatus == null) {
			initScene();
		}
		return workerStatus;
	}
	
	/**
	 * Getter for graphic context of the map canvas.
	 * 
	 * @return Graphic context of the map canvas
	 */
	public static GraphicsContext getGraphicsContext() {
		if (gc == null) {
			initScene();
		}
		return gc;
	}

	/**
	 * Getter for game pane.
	 * 
	 * @return Game pane
	 */
	public static StackPane getGamePane() {
		if (gamePane == null) {
			initScene();
		}
		return gamePane;
	}

}