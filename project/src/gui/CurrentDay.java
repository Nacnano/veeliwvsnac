package gui;

import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

/**
 * The CurrentDay class represents a graphical user interface component that displays the current day
 * in the game. It provides methods to update the displayed day.
 */
public class CurrentDay extends HBox {
	
	/**
	 * Represent the height of the pane.
	 */
	private final int heightBox = 16;

	/**
	 * Represent the width of the pane.
	 */
	private final int widthBox = 40;
	
	Text dayText;
	
	/**
     * Constructs a new CurrentDay object. It initializes the style of the pane and adds the text component
     * displaying the current day.
     */
	public CurrentDay() {
		styleSetup();
		dayText = new Text("Day: " + GameController.getDay());
		dayText.setFont(FontUtil.getFont("extraSmall"));
		this.getChildren().add(dayText);
	}
	
	/**
     * Initializes the style for the pane.
     */
	private void styleSetup() {
		setBackground(new Background(new BackgroundFill(Color.LIGHTSALMON, null, null)));
		setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		setAlignment(Pos.CENTER);
		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setMaxHeight(heightBox * GameConfig.getScale());
		setMaxWidth(widthBox * GameConfig.getScale());
	}
	
	/**
     * Updates the displayed day with the current day from the game.
     */
	public void update() {
		dayText.setText("Day: " + GameController.getDay());
	}
	
}
