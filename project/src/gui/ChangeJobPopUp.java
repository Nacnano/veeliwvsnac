package gui;

import controller.InterruptController;
import entity.building.BaseBuilding;
import entity.building.Resource;
import game.GameLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import scene.GameScene;
import utils.FontUtil;
import utils.GameConfig;

/**
 * The ChangeJobPopUp class represents a graphical user interface component that displays a pop-up
 * window for changing the number of workers in a building. It allows the user to input the desired
 * number of workers and save the changes.
 */
public class ChangeJobPopUp extends VBox {

	/**
	 * Represent the height of the pane.
	 */
	private final int heightBox = 50;

	/**
	 * Represent the width of the pane.
	 */
	private final int widthBox = 100;
	
	private final int textFieldWidth = 50;

	TextField amount;
	
	Text unemployed, max, optionTitle;
	
	HBox closeBox;
	
//	Position pos;
	BaseBuilding building;
	
	/**
     * Constructs a new ChangeJobPopUp object. It initializes the internal components, event handlers,
     * and style of the pop-up window.
     */
	public ChangeJobPopUp() {
		styleSetup();
		addTitle();
		addOptionContainer();
		addCloseText();

		setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				remove();
			}
		});

		InterruptController.setIsChangeJobOpen(false);
	}
	
	/**
	 * Update value inside setting to current value.
	 */
	public void quitJob() {
		try {
			GameLogic.setNumberOfWorkers(building, Integer.parseInt(amount.getText()));
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}

	/**
	 * Initialize style for pane.
	 */
	private void styleSetup() {
		setBackground(new Background(new BackgroundFill(Color.rgb(245, 246, 231), null, null)));
		setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		setPadding(new Insets(20));
		setSpacing(10);

		setAlignment(Pos.CENTER);
		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setMaxHeight(heightBox * GameConfig.getScale());
		setMaxWidth(widthBox * GameConfig.getScale());

	}

	/**
     * Initializes the new option container and adds components to the container.
     */
	public void addOptionContainer() {
		unemployed = new Text();
		unemployed.setFont(FontUtil.getFont("extraSmall"));
		
		max = new Text();
		max.setFont(FontUtil.getFont("extraSmall"));
	
		Label label = new Label();
		label.setFont(FontUtil.getFont("extraSmall"));
		
		amount = new TextField();
		amount.setPrefWidth(textFieldWidth);
		
		HBox box = new HBox();
		box.getChildren().addAll(label, amount, max);
		box.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(unemployed, box);
	}
	
	/**
	 * Initialize new close text which can be clicked to close pane.
	 */
	private void addCloseText() {
		closeBox = new HBox();
		closeBox.setPadding(new Insets(10, 0, 0, 0));
		closeBox.setAlignment(Pos.CENTER);

		Text closeText = new Text("Save");
		closeText.setFont(FontUtil.getFont("small"));
		closeText.setFill(Color.BLACK);
		closeText.setStroke(null);

		closeBox.getChildren().addAll(closeText);

		this.getChildren().add(closeBox);
	}

	/**
	 * Initialize new title text.
	 */
	private void addTitle() {
		optionTitle = new Text();
		optionTitle.setFont(FontUtil.getFont("small"));
		optionTitle.setFill(Color.BLACK);

		this.getChildren().add(optionTitle);
	}
	
	/**
     * Updates the pop-up window with the specified building.
     *
     * @param building The building to be updated in the pop-up window.
     */
	public void update(BaseBuilding building) {
		setBuilding(building);
		Resource resource = (Resource) building;
		optionTitle.setText("Set workers in " + resource.getClass().getSimpleName());
		amount.setText(Integer.toString(resource.getCurrentPeople()));
		unemployed.setText("Unemployed: " + Integer.toString(GameLogic.getUnemployed()));
		max.setText(" / " + Integer.toString(resource.getMaxPeople()));
		
		closeBox.setOnMouseClicked((event) -> {
			try {
				quitJob();
				GameScene.getResourceStatus().update(building);
				GameScene.getWorkerStatus().update();
				remove();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Handle when component is removed from the scene.
	 */
	public void remove() {
		try {
			((StackPane) getParent()).getChildren().remove(this);
			InterruptController.setIsChangeJobOpen(false);
		} catch (ClassCastException e) {
			System.out.println(this.getClass().getName() + " has already closed");
		} catch (NullPointerException e) {
			System.out.println(this.getClass().getName() + " has not opened yet.");
		}
	}
	
	/**
     * Retrieves the current building associated with the pop-up window.
     *
     * @return The current building associated with the pop-up window.
     */
	public BaseBuilding getBuilding() {
		return building;
	}
	
	 /**
     * Sets the building for the pop-up window.
     *
     * @param building The building to be associated with the pop-up window.
     */
	public void setBuilding(BaseBuilding building) {
		this.building = building;
	}
	
}
