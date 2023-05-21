package entity.building;

/**
 * Base class to represent resources building
 */
public abstract class Resource extends BaseBuilding {

	/**
	 * Max people of this building
	 */
	private int maxPeople;
	
	/**
	 * Current people of this building
	 */
	private int currentPeople;
	
	/**
	 * Fatility rate of this building
	 */
	private float fatalityRate;
	
	/**
	 * The constructor of this class
	 * 
	 * @param durability 	Durability of this building
	 * @param maxPeople 	Max people of this building
	 * @param fatalityRate 	Fatility rate of this building
	 */
	public Resource(int durability, int maxPeople, float fatalityRate) {
		super(durability);
		setMaxPeople(maxPeople);
		setCurrentPeople(0);
		setFatalityRate(fatalityRate);
	}
	
	/**
	 * Getter for {@link #maxPeople}
	 * @return {@link #maxPeople}
	 */
	public int getMaxPeople() {
		return maxPeople;
	}
	
	/**
	 * Setter for {@link #maxPeople}
	 * @param maxPeople for {@link #maxPeople}
	 */
	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}
	
	/**
	 * Getter for {@link #currentPeople}
	 * @return {@link #currentPeople}
	 */
	public int getCurrentPeople() {
		return currentPeople;
	}
	
	/**
	 * Setter for {@link #currentPeople}
	 * @param currentPeople for {@link #currentPeople}
	 */
	public void setCurrentPeople(int currentPeople) {
		this.currentPeople = currentPeople;
	}
	
	/**
	 * Getter for {@link #fatalityRate}
	 * @return {@link #fatalityRate}
	 */
	public float getFatalityRate() {
		return fatalityRate;
	}
	
	/**
	 * Setter for {@link #fatalityRate}
	 * @param fatalityRate for {@link #fatalityRate}
	 */
	public void setFatalityRate(float fatalityRate) {
		this.fatalityRate = fatalityRate;
	}
}
