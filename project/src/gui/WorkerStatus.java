package gui;

import entity.building.Resource;
import game.GameLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

public class WorkerStatus extends VBox {
	private Text field, mine, sawmill, smelter;
	
	public WorkerStatus() {
		this.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPrefSize(40 * GameConfig.getScale(), 40 * GameConfig.getScale());
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);
		
		field = new Text("Field Workers: " + Integer.toString(GameLogic.getNumberOfWorkers("Field")));
//		field.setFont(FontUtil.getFont("extraSmall"));
		
//		mine = new Text("Mine Workers: " + Integer.toString(GameLogic.getNumberOfWorkers("Mine")));
//		mine.setFont(FontUtil.getFont("extraSmall"));
		
//		this.getChildren().addAll(field, mine);
	}
	
}
