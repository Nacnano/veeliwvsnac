package scene;

import controller.GameController;
import controller.SceneController;
import entity.building.Field;
import game.GameLogic;
import game.Position;
import game.Terrain;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class TestGameScene {

	/**
	 * Represent the cached scene so that it does not need to have a multiple
	 * initialize.
	 */
	private static Scene cachedScene = null;
	private static GridPane root;

	/**
	 * Get the {@link #cachedScene landingScene}.
	 * 
	 * @return the {@link Scene} which used for display in first page
	 */
	public static Scene getScene() {

		// If it have already cached then return the cache
		if (cachedScene != null) {
			return cachedScene;
		}

		System.out.println("Opening Test Game Scene...");
		
		GameLogic.SetCurrentPopulation(50);
		Position pos1 = new Position(0, 0);
		Field field1 = new Field();
		Position pos2 = new Position(1, 0);
		
		GameLogic.getBuildings().put(pos1, field1);
		GameLogic.setNumberOfWorkers(pos1, 10);
		GameLogic.getMap().put(pos2, Terrain.FOREST);
		
		GameLogic.setWood(500);
		GameLogic.setStone(500);
		GameLogic.setIron(500);
		GameLogic.setMoney(3000);
		
		root = new GridPane();
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		VBox workerStatus = new gui.WorkerStatus();
		root.add(workerStatus, 0, 0);
		
		VBox resourceStatus = new gui.ResourceStatus(pos1);
		root.add(resourceStatus, 1, 0);
		
		HBox materialStatus = new gui.MaterialStatus();
		root.add(materialStatus, 2, 0);
		
		VBox shopPopUp = new gui.ShopPopUp();
		root.add(shopPopUp, 0, 1);
		
		VBox changeJobPopUp = new gui.ChangeJobPopUp(pos1);
		root.add(changeJobPopUp, 1, 1);
		
		VBox buildPopUp = new gui.BuildPopUp(pos2);
		root.add(buildPopUp, 2, 1);
		
		cachedScene = SceneController.makeNewScene(root);
		return cachedScene;
	}


}
