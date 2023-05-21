package entity.unit;

import entity.building.BaseBuilding;
import game.GameLogic;
import game.Position;

/**
 * The base class for unit. They can attack and move.
 *
 */
public class BaseUnit implements Attackable, Movable{
	
	/**
	 * People of this unit
	 */
	private int people;
	
	/**
	 * Attack range of this unit
	 */
	private int attackRange;
	
	/**
	 * Move range of this unit
	 */
	private int moveRange;
	
	/**
	 * Attack multiplier of this unit
	 */
	private float attackMultiplier;
	
	/**
	 * Moved state of this unit
	 */
	private boolean isMoved;
	
	/**
	 * Attacked state of this unit
	 */
	private boolean isAttacked;
	
	/**
	 * Position of this unit
	 */
	private Position position;
	
	/**
	 * The constructor of this class.
	 * 
	 * @param people 			People of this unit
	 * @param attackRange		Attack range of this unit
	 * @param moveRange 		Move rangeof this unit
	 * @param attackMultiplier 	Attack multiplier of this unit
	 */
	public BaseUnit(int people, int attackRange, int moveRange, float attackMultiplier) {
		setPeople(people);
		setAttackRange(attackRange);
		setMoveRange(moveRange);
		setAttackMultiplier(attackMultiplier);
		setMoved(false);
		setIsAttacked(false);
	}
	
	/**
	 * This method is called when this unit attack another unit.
	 * 
	 * @param to the unit which this entity attack to
	 * 
	 */
	@Override
	public void attack(BaseUnit to) {
		to.setPeople(to.people - (int)(getPeople()*getAttackMultiplier()));
	}
	
	/**
	 * This method is called when this unit attack another building.
	 * 
	 * @param building the building which this entity attack to
	 * 
	 */
	@Override
	public void destroy(BaseBuilding building) {
		building.setDurability(building.getDurability() - (int)(getPeople()*getAttackMultiplier()) );
		
	}
	
	/**
	 * This methods is called when {@link BaseUnit} is moving to specific
	 * {@link Position}.
	 * 
	 * @param destination The position which entity will be moving to
	 * 
	 */
	@Override
	public void move (Position destination) {
		if(GameLogic.getOurUnits().containsKey(this)) {
			GameLogic.removeOurUnit(this);
			GameLogic.addOurUnit(this, destination);
		}
		else if(GameLogic.getEnemyUnits().containsKey(this)) {
			GameLogic.removeEnemyUnit(this);
			GameLogic.addEnemyUnit(this, destination);
		}
	}
	
	/**
	 * Setter for {@link #people}
	 * @param people for {@link #people}
	 */
	public void setPeople(int people) {
		if(people <= 0) {
			GameLogic.removeUnit(this);
		}
		this.people = Math.max(0, people);
	}
	
	/**
	 * Setter for {@link #attackRange}
	 * @param attackRange for {@link #attackRange}
	 */
	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}
	
	/**
	 * Setter for {@link #moveRange}
	 * @param moveRange for {@link #moveRange}
	 */
	public void setMoveRange(int moveRange) {
		this.moveRange = moveRange;
	}
	
	/**
	 * Setter for {@link #attackMultiplier}
	 * @param attackMultiplier for {@link #attackMultiplier}
	 */
	public void setAttackMultiplier(float attackMultiplier) {
		this.attackMultiplier = attackMultiplier;
	}

	/**
	 * Setter for {@link #isAttacked}
	 * @param isAttacked for {@link #isAttacked}
	 */
	public void setIsAttacked(boolean isAttacked) {
		this.setAttacked(isAttacked);
	}
	
	/**
	 * Getter for {@link #people}
	 * @return {@link #people}
	 */
	public int getPeople () {
		return people;
	}
	
	/**
	 * Getter for {@link #attackRange}
	 * @return {@link #attackRange}
	 */
	public int getAttackRange() {
		return attackRange;
	}
	
	/**
	 * Getter for {@link #moveRange}
	 * @return {@link #moveRange}
	 */
	public int getMoveRange() {
		return moveRange;
	}
	
	/**
	 * Getter for {@link #attackMultiplier}
	 * @return {@link #attackMultiplier}
	 */
	public float getAttackMultiplier() {
		return this.attackMultiplier;
	}

	/**
	 * Getter for {@link #position}
	 * @return {@link #position}
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Setter for {@link #position}
	 * @param position for {@link #position}
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Getter for {@link #isAttacked}
	 * @return {@link #isAttacked}
	 */
	public boolean isAttacked() {
		return isAttacked;
	}

	/**
	 * Setter for {@link #isAttacked}
	 * @param isAttacked for {@link #isAttacked}
	 */
	public void setAttacked(boolean isAttacked) {
		this.isAttacked = isAttacked;
	}

	/**
	 * Getter for {@link #isMoved}
	 * @return {@link #isMoved}
	 */
	public boolean isMoved() {
		return isMoved;
	}

	/**
	 * Setter for {@link #isMoved}
	 * @param isMoved for {@link #isMoved}
	 */
	public void setMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}

}
