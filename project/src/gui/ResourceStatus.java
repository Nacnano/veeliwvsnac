package gui;

import entity.building.Resource;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ResourceStatus extends VBox {
	private Text currentPeople;
	private Text durability;
	
	public ResourceStatus(Resource resource) {
		currentPeople = new Text("People: " + resource.getCurrentPeople() + "/" + resource.getMaxPeople());
		durability = new Text("Durability: " + resource.getDurability());
		
		this.getChildren().addAll(currentPeople, durability);
	}
	
}
