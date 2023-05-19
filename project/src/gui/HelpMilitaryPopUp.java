package gui;

import controller.InterruptController;
import entity.unit.BaseUnit;
import entity.unit.SwordMan;
import game.GameLogic;
import game.Material;
import game.Terrain;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

public class HelpMilitaryPopUp extends VBox {

	/**
	 * Represent the height of the pane.
	 */
	private final int heightBox = 50;

	/**
	 * Represent the width of the pane.
	 */
	private final int widthBox = 150;
	Color btnColor = Color.BEIGE;

	BaseUnit unit;
	
	/**
	 * The constructor of the class. Initialize the inside component, event handler
	 * and style.
	 */
	public HelpMilitaryPopUp(BaseUnit unit) {
		this.unit = unit;
		styleSetup();
		addTitle();
		addOptions();
		addCloseText();

		setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				((Pane) this.getParent()).getChildren().remove(this);
			}
		});

		InterruptController.setSettingOpen(true);
	}

	/**
	 * Initialize style for pane.
	 */
	private void styleSetup() {
		setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN, null, null)));
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
	
	private void addOptions() {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER);
		
		box.getChildren().add(heal());
		if (unit instanceof SwordMan)
			box.getChildren().add(upgradeSwordMan());
		
		this.getChildren().add(box);
	}

	private VBox heal() {
		StyledVBoxButton vbox = new StyledVBoxButton(btnColor);
		vbox.setAlignment(Pos.CENTER);
		Label label = new Label("Heal");
		label.setFont(FontUtil.getFont("extraSmall"));
		Text text1 = new Text("Current: " + unit.getPeople() + "/" + GameConfig.MILITARY_SIZE);
		text1.setFont(FontUtil.getFont("extraSmall"));
		Text text2 = new Text("Unemployed: " + GameLogic.getUnemployed());
		text2.setFont(FontUtil.getFont("extraSmall"));
		
		vbox.setOnMouseClicked((event) -> {
			try {
				GameLogic.heal(unit);
				((Pane) this.getParent()).getChildren().remove(this);
				InterruptController.setSettingOpen(false);
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		vbox.getChildren().addAll(label, text1, text2);
		return vbox;
	}
	
	private VBox upgradeSwordMan() {
		StyledVBoxButton vbox = new StyledVBoxButton(btnColor);
		vbox.setAlignment(Pos.CENTER);
		Terrain terrain = GameLogic.getOurUnitTerrain(unit);
		Label label = new Label("Upgrade to " + terrain + " Swordman");
		label.setFont(FontUtil.getFont("extraSmall"));
		Text text = new Text("Money: " + GameConfig.MILLITARY_UPGRADE_PRICE);
		text.setFont(FontUtil.getFont("extraSmall"));
		
		vbox.setOnMouseClicked((event) -> {
			try {
				GameLogic.upgradeSwordMan(unit);
				((Pane) this.getParent()).getChildren().remove(this);
				InterruptController.setSettingOpen(false);
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		vbox.getChildren().addAll(label, text);
		return vbox;
	}
	
	/**
	 * Initialize new close text which can be clicked to close pane.
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
				((Pane) this.getParent()).getChildren().remove(this);
				InterruptController.setSettingOpen(false);
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		closeBox.getChildren().addAll(closeText);

		this.getChildren().add(closeBox);
	}

	/**
	 * Initialize new title text.
	 */
	private void addTitle() {
		Text optionTitle = new Text("Military Helper");

		optionTitle.setFont(FontUtil.getFont("small"));
		optionTitle.setFill(Color.BLACK);

		this.getChildren().add(optionTitle);
	}
}
