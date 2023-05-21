package entity.unit;

/**
 * The FieldSwordMan class represents Field Swordman unit. They can attack and move.
 *
 */
public class FieldSwordMan extends SwordMan implements Attackable, Movable{

	/**
	 * The constructor of this class.
	 * 
	 * @param people 			People of this unit
	 * @param attackRange		Attack range of this unit
	 * @param moveRange 		Move rangeof this unit
	 * @param attackMultiplier 	Attack multiplier of this unit
	 */
	public FieldSwordMan(int people, int attackRange, int moveRange, float attackMultiplier) {
		super(people, attackRange, moveRange, attackMultiplier);
	}
	
	/**
	 * The constructor of this class.
	 */
	public FieldSwordMan() {
		super();
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
