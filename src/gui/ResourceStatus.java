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

/**
 * The ResourceStatus class represents a graphical component that displays information about a building's resources.
 * It provides details such as the building name, current number of people, and durability.
 * The component's appearance and information are updated based on the provided BaseBuilding object.
 */
public class ResourceStatus extends VBox {
	private BaseBuilding building;
	private Text name;
	private Text currentPeople;
	private Text durability;
	
	/**
     * Constructs a new ResourceStatus component.
     * It initializes the component's visual style, size, and layout.
     * The initial values for the building's information are set to placeholders.
     */
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

	/**
     * Updates the information displayed by the ResourceStatus component based on the provided BaseBuilding object.
     * If the building is null, the component displays placeholder values.
     * If the building is a Resource object, it displays specific resource-related information.
     * If the building is a non-resource building, it displays general building information.
     *
     * @param building The BaseBuilding object to retrieve information from.
     */
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
	
	/**
	 * Getter for the BaseBuilding object associated with this ResourceStatus component.
	 *
	 * @return The BaseBuilding object associated with this ResourceStatus.
	 */
	public BaseBuilding getBuilding() {
		return building;
	}
	
	/**
	 * Sets the BaseBuilding object associated with this ResourceStatus component.
	 *
	 * @param building The BaseBuilding object to associate with this ResourceStatus.
	 */
	public void setBuilding(BaseBuilding building) {
		this.building = building;
	}

	/**
	 * Gatter for the text representing the name of the building displayed by this ResourceStatus component.
	 *
	 * @return The name of the building.
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * Sets the text representing the name of the building displayed by this ResourceStatus component.
	 *
	 * @param name The text representing the name of the building.
	 */
	public void setName(Text name) {
		this.name = name;
	}

	/**
	 * Getter for the text representing the current number of people associated with the building displayed by this ResourceStatus component.
	 *
	 * @return The current number of people.
	 */
	public String getCurrentPeople() {
		return currentPeople.getText();
	}

	/**
	 * Sets the text representing the current number of people associated with the building displayed by this ResourceStatus component.
	 *
	 * @param currentPeople The text representing the current number of people.
	 */
	public void setCurrentPeople(Text currentPeople) {
		this.currentPeople = currentPeople;
	}

	/**
	 * Getter for the text representing the durability of the building displayed by this ResourceStatus component.
	 *
	 * @return The durability of the building.
	 */
	public Text getDurability() {
		return durability;
	}

	/**
	 * Sets the text representing the durability of the building displayed by this ResourceStatus component.
	 *
	 * @param durability The text representing the durability of the building.
	 */
	public void setDurability(Text durability) {
		this.durability = durability;
	}
}
