package entity.unit;

import game.Terrain;
import utils.GameConfig;

public class SwordMan extends BaseUnit implements Movable, Attackable{

	public SwordMan () {
		super(GameConfig.MILITARY_SIZE, GameConfig.SWORDMAN_ATTACK_RANGE, GameConfig.SWORDMAN_MOVE_RANGE, GameConfig.SWORDMAN_ATTACK_MULTIPLIER);
	}
	
	public SwordMan(int people, int attackRange, int moveRange, float attackMultiplier) {
		super(people, attackRange, moveRange, attackMultiplier);
	}
	
	public void buffByTerrain(Terrain t) {
//		System.out.println("1)");
		setAttackMultiplier(GameConfig.SWORDMAN_ATTACK_MULTIPLIER);
		setMoveRange(GameConfig.SWORDMAN_MOVE_RANGE);
	}
	
}
