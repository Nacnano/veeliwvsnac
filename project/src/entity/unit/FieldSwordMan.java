package entity.unit;

public class FieldSwordMan extends SwordMan implements Attackable, Movable{

	
	public FieldSwordMan(int people, int attackRange, int moveRange, float attackMultiplier) {
		super(people, attackRange, moveRange, attackMultiplier);
	}
	
	public void attack() {
		// some logic to check terrain
		// if(GameLogic.)
	}
	
	public void move() {
		// some logic to check terrain
		// if(GameLogic.)
	}
}
