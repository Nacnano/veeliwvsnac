package entity.unit;

/**
 * The MountainSwordMan class represents Mountain Swordman unit. They can attack and move.
 *
 */
public class MountainSwordMan extends SwordMan implements Attackable, Movable{

	/**
	 * The constructor of this class.
	 * 
	 * @param people 			People of this unit
	 * @param attackRange		Attack range of this unit
	 * @param moveRange 		Move rangeof this unit
	 * @param attackMultiplier 	Attack multiplier of this unit
	 */
	public MountainSwordMan(int people, int attackRange, int moveRange, float attackMultiplier) {
		super(people, attackRange, moveRange, attackMultiplier);
	}
	
	/**
	 * The constructor of this class.
	 * 
	 */
	public MountainSwordMan() {
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

