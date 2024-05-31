package gui;

import controller.InterruptController;
import entity.building.Field;
import entity.building.House;
import entity.building.MilitaryCamp;
import entity.building.Mine;
import entity.building.Sawmill;
import entity.building.Smelter;
import game.GameLogic;
import game.MapRenderer;
import game.Position;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
 * The BuildPopUp class represents a graphical user interface component that displays a pop-up
 * window for building options. It allows the user to select and build different types of buildings
 * on the game map.
 */
public class BuildPopUp extends VBox {

	/**
	 * Represent the height of the pane.
	 */
	private final int heightBox = 100;

	/**
	 * Represent the width of the pane.
	 */
	private final int widthBox = 300;
	Color btnColor = Color.rgb(245, 246, 231);
	
	HBox optionsBox;
	
	Position pos;
	Text buildButton;
	
	/**
	 * The constructor of the class. Initialize the inside component, event handler
	 * and style.
	 */
	public BuildPopUp() {
		styleSetup();
		addTitle();
		addOptions();
		addCloseText();

		setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				remove();
			}
		});

		InterruptController.setIsBuildOpen(false);
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
     * Adds the options box to the BuildPopUp pane.
     */
	private void addOptions() {
		optionsBox = new HBox(5);
		optionsBox.setAlignment(Pos.CENTER);
		this.getChildren().add(optionsBox);
	}
	
	/**
     * Builds and returns a VBox component for the specified Field object.
     *
     * @param field The Field object for which to build the VBox component.
     * @return The constructed VBox component.
     */
	private VBox buildField(Field field) {
		StyledVBoxButton vbox = new StyledVBoxButton(btnColor);
		vbox.setAlignment(Pos.CENTER);
		Label label = new Label("Barn");
		label.setFont(FontUtil.getFont("small"));
		
		Text wood = new Text("Wood: " + GameConfig.FIELD_REQUIRE_WOOD);
		wood.setFont(FontUtil.getFont("extraSmall"));
		Text stone = new Text("Stone: " + GameConfig.FILED_REQUIRE_STONE);
		stone.setFont(FontUtil.getFont("extraSmall"));
		Text iron = new Text("Iron: " + GameConfig.FIELD_REQUIRE_IRON);
		iron.setFont(FontUtil.getFont("extraSmall"));
		Text money = new Text("Money: " + GameConfig.FIELD_REQUIRE_MONEY);
		money.setFont(FontUtil.getFont("extraSmall"));
		
		Text build = new Text("Build");
		build.setFont(FontUtil.getFont("small"));
		
		vbox.setOnMouseClicked((event) -> {
			try {
				GameLogic.buildBuilding(field, pos);
				MapRenderer.render();
				remove();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		vbox.getChildren().addAll(label, wood, stone, iron, money, build);
		return vbox;
	}
	
	/**
     * Builds and returns a VBox component for the specified Mine object.
     *
     * @param mine The Mine object for which to build the VBox component.
     * @return The constructed VBox component.
     */
	private VBox buildMine(Mine mine) {
		StyledVBoxButton vbox = new StyledVBoxButton(btnColor);
		vbox.setAlignment(Pos.CENTER);
		Label label = new Label("Mine");
		label.setFont(FontUtil.getFont("small"));
		
		Text wood = new Text("Wood: " + GameConfig.MINE_REQUIRE_WOOD);
		wood.setFont(FontUtil.getFont("extraSmall"));
		Text stone = new Text("Stone: " + GameConfig.MINE_REQUIRE_STONE);
		stone.setFont(FontUtil.getFont("extraSmall"));
		Text iron = new Text("Iron: " + GameConfig.MINE_REQUIRE_IRON);
		iron.setFont(FontUtil.getFont("extraSmall"));
		Text money = new Text("Money: " + GameConfig.MINE_REQUIRE_MONEY);
		money.setFont(FontUtil.getFont("extraSmall"));
		
		Text build = new Text("Build");
		build.setFont(FontUtil.getFont("small"));
		
		vbox.setOnMouseClicked((event) -> {
			try {
				GameLogic.buildBuilding(mine, pos);
				MapRenderer.render();
				remove();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		vbox.getChildren().addAll(label, wood, stone, iron, money, build);
		return vbox;
	}
	
	/**
     * Builds and returns a VBox component for the specified Sawmill object.
     *
     * @param sawmill The Sawmill object for which to build the VBox component.
     * @return The constructed VBox component.
     */
	private VBox buildSawmill(Sawmill sawmill) {
		StyledVBoxButton vbox = new StyledVBoxButton(btnColor);
		vbox.setAlignment(Pos.CENTER);
		Label label = new Label("Sawmill");
		label.setFont(FontUtil.getFont("small"));
		
		Text wood = new Text("Wood: " + GameConfig.SAWMILL_REQUIRE_WOOD);
		wood.setFont(FontUtil.getFont("extraSmall"));
		Text stone = new Text("Stone: " + GameConfig.SAWMILL_REQUIRE_STONE);
		stone.setFont(FontUtil.getFont("extraSmall"));
		Text iron = new Text("Iron: " + GameConfig.SAWMILL_REQUIRE_IRON);
		iron.setFont(FontUtil.getFont("extraSmall"));
		Text money = new Text("Money: " + GameConfig.SAWMILL_REQUIRE_MONEY);
		money.setFont(FontUtil.getFont("extraSmall"));
		
		Text build = new Text("Build");
		build.setFont(FontUtil.getFont("small"));
		
		vbox.setOnMouseClicked((event) -> {
			try {
				GameLogic.buildBuilding(sawmill, pos);
				MapRenderer.render();
				remove();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		vbox.getChildren().addAll(label, wood, stone, iron, money, build);
		return vbox;
	}
	
	/**
     * Builds and returns a VBox component for the specified Smelter object.
     *
     * @param smelter The Smelter object for which to build the VBox component.
     * @return The constructed VBox component.
     */
	private VBox buildSmelter(Smelter smelter) {
		StyledVBoxButton vbox = new StyledVBoxButton(btnColor);
		vbox.setAlignment(Pos.CENTER);
		Label label = new Label("Smelter");
		label.setFont(FontUtil.getFont("small"));
		
		Text wood = new Text("Wood: " + GameConfig.SMELTER_REQUIRE_WOOD);
		wood.setFont(FontUtil.getFont("extraSmall"));
		Text stone = new Text("Stone: " + GameConfig.SMELTER_REQUIRE_STONE);
		stone.setFont(FontUtil.getFont("extraSmall"));
		Text iron = new Text("Iron: " + GameConfig.SMELTER_REQUIRE_IRON);
		iron.setFont(FontUtil.getFont("extraSmall"));
		Text money = new Text("Money: " + GameConfig.SMELTER_REQUIRE_MONEY);
		money.setFont(FontUtil.getFont("extraSmall"));
		
		Text build = new Text("Build");
		build.setFont(FontUtil.getFont("small"));
		
		vbox.setOnMouseClicked((event) -> {
			try {
				GameLogic.buildBuilding(smelter, pos);
				MapRenderer.render();
				remove();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		vbox.getChildren().addAll(label, wood, stone, iron, money, build);
		return vbox;
	}
	
	/**
     * Builds and returns a VBox component for the specified House object.
     *
     * @param house The House object for which to build the VBox component.
     * @return The constructed VBox component.
     */
	private VBox buildHouse(House house) {
		StyledVBoxButton vbox = new StyledVBoxButton(btnColor);
		vbox.setAlignment(Pos.CENTER);
		Label label = new Label("House");
		label.setFont(FontUtil.getFont("small"));
		
		Text wood = new Text("Wood: " + GameConfig.HOUSE_REQUIRE_WOOD);
		wood.setFont(FontUtil.getFont("extraSmall"));
		Text stone = new Text("Stone: " + GameConfig.HOUSE_REQUIRE_STONE);
		stone.setFont(FontUtil.getFont("extraSmall"));
		Text iron = new Text("Iron: " + GameConfig.HOUSE_REQUIRE_IRON);
		iron.setFont(FontUtil.getFont("extraSmall"));
		Text money = new Text("Money: " + GameConfig.HOUSE_REQUIRE_MONEY);
		money.setFont(FontUtil.getFont("extraSmall"));
		
		Text build = new Text("Build");
		build.setFont(FontUtil.getFont("small"));
		
		vbox.setOnMouseClicked((event) -> {
			try {
				GameLogic.buildBuilding(house, pos);
				MapRenderer.render();
				remove();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		vbox.getChildren().addAll(label, wood, stone, iron, money, build);
		return vbox;
	}
	
	/**
     * Builds and returns a VBox component for the specified Military Camp object.
     *
     * @param militaryCamp The Military Camp object for which to build the VBox component.
     * @return The constructed VBox component.
     */
	private VBox buildMilitaryCamp(MilitaryCamp militaryCamp) {
		StyledVBoxButton vbox = new StyledVBoxButton(btnColor);
		vbox.setAlignment(Pos.CENTER);
		Label label = new Label("Military Camp");
		label.setFont(FontUtil.getFont("small"));
		
		Text wood = new Text("Wood: " + GameConfig.MILITARYCAMP_REQUIRE_WOOD);
		wood.setFont(FontUtil.getFont("extraSmall"));
		Text stone = new Text("Stone: " + GameConfig.MILITARYCAMP_REQUIRE_STONE);
		stone.setFont(FontUtil.getFont("extraSmall"));
		Text iron = new Text("Iron: " + GameConfig.MILITARYCAMP_REQUIRE_IRON);
		iron.setFont(FontUtil.getFont("extraSmall"));
		Text money = new Text("Money: " + GameConfig.MILITARYCAMP_REQUIRE_MONEY);
		money.setFont(FontUtil.getFont("extraSmall"));
		
		Text build = new Text("Build");
		build.setFont(FontUtil.getFont("small"));
		
		vbox.setOnMouseClicked((event) -> {
			try {
				GameLogic.buildBuilding(militaryCamp, pos);
				MapRenderer.render();
				remove();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		vbox.getChildren().addAll(label, wood, stone, iron, money, build);
		return vbox;
	}
	
	/**
	 * Initialize new title text.
	 */
	private void addTitle() {
		Text optionTitle = new Text("Build a building");

		optionTitle.setFont(FontUtil.getFont("small"));
		optionTitle.setFill(Color.BLACK);

		this.getChildren().add(optionTitle);
	}
	
	/**
	 * Adds the close text section to the pop-up.
	 */
	private void addCloseText() {
		HBox closeBox = new HBox();
		closeBox.setPadding(new Insets(10, 0, 0, 0));
		closeBox.setAlignment(Pos.CENTER);

		Text closeText = new Text("Cancel");
		closeText.setFont(FontUtil.getFont("small"));
		closeText.setFill(Color.BLACK);
		closeText.setStroke(null);

		closeText.setOnMouseClicked((event) -> {
			try {
				remove();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		closeBox.getChildren().addAll(closeText);

		this.getChildren().add(closeBox);
	}
	
	/**
	 * Updates the pop-up with the specified position.
	 *
	 * @param pos The Position associated with the pop-up.
	 */
	public void update(Position pos) {
		setPos(pos);
		optionsBox.getChildren().clear();
		
		House house = new House();
		if (GameLogic.canBuildBuilding(house, pos))
			optionsBox.getChildren().add(buildHouse(house));
		
		Field field = new Field();
		if (GameLogic.canBuildBuilding(field, pos))
			optionsBox.getChildren().add(buildField(field));
		
		Mine mine = new Mine();
		if (GameLogic.canBuildBuilding(mine, pos))
			optionsBox.getChildren().add(buildMine(mine));
		
		Sawmill sawmill = new Sawmill();
		if (GameLogic.canBuildBuilding(sawmill, pos))
			optionsBox.getChildren().add(buildSawmill(sawmill));
		
		Smelter smelter = new Smelter();
		if (GameLogic.canBuildBuilding(smelter, pos))
			optionsBox.getChildren().add(buildSmelter(smelter));
		
		MilitaryCamp militaryCamp = new MilitaryCamp();
		if (GameLogic.canBuildBuilding(militaryCamp, pos))
			optionsBox.getChildren().add(buildMilitaryCamp(militaryCamp));		
	}
	
	/**
	 * Handle when component is removed from the scene.
	 */
	public void remove() {
		try {
			((StackPane) getParent()).getChildren().remove(this);
			GameScene.getMaterialStatus().update();
			InterruptController.setIsBuildOpen(false);
		} catch (ClassCastException e) {
			System.out.println(this.getClass().getName() + " has already closed");
		} catch (NullPointerException e) {
			System.out.println(this.getClass().getName() + " has not opened yet.");
		}
	}

	/**
	 * Gets the position associated with the pop-up.
	 *
	 * @return The position associated with the pop-up.
	 */
	public Position getPos() {
		return pos;
	}

	/**
	 * Sets the position associated with the pop-up.
	 *
	 * @param pos The Position to be associated with the pop-up.
	 */
	public void setPos(Position pos) {
		this.pos = pos;
	}
	
}
