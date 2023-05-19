package gui;

import entity.building.Resource;
import game.GameLogic;
import game.Position;
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

public class ResourceStatus extends VBox {
	private Text name;
	private Text currentPeople;
	private Text durability;
	
	public ResourceStatus() {
		this.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPrefSize(50 * GameConfig.getScale(), 50 * GameConfig.getScale());
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);
		name = new Text("Buidling: -");
		currentPeople = new Text("People: -");
		currentPeople.setFont(FontUtil.getFont("extraSmall"));
		durability = new Text("Durability: -");
		durability.setFont(FontUtil.getFont("extraSmall"));
		
		this.getChildren().addAll(currentPeople, durability);
	}
	
	public ResourceStatus(Position pos) {
		Resource resource = (Resource) GameLogic.getBuildings().get(pos);
		this.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPrefSize(50 * GameConfig.getScale(), 50 * GameConfig.getScale());
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);
		
		name = new Text("Building: " + resource.getClass().getSimpleName());
		name.setFont(FontUtil.getFont("extraSmell"));
		currentPeople = new Text("People: " + resource.getCurrentPeople() + "/" + resource.getMaxPeople());
		currentPeople.setFont(FontUtil.getFont("extraSmall"));
		durability = new Text("Durability: " + resource.getDurability());
		durability.setFont(FontUtil.getFont("extraSmall"));
		
		this.getChildren().addAll(currentPeople, durability);
	}
	
}
