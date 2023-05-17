package entity.unit;

import game.GameLogic;
import game.Position;

public class BaseUnit implements Attackable, Movable{
	private int people;
	private int attackRange;
	private int moveRange;
	private float attackMultiplier;
	private boolean isMoved;
	// private Position position;
	
	public BaseUnit(int people, int attackRange, int moveRange, float attackMultiplier, boolean isMoved ) {
		setPeople(people);
		setAttackRange(attackRange);
		setMoveRange(moveRange);
		setAttackMultiplier(attackMultiplier);
		setIsMoved(isMoved);
	}
	
	public void attack(BaseUnit enemy) {
		// if(getAttackRange() <= getPosition.getDistanceFrom(enemy.getPosition))
		enemy.setPeople(enemy.people-getPeople()*getAttackMultiplier());
	}
	
	public void move (Position destination) {
		// if(getMoveRange() <= getPosition.getDistanceFrom(destination))
		GameLogic.moveUnit(this, destination);
	}
	
	
	
	public void setPeople(int people) {
		this.people = Math.max(0, people);
	}
	
	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}
	
	public void setMoveRange(int moveRange) {
		this.moveRange = moveRange;
	}
	
	public void setAttackMultiplier(float attackMultiplier) {
		this.attackMultiplier = attackMultiplier;
	}
	
	public void setIsMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}
	
	public int getPeople () {
		return this.people;
	}
	
	public int getAttackRange() {
		return this.attackRange;
	}
	
	public int getMoveRange() {
		return this.moveRange;
	}
	
	public int getAttackMultiplier() {
		return this.attackMultiplier;
	}
	
	public boolean getIsMoved() {
		return this.isMoved;
	}
}
