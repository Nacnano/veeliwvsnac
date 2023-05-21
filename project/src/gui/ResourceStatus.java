package gui;

import entity.building.BaseBuilding;
import entity.building.Resource;
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
	private BaseBuilding building;
	private Text name;
	private Text currentPeople;
	private Text durability;
	
	public ResourceStatus() {
		setBuilding(null);
		this.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPrefSize(50 * GameConfig.getScale(), 50 * GameConfig.getScale());
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);
		name = new Text("Buidling: -");
		name.setFont(FontUtil.getFont("extraSmall"));
		currentPeople = new Text("People: -");
		currentPeople.setFont(FontUtil.getFont("extraSmall"));
		durability = new Text("Durability: -");
		durability.setFont(FontUtil.getFont("extraSmall"));
		
		this.getChildren().addAll(name, currentPeople, durability);
	}

	public void update(BaseBuilding building) {
		setBuilding(building);
		if (building == null) {
			name.setText("Buidling: -");
			currentPeople.setText("People: -");
			durability.setText("Durability: -");
		}
		else {
			if (building instanceof Resource) {
				Resource resource = (Resource) building;
				name.setText("Building: " + resource.getClass().getSimpleName());
				currentPeople.setText("People: " + resource.getCurrentPeople() + "/" + resource.getMaxPeople());
				durability.setText("Durability: " + resource.getDurability());
			}
			else {
				name.setText("Building: " + building.getClass().getSimpleName());
				currentPeople.setText("People: -");
				durability.setText("Durability: " + building.getDurability());
			}
		}
	}
	
	public BaseBuilding getBuilding() {
		return building;
	}
	
	public void setBuilding(BaseBuilding building) {
		this.building = building;
	}

	public String getName() {
		return name.getText();
	}

	public void setName(Text name) {
		this.name = name;
	}

	public String getCurrentPeople() {
		return currentPeople.getText();
	}

	public void setCurrentPeople(Text currentPeople) {
		this.currentPeople = currentPeople;
	}

	public Text getDurability() {
		return durability;
	}

	public void setDurability(Text durability) {
		this.durability = durability;
	}
}
