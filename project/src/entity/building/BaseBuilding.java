package entity.building;

public class BaseBuilding implements Buildable {

	private int durability;

	public void build(BaseBuilding b) {
		
		
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}
}
