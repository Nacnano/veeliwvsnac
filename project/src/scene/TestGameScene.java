package scene;

import controller.GameController;
import controller.SceneController;
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
		
		root = new GridPane();
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		VBox workerStatus = new gui.WorkerStatus();
		root.add(workerStatus, 0, 0);
		
		VBox resourceStatus = new gui.ResourceStatus();
		root.add(resourceStatus, 1, 0);
		
		HBox materialStatus = new gui.MaterialStatus();
		root.add(materialStatus, 2, 0);
		
		VBox shopPopUp = new gui.ShopPopUp();
		root.add(shopPopUp, 0, 1);
		
		cachedScene = SceneController.makeNewScene(root);
		return cachedScene;
	}


}
