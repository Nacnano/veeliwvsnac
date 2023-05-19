package gui;

import controller.InterruptController;
import game.GameLogic;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

public class BuildMilitaryPopUp extends VBox {

	/**
	 * Represent the height of the pane.
	 */
	private final int heightBox = 50;

	/**
	 * Represent the width of the pane.
	 */
	private final int widthBox = 50;
	Color btnColor = Color.BEIGE;
	
	Position pos;
	
	/**
	 * The constructor of the class. Initialize the inside component, event handler
	 * and style.
	 */
	public BuildMilitaryPopUp(Position pos) {
		this.pos = pos;
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
		setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, null, null)));
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
				
		if (GameLogic.getUnemployed() < GameConfig.MILITARY_SIZE) {
			Text text = new Text("Unemployed: " + GameLogic.getUnemployed() + " (expected >= " + GameConfig.MILITARY_SIZE + ")");
			text.setFont(FontUtil.getFont("extraSmall"));
			box.getChildren().add(text);
		}
		else {
			box.getChildren().addAll(buildSwordMan(), buildArcher());
		}

		this.getChildren().add(box);
	}
	
	private VBox buildSwordMan() {
		StyledVBoxButton vbox = new StyledVBoxButton(btnColor);
		vbox.setAlignment(Pos.CENTER);
//		vbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
//				new BorderWidths(GameConfig.getScale()))));
		Label label = new Label("SwordMan");
		label.setFont(FontUtil.getFont("extraSmall"));
		
		vbox.setOnMouseClicked((event) -> {
			try {
				GameLogic.buildMilitary(pos, "SwordMan");
				((Pane) this.getParent()).getChildren().remove(this);
				InterruptController.setSettingOpen(false);
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		vbox.getChildren().addAll(label);
		
		return vbox;
	}
	
	private VBox buildArcher() {
		StyledVBoxButton vbox = new StyledVBoxButton(btnColor);
		vbox.setAlignment(Pos.CENTER);
		Label label = new Label("Archer");
		label.setFont(FontUtil.getFont("extraSmall"));
		
		vbox.setOnMouseClicked((event) -> {
			try {
				GameLogic.buildMilitary(pos, "Archer");
				((Pane) this.getParent()).getChildren().remove(this);
				InterruptController.setSettingOpen(false);
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		vbox.getChildren().addAll(label);
		return vbox;
	}
	
	/**
	 * Initialize new title text.
	 */
	private void addTitle() {
		Text optionTitle = new Text("Build a military");

		optionTitle.setFont(FontUtil.getFont("small"));
		optionTitle.setFill(Color.BLACK);

		this.getChildren().add(optionTitle);
	}
	
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
}
