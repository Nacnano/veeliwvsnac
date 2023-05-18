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

		HBox materialStatus = new gui.MaterialStatus();
		root.add(materialStatus, 0, 0);
		
		
		cachedScene = SceneController.makeNewScene(root);
		return cachedScene;
	}


}