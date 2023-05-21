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
 * The NextDay class represents a graphical component that allows the user to advance to the next day in the game.
 * It is a clickable box with the text "Next Day >>>" displayed on it.
 * When clicked, it triggers the next day action in the game.
 */
public class NextDay extends HBox {

	/**
	 * Represent the height of the pane.
	 */
	private final int heightBox = 16;

	/**
	 * Represent the width of the pane.
	 */
	private final int widthBox = 40;
	
	/**
     * Constructs a new NextDay component.
     * It sets up the style, adds the text label, and registers the mouse click event handler.
     */
	public NextDay() {
		styleSetup();
		Text text = new Text("Next Day >>>");
		text.setFont(FontUtil.getFont("extraSmall"));
		this.getChildren().add(text);
		
		setOnMouseClicked((event) -> {
			try {
				GameController.nextDay();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

	}
	
	/**
     * Initializes the style for the pane.
     */
	private void styleSetup() {
		setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, null, null)));
		setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		setAlignment(Pos.CENTER);
		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setMaxHeight(heightBox * GameConfig.getScale());
		setMaxWidth(widthBox * GameConfig.getScale());

	}
}
